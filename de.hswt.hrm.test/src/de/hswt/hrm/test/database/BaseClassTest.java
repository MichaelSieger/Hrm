package de.hswt.hrm.test.database;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.Test;

import de.hswt.hrm.common.Config;
import de.hswt.hrm.common.Config.Keys;
import de.hswt.hrm.common.database.DatabaseFactory;
import de.hswt.hrm.common.database.exception.DatabaseException;

public class BaseClassTest extends AbstractDatabaseTest {

    @Test
    public void testCorrectDatabaseName() {
        final String dbName = Config.getInstance().getProperty(Keys.DB_NAME);
        assertTrue("Database name not correctly configured.", dbName.startsWith("hrmtest_"));
    }
    
    @Test
    public void testConnectionIsValid() throws SQLException, DatabaseException {
        try (Connection con = DatabaseFactory.getConnection()) {
            assertFalse("Connection closed after creation.", con.isClosed());
            
            final String dbName = Config.getInstance().getProperty(Keys.DB_NAME);
            assertEquals("Connection to the wrong database.", dbName, con.getCatalog());
        }
    }
    
}
