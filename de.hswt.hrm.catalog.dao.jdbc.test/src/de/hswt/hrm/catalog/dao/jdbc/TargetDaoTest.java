package de.hswt.hrm.catalog.dao.jdbc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import de.hswt.hrm.catalog.model.Target;
import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.database.exception.ElementNotFoundException;
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
        TargetDao dao = new TargetDao();
        Target parsed = dao.insert(expected);
        compareTargetFields(expected, parsed);
        assertTrue("ID not set correctly.", parsed.getId() >= 0);

        // Request from database
        Target requested = dao.findById(parsed.getId());
        compareTargetFields(expected, requested);
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
