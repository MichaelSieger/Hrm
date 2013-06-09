package de.hswt.hrm.place.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.util.Collection;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.EclipseContextFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.common.base.Optional;

import de.hswt.hrm.common.Config;
import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.database.exception.ElementNotFoundException;
import de.hswt.hrm.common.locking.jdbc.ILockService;
import de.hswt.hrm.common.locking.jdbc.Lock;
import de.hswt.hrm.common.locking.jdbc.LockException;
import de.hswt.hrm.common.locking.jdbc.LockService;
import de.hswt.hrm.place.dao.core.IPlaceDao;
import de.hswt.hrm.place.dao.jdbc.PlaceDao;
import de.hswt.hrm.place.model.Place;
import de.hswt.hrm.test.database.AbstractDatabaseTest;

public class PlaceServiceTest extends AbstractDatabaseTest {

    private void comparePlaceFields(final Place expected, final Place actual) {

        assertEquals("City not set correctly.", expected.getCity(), actual.getCity());
        assertEquals("PlaceName not set correctly.", expected.getPlaceName(), actual.getPlaceName());
        assertEquals("PostCode not set correctly.", expected.getPostCode(), actual.getPostCode());
        assertEquals("Street not set correctly.", expected.getStreet(), actual.getStreet());
        assertEquals("StreetNo not set correctly.", expected.getStreetNo(), actual.getStreetNo());
    }

    private ILockService createLockService() throws DatabaseException, SQLException {
        ILockService lockService = new LockService();
        return lockService;
    }

    private PlaceService createInjectedPlaceService() throws DatabaseException, SQLException {
        IPlaceDao dao = new PlaceDao();

        IEclipseContext context = EclipseContextFactory.create();
        context.set(IPlaceDao.class, dao);
        context.set(ILockService.class, createLockService());
        PlaceService service = ContextInjectionFactory.make(PlaceService.class, context);

        return service;
    }

    @BeforeClass
    public static void disableLockingInConfig() {
        Config cfg = Config.getInstance();
        cfg.setProperty(Config.Keys.DB_LOCKING, "0");
    }

    @Before
    public void forceNewSession() {
        // As we delete the database everytime, but don't rebuild the config
        // between tests, we have to ensure, that a new session is started
        // for every test
        Config cfg = Config.getInstance();
        cfg.setProperty(Config.Keys.SESSION_UUID, "");
    }

    @Test
    public void testFindAll() throws DatabaseException, SQLException {
        Place p1 = new Place("place Name", "55464", "SomeCity", "Street", "113a");
        Place p2 = new Place("Another place", "65461", "Gotham City", "Bat-Avenue", "558");

        PlaceService service = createInjectedPlaceService();
        service.insert(p1);
        service.insert(p2);

        Collection<Place> places = service.findAll();
        assertEquals("Count of retrieved places does not match.", 2, places.size());
    }

    @Test
    public void testFindById() throws ElementNotFoundException, DatabaseException, SQLException {
        Place expected = new Place("place Name", "55464", "SomeCity", "Street", "113a");

        PlaceService service = createInjectedPlaceService();
        Place parsed = service.insert(expected);

        Place retrieved = service.findById(parsed.getId());
        comparePlaceFields(expected, retrieved);
    }

    @Test
    public void testInsert() throws ElementNotFoundException, DatabaseException, SQLException {
        Place expected = new Place("place Name", "55464", "SomeCity", "Street", "113a");

        PlaceService service = createInjectedPlaceService();
        Place parsed = service.insert(expected);
        comparePlaceFields(expected, parsed);
        assertTrue("ID not set correctly.", parsed.getId() >= 0);

        Place retrieved = service.findById(parsed.getId());
        comparePlaceFields(expected, retrieved);
        assertEquals("Requested object does not equal parsed one.", parsed, retrieved);
    }

    @Test
    public void testUpdate() throws ElementNotFoundException, DatabaseException, SQLException {
        Place expected = new Place("place Name", "55464", "SomeCity", "Street", "113a");

        PlaceService service = createInjectedPlaceService();
        Place parsed = service.insert(expected);

        Place p2 = new Place("Another place", "65461", "Gotham City", "Bat-Avenue", "558");
        service.insert(p2);

        parsed.setCity("A Changed City");
        parsed.setStreet("Best Street Ever");
        service.update(parsed);

        Place retrieved = service.findById(parsed.getId());
        comparePlaceFields(parsed, retrieved);
        assertEquals("Requested object does not equal updated one.", parsed, retrieved);
    }

    @Test(expected = LockException.class)
    public void testFailWithoutLock() throws DatabaseException, SQLException {
        Config cfg = Config.getInstance();
        // First we have to enalbe locking
        cfg.setProperty(Config.Keys.DB_LOCKING, "1");

        Place expected = new Place("place Name", "55464", "SomeCity", "Street", "113a");

        PlaceService service = createInjectedPlaceService();
        Place parsed = service.insert(expected);

        // Update without lock

        service.update(parsed);

        // Don't forget to disable locking afterwards to not crash other tests
        cfg.setProperty(Config.Keys.DB_LOCKING, "0");
    }

    @Test()
    public void testUpdateWithLocking() throws DatabaseException, SQLException {
        Config cfg = Config.getInstance();
        // First we have to enalbe locking
        cfg.setProperty(Config.Keys.DB_LOCKING, "1");

        Place expected = new Place("place Name", "55464", "SomeCity", "Street", "113a");

        PlaceService service = createInjectedPlaceService();
        Place parsed = service.insert(expected);

        // Retrieve lock
        ILockService lockService = createLockService();
        Optional<Lock> lock = lockService.getLock(ILockService.TBL_PLACE, parsed.getId());
        assertTrue("Could not retrieve lock.", lock.isPresent());

        // Update lock
        service.update(parsed);

        // Release lock
        lockService.release(lock);

        // Don't forget to disable locking afterwards to not crash other tests
        cfg.setProperty(Config.Keys.DB_LOCKING, "0");
    }
}
