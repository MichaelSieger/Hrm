package de.hswt.hrm.common.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.google.common.base.Strings.*;

import de.hswt.hrm.common.Config;
import de.hswt.hrm.common.Config.Keys;
import de.hswt.hrm.common.database.exception.DatabaseException;

/**
 * Class that is used to get a database connection. This may be changed to injection later on.
 */
public final class DatabaseFactory {
    private final static Logger LOG = LoggerFactory.getLogger(DatabaseFactory.class);
    
    /**
     * The current connection if running in a transaction.
     */
    private static Connection currentConnection;
    
	private DatabaseFactory() { }
	
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

        Config cfg = Config.getInstance();
        final String host = cfg.getProperty(Keys.DB_HOST, "jdbc:mysql://localhost");
        final String username = cfg.getProperty(Keys.DB_USER, "root");
        final String password = cfg.getProperty(Keys.DB_PASSWORD, "70b145pl4ch7");
        final String database = cfg.getProperty(Keys.DB_NAME, "hrm");
        
        // Build connection String
        String conStr = host;
        if (!isNullOrEmpty(database)) {
            conStr += conStr.endsWith("/") ? database : "/" + database; 
        }
        
        try {
            if (currentConnection != null) {
                return currentConnection;
            }
            
            return DriverManager.getConnection(conStr, username, password);
        }
        catch (SQLException e) {
            // TODO maybe add specific information about the error
            throw new DatabaseException(e);
        }

    }
    
    /**
     * Starts a new transaction. All following calls to {@link #getConnection()} will retrieve
     * the same connection with auto commit disabled. This is will also work if the transaction
     * should be wrapped around a couple of classes that retrieve connections independently.
     * 
     * <p>Example usage:</p>
     * <code><pre>
     * {@code
     * // First we start a new transaction
     * DatabaseFactory.startTransaction();
     * // Then you are able to retrieve the connection within the running transaction
     * Connection con = DatabaseFactory.getConnection();
     * // know do something with the connection ...
     * // and then commit the transaction
     * DatabaseFactory.commitTransaction();
     * // at least close connection that is still opened
     * con.close();
     * }
     * </pre></code>
     * 
     * @throws DatabaseException
     * @throws SQLException
     * @throws IllegalStateException If there is already a transaction running.
     */
    public static void startTransaction() throws DatabaseException, SQLException {
        if (currentConnection != null) {
            throw new IllegalStateException("There is already a transaction running.");
        }
        
        currentConnection = getConnection();
        currentConnection.setAutoCommit(false);
    }
    
    /**
     * Commit the currently running transaction.
     * <p><b>You should then close the connection you have retrieved before!</b></p>
     * 
     * @throws SQLException
     * @throws IllegalStateException If there is no transaction running, or the connection of the
     *                               current transaction is already closed.
     */
    public static void commitTransaction() throws SQLException {
        if (currentConnection == null || currentConnection.isClosed()) {
            throw new IllegalArgumentException("There was no transaction started, or the"
                    + " connection is already closed.");
        }
        
        currentConnection.commit();
        currentConnection.setAutoCommit(true);
        currentConnection = null;
    }
    
    /**
     * Rollback the currently running transaction.
     * <p><b>You should then close the connection you have retrieved before!</b></p>
     * 
     * @throws SQLException
     * @throws IllegalStateException If there is no transaction running, or the connection of the
     *                               current transaction is already closed.
     */
    public static void rollbackTransaction() throws SQLException {
        if (currentConnection == null || currentConnection.isClosed()) {
            throw new IllegalArgumentException("There was no transaction started, or the"
                    + " connection is already closed.");
        }
        
        currentConnection.rollback();
        currentConnection.setAutoCommit(true);
        currentConnection = null;
    }
}
