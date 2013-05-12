package de.hswt.hrm.place.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.EclipseContextFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.junit.Test;

import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.database.exception.ElementNotFoundException;
import de.hswt.hrm.place.dao.core.IPlaceDao;
import de.hswt.hrm.place.dao.jdbc.PlaceDao;
import de.hswt.hrm.place.model.Place;
import de.hswt.hrm.test.database.AbstractDatabaseTest;

public class PlaceServiceTest extends AbstractDatabaseTest {

    private void comparePlaceFields(final Place expected, final Place actual) {
        assertEquals("Area not set correctly.", expected.getArea(), actual.getArea());
        assertEquals("City not set correctly.", expected.getCity(), actual.getCity());
        assertEquals("Location not set correctly.", expected.getLocation(), actual.getLocation());
        assertEquals("PlaceName not set correctly.", expected.getPlaceName(), actual.getPlaceName());
        assertEquals("PostCode not set correctly.", expected.getPostCode(), actual.getPostCode());
        assertEquals("Street not set correctly.", expected.getStreet(), actual.getStreet());
        assertEquals("StreetNo not set correctly.", expected.getStreetNo(), actual.getStreetNo());
    }
    
    private PlaceService createInjectedPlaceService() {
        IPlaceDao dao = new PlaceDao();
        
        IEclipseContext context = EclipseContextFactory.create();
        context.set(IPlaceDao.class, dao);
        PlaceService service = ContextInjectionFactory.make(PlaceService.class, context);
        
        return service;
    }

    @Test
    public void testFindAll() throws DatabaseException {
        Place p1 = new Place("place Name", "55464", "SomeCity", "Street", "113a", "Location",
                "Some Area");
        Place p2 = new Place("Another place", "65461", "Gotham City", "Bat-Avenue", "558",
                "Bat-Cave", "No idea");
        
        PlaceService service = createInjectedPlaceService();
        service.insert(p1);
        service.insert(p2);

        Collection<Place> places = service.findAll();
        assertEquals("Count of retrieved places does not match.", 2, places.size());
    }

    @Test
    public void testFindById() throws ElementNotFoundException, DatabaseException {
        Place expected = new Place("place Name", "55464", "SomeCity", "Street", "113a", "Location",
                "Some Area");
        
        PlaceService service = createInjectedPlaceService();
        Place parsed = service.insert(expected);

        Place retrieved = service.findById(parsed.getId());
        comparePlaceFields(expected, retrieved);
    }

    @Test
    public void testInsert() throws ElementNotFoundException, DatabaseException {
        Place expected = new Place("place Name", "55464", "SomeCity", "Street", "113a", "Location",
                "Some Area");

        PlaceService service = createInjectedPlaceService();
        Place parsed = service.insert(expected);
        comparePlaceFields(expected, parsed);
        assertTrue("ID not set correctly.", parsed.getId() >= 0);

        Place retrieved = service.findById(parsed.getId());
        comparePlaceFields(expected, retrieved);
        assertEquals("Requested object does not equal parsed one.", parsed, retrieved);
    }

    @Test
    public void testUpdate() throws ElementNotFoundException, DatabaseException {
        Place expected = new Place("place Name", "55464", "SomeCity", "Street", "113a", "Location",
                "Some Area");
        
        PlaceService service = createInjectedPlaceService();
        Place parsed = service.insert(expected);

        Place p2 = new Place("Another place", "65461", "Gotham City", "Bat-Avenue", "558",
                "Bat-Cave", "No idea");
        service.insert(p2);

        parsed.setCity("A Changed City");
        parsed.setStreet("Best Street Ever");
        service.update(parsed);

        Place retrieved = service.findById(parsed.getId());
        comparePlaceFields(parsed, retrieved);
        assertEquals("Requested object does not equal updated one.", parsed, retrieved);
    }
}
