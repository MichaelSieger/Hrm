package de.hswt.hrm.catalog.dao.jdbc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

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
    public void testUpdateTarget() throws ElementNotFoundException, DatabaseException {
        Target tar1 = new Target("FirstTarget", "FirstText");

        TargetDao dao = new TargetDao();
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

        TargetDao dao = new TargetDao();
        dao.insert(tar1);
        dao.insert(tar2);

        Collection<Target> target = dao.findAll();
        assertEquals("Count of retrieved target does not match.", 2, target.size());
    }

    @Test
    public void testFindByIdTarget() throws ElementNotFoundException, DatabaseException {

    }

}
