package de.hswt.hrm.catalog.dao.jdbc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.database.exception.ElementNotFoundException;
import de.hswt.hrm.catalog.dao.jdbc.ActivityDao;
import de.hswt.hrm.catalog.model.Activity;
import de.hswt.hrm.test.database.AbstractDatabaseTest;

public class ActivityDaoTest extends AbstractDatabaseTest {

    @Test
    public void testInsertActivity() throws ElementNotFoundException, DatabaseException {
        final String name = "ActivityName";
        final String text = "Get outta here ...";

        Activity a = new Activity(name, text);

        ActivityDao dao = new ActivityDao();
        Activity inserted = dao.insert(a);
        assertTrue("ID is invalid.", inserted.getId() >= 0);
        assertEquals("Name set wrong.", name, inserted.getName());
        assertEquals("Text set wrong.", text, inserted.getText());

        // Freshly retrieve vom DB
        Activity requested = dao.findById(inserted.getId());

        // Check fields
        assertEquals("Name not saved correctly.", name, requested.getName());
        assertEquals("Text not saved correctly.", text, requested.getText());

        // Check equals
        assertTrue("Queried object does not equal inserted one.", requested.equals(inserted));
    }

}
