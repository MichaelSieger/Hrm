package de.hswt.hrm.common.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import de.hswt.hrm.common.database.exception.DatabaseException;

/**
 * Class that is used to get a database connection. This may be changed to injection later on.
 */
public class DatabaseFactory {
    /**
     * Returns a connection object for the database.
     * 
     * @return Connection object for the database.
     * @throws DatabaseException If connection could not be created.
     */
    public static Connection getConnection() throws DatabaseException {
        // load mariadb driver
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        }
        catch (ClassNotFoundException e) {
            throw new DatabaseException("Database driver not found.", e);
        }
        
        // TODO load connection string from configuration
        String config = "jdbc:mysql://10.154.4.20";
        String username = "root";
        String password = "70b145pl4ch7";
        
        try {
            Connection con = DriverManager.getConnection(config, username, password);
            return con;
        }
        catch (SQLException e) {
            // TODO maybe add specific information about the error
            throw new DatabaseException(e);
        }

    }
}
