package de.hswt.hrm.common.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import de.hswt.hrm.common.database.exception.DatabaseException;

/**
 * Class that is used to get a database connection. This may be changed to injection later on.
 */
public class DatabaseFactory {
    private static Connection con;
    
    /**
     * Returns a connection object for the database. This is a singleton (will only create the
     * connection once and returns the same instance on all later calls).
     * 
     * @return Connection object for the database.
     * @throws DatabaseException If connection could not be created.
     */
    public static Connection getConnection() throws DatabaseException {
        // load mariadb driver
        try {
            Class.forName("org.mariadb.jdbc.Driver.class");
        }
        catch (ClassNotFoundException e) {
            throw new DatabaseException("Database driver not found.");
        }
        
        // TODO load connection string from configuration
        String config = "";
        
        if (con == null) {
            try {
                con = DriverManager.getConnection(config);
            }
            catch (SQLException e) {
                // TODO maybe add specific information about the error
                throw new DatabaseException(e);
            }
        }
        
        return con;
    }
}
