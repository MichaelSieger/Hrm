package de.hswt.hrm.catalog.dao.jdbc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

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

        Activity act1 = new Activity("FirstActivity", "FirstText");

        ActivityDao dao = new ActivityDao();
        Activity parsed = dao.insert(act1);

        // We add another contact to ensure that the update affects just one row.
        Activity act2 = new Activity("SecondActivity", "SecondText");
        dao.insert(act2);

        parsed.setText("Some City");
        parsed.setName("someone@example.com");
        dao.update(parsed);

        Activity requested = dao.findById(parsed.getId());
        compareActivityFields(parsed, requested);
        assertEquals("Requested object does not equal updated one.", parsed, requested);

    }

    @Test
    public void testFindAllActivity() throws ElementNotFoundException, DatabaseException {
        Activity act1 = new Activity("FirstActivity", "FirstText");
        Activity act2 = new Activity("SecondActivity", "SecondText");

        ActivityDao dao = new ActivityDao();
        dao.insert(act1);
        dao.insert(act2);

        Collection<Activity> activity = dao.findAll();
        assertEquals("Count of retrieved activities does not match.", 2, activity.size());
    }

    @Test
    public void testFindByIdActivity() throws ElementNotFoundException, DatabaseException {
        Activity expected = new Activity("FirstActivity", "FirstText");
        ActivityDao dao = new ActivityDao();
        Activity parsed = dao.insert(expected);

        Activity requested = dao.findById(parsed.getId());
        compareActivityFields(expected, requested);

    }

}
