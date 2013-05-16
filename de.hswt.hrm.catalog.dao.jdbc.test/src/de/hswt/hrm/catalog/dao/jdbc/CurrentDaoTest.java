package de.hswt.hrm.catalog.dao.jdbc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import de.hswt.hrm.catalog.model.Current;
import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.database.exception.ElementNotFoundException;
import de.hswt.hrm.test.database.AbstractDatabaseTest;

public class CurrentDaoTest extends AbstractDatabaseTest {

    private void compareCurrentFields(final Current expected, final Current actual) {
        assertEquals("Name not set correctly.", expected.getName(), actual.getName());
        assertEquals("Text not set correctly.", expected.getText(), actual.getText());

    }

    @Test
    public void testInsertCurrent() throws ElementNotFoundException, DatabaseException {
        final String name = "CurrentName";
        final String text = "Get outta here ...";

        Current expected = new Current(name, text);

        // Check return value from insert
        CurrentDao dao = new CurrentDao();
        Current parsed = dao.insert(expected);
        compareCurrentFields(expected, parsed);
        assertTrue("ID not set correctly.", parsed.getId() >= 0);

        // Request from database
        Current requested = dao.findById(parsed.getId());
        compareCurrentFields(expected, requested);
        assertEquals("Requested object does not equal parsed one.", parsed, requested);
    }

    @Test
    public void testUpdateCurrent() throws ElementNotFoundException, DatabaseException {
        Current cur1 = new Current("FirstCurrent", "FirstText");

        CurrentDao dao = new CurrentDao();
        Current parsed = dao.insert(cur1);

        // We add another current to ensure that the update affects just one row.
        Current cur2 = new Current("SecondCurrent", "SecondText");
        dao.insert(cur2);

        parsed.setText("Some Test");
        parsed.setName("Some Name");
        dao.update(parsed);

        Current requested = dao.findById(parsed.getId());
        compareCurrentFields(parsed, requested);
        assertEquals("Requested object does not equal updated one.", parsed, requested);
    }

    @Test
    public void testFindAllCurrent() throws ElementNotFoundException, DatabaseException {

    }

    @Test
    public void testFindByIdCurrent() throws ElementNotFoundException, DatabaseException {

    }

}
