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

    private void compareActivityFields(final Activity expected, final Activity actual) {
        assertEquals("Name not set correctly.", expected.getName(), actual.getName());
        assertEquals("Text not set correctly.", expected.getText(), actual.getText());

    }

    @Test
    public void testInsertActivity() throws ElementNotFoundException, DatabaseException {
        final String name = "ActivityName";
        final String text = "Get outta here ...";

        Activity expected = new Activity(name, text);

        // Check return value from insert
        ActivityDao dao = new ActivityDao();
        Activity parsed = dao.insert(expected);
        compareActivityFields(expected, parsed);
        assertTrue("ID not set correctly.", parsed.getId() >= 0);

        // Request from database
        Activity requested = dao.findById(parsed.getId());
        compareActivityFields(expected, requested);
        assertEquals("Requested object does not equal parsed one.", parsed, requested);
    }

    @Test
    public void testUpdateActivity() throws ElementNotFoundException, DatabaseException {

    }

    @Test
    public void testFindAllActivity() throws ElementNotFoundException, DatabaseException {

    }

    @Test
    public void testFindByIdActivity() throws ElementNotFoundException, DatabaseException {

    }

}
