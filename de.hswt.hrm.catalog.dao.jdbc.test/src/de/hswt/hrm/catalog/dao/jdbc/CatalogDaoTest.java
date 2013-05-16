package de.hswt.hrm.catalog.dao.jdbc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.junit.Test;

import de.hswt.hrm.catalog.model.Catalog;
import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.database.exception.ElementNotFoundException;
import de.hswt.hrm.test.database.AbstractDatabaseTest;

public class CatalogDaoTest extends AbstractDatabaseTest {

    private void compareCatalogFields(final Catalog expected, final Catalog actual) {
        assertEquals("Name not set correctly.", expected.getName(), actual.getName());

    }

    @Test
    public void testInsertCatalog() throws ElementNotFoundException, DatabaseException {
        final String name = "CatalogName";

        Catalog expected = new Catalog(name);

        // Check return value from insert
        CatalogDao dao = new CatalogDao();
        Catalog parsed = dao.insert(expected);
        compareCatalogFields(expected, parsed);
        assertTrue("ID not set correctly.", parsed.getId() >= 0);

        // Request from database
        Catalog requested = dao.findById(parsed.getId());
        compareCatalogFields(expected, requested);
        assertEquals("Requested object does not equal parsed one.", parsed, requested);
    }

    @Test
    public void testUpdateCatalog() throws ElementNotFoundException, DatabaseException {
        Catalog cat1 = new Catalog("FirstCatalog");

        CatalogDao dao = new CatalogDao();
        Catalog parsed = dao.insert(cat1);

        // We add another catalog to ensure that the update affects just one row.
        Catalog cat2 = new Catalog("SecondCatalog");
        dao.insert(cat2);

        parsed.setName("Some Name");
        dao.update(parsed);

        Catalog requested = dao.findById(parsed.getId());
        compareCatalogFields(parsed, requested);
        assertEquals("Requested object does not equal updated one.", parsed, requested);
    }

    @Test
    public void testFindAllCatalog() throws ElementNotFoundException, DatabaseException {
        Catalog cat1 = new Catalog("FirstCatalog");
        Catalog cat2 = new Catalog("SecondCatalog");

        CatalogDao dao = new CatalogDao();
        dao.insert(cat1);
        dao.insert(cat2);

        Collection<Catalog> catalog = dao.findAll();
        assertEquals("Count of retrieved catalogs does not match.", 2, catalog.size());
    }

    @Test
    public void testFindByIdCatalog() throws ElementNotFoundException, DatabaseException {
        Catalog expected = new Catalog("FirstCatalog");
        CatalogDao dao = new CatalogDao();
        Catalog parsed = dao.insert(expected);

        Catalog requested = dao.findById(parsed.getId());
        compareCatalogFields(expected, requested);
    }

}
