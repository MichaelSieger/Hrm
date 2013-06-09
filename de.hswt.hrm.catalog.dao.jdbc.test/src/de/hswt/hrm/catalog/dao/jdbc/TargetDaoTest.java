package de.hswt.hrm.catalog.dao.jdbc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.junit.Test;

import de.hswt.hrm.catalog.dao.core.ICatalogDao;
import de.hswt.hrm.catalog.model.Catalog;
import de.hswt.hrm.catalog.model.Target;
import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.database.exception.ElementNotFoundException;
import de.hswt.hrm.common.database.exception.SaveException;
import de.hswt.hrm.test.database.AbstractDatabaseTest;

public class TargetDaoTest extends AbstractDatabaseTest {

    private void compareTargetFields(final Target expected, final Target actual) {
        assertEquals("Name not set correctly.", expected.getName(), actual.getName());
        assertEquals("Text not set correctly.", expected.getText(), actual.getText());
    }

    @Test
    public void testInsertTarget() throws ElementNotFoundException, DatabaseException {
        final String name = "TargetName";
        final String text = "Get outta here ...";

        Target expected = new Target(name, text);

        // Check return value from insert
        ICatalogDao catalogDao = new CatalogDao();
        TargetDao dao = new TargetDao(catalogDao);
        Target parsed = dao.insert(expected);
        compareTargetFields(expected, parsed);
        assertTrue("ID not set correctly.", parsed.getId() >= 0);

        // Request from database
        Target requested = dao.findById(parsed.getId());
        compareTargetFields(expected, requested);
        assertEquals("Requested object does not equal parsed one.", parsed, requested);
    }

    @Test
    public void testUpdateTarget() throws ElementNotFoundException, DatabaseException {
        Target tar1 = new Target("FirstTarget", "FirstText");

        ICatalogDao catalogDao = new CatalogDao();
        TargetDao dao = new TargetDao(catalogDao);
        Target parsed = dao.insert(tar1);

        // We add another target to ensure that the update affects just one row.
        Target tar2 = new Target("SecondTarget", "SecondText");
        dao.insert(tar2);

        parsed.setText("Some Test");
        parsed.setName("Some Name");
        dao.update(parsed);

        Target requested = dao.findById(parsed.getId());
        compareTargetFields(parsed, requested);
        assertEquals("Requested object does not equal updated one.", parsed, requested);
    }

    @Test
    public void testFindAllTarget() throws ElementNotFoundException, DatabaseException {
        Target tar1 = new Target("FirstTarget", "FirstText");
        Target tar2 = new Target("SecondTarget", "SecondText");

        ICatalogDao catalogDao = new CatalogDao();
        TargetDao dao = new TargetDao(catalogDao);
        dao.insert(tar1);
        dao.insert(tar2);

        Collection<Target> target = dao.findAll();
        assertEquals("Count of retrieved target does not match.", 2, target.size());
    }
    
    @Test
    public void testConnectCatalogAndCurrentState() throws SaveException {
    	ICatalogDao catalogDao = new CatalogDao();
        TargetDao targetDao = new TargetDao(catalogDao);
        Catalog catalog = new Catalog("Some Catalog");
        Target target = new Target("FirstTarget", "Some Text");
        
        targetDao.addToCatalog(catalog, target);
        
        // FIXME: check if could retrieve the added connection
    }
    
    @Test
    public void testDisconnectCatalogAndCurrentState() throws DatabaseException {
    	ICatalogDao catalogDao = new CatalogDao();
        TargetDao targetDao = new TargetDao(catalogDao);
        Catalog catalog = new Catalog("Some Catalog");
        catalog = catalogDao.insert(catalog);
        Target target = new Target("FirstTarget", "Some Text");
        target = targetDao.insert(target);
        targetDao.addToCatalog(catalog, target);
        
        targetDao.removeFromCatalog(catalog, target);
    }

    @Test
    public void testFindByIdTarget() throws ElementNotFoundException, DatabaseException {
        Target expected = new Target("FirstTarget", "FirstText");
        ICatalogDao catalogDao = new CatalogDao();
        TargetDao dao = new TargetDao(catalogDao);
        Target parsed = dao.insert(expected);

        Target requested = dao.findById(parsed.getId());
        compareTargetFields(expected, requested);
    }

}
