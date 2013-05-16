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

    }

    @Test
    public void testFindAllActivity() throws ElementNotFoundException, DatabaseException {

    }

    @Test
    public void testFindByIdActivity() throws ElementNotFoundException, DatabaseException {

    }

}
