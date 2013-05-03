package de.hswt.hrm.common.locking.jdbc;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Strings.isNullOrEmpty;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.dbutils.DbUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hswt.hrm.common.database.DatabaseFactory;
import de.hswt.hrm.common.database.NamedParameterStatement;
import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.database.exception.ElementNotFoundException;
import de.hswt.hrm.common.database.exception.SaveException;

final class LockDao {
    private final static Logger LOG = LoggerFactory.getLogger(LockDao.class);
    private static LockDao instance;
    
    private LockDao() { }
    
    public static LockDao getInstance() {
        if (instance == null) {
            instance = new LockDao();
        }
        
        return instance;
    }
    
    public Collection<Lock> findAll() throws SQLException, DatabaseException {
        final String query =
                "SELECT Lock_Uuid_Fk, Lock_Table, Lock_Row_ID, Lock_Timestamp "
                        + "FROM `Lock`;";
        
        try (Connection con = DatabaseFactory.getConnection()) {
            try (NamedParameterStatement stmt = NamedParameterStatement.fromConnection(con, query)) {
                ResultSet result = stmt.executeQuery();
                
                Collection<Lock> locks = fromResultSet(result);
                DbUtils.closeQuietly(result);
                
                return locks;
            }
        }
    }
    
    public Lock findByLock(final String session, final String table, final int fk) 
            throws SQLException, DatabaseException {
        
        if (isNullOrEmpty(session)) {
            throw new IllegalStateException("Session must not be empty.");
        }
        
        if (isNullOrEmpty(table)) {
            throw new IllegalStateException("Table must not be empty");
        }
        
        checkArgument(fk >= 0, "Foreign key must not be negative.");
        
        final String query =
                "SELECT Lock_Uuid_Fk, Lock_Table, Lock_Row_ID, Lock_Timestamp "
                        + "FROM `Lock` "
                        + "WHERE Lock_Uuid_Fk = :uuid "
                        + "AND Lock_Table = :table "
                        + "AND Lock_Row_ID = :fk;";
        
        try (Connection con = DatabaseFactory.getConnection()) {
            try (NamedParameterStatement stmt = NamedParameterStatement.fromConnection(con, query)) {
                stmt.setParameter("uuid", session);
                stmt.setParameter("table", table);
                stmt.setParameter("fk", fk);
                
                ResultSet result = stmt.executeQuery();
                
                Collection<Lock> locks = fromResultSet(result);
                DbUtils.closeQuietly(result);
                
                if (locks.size() < 1) {
                    throw new ElementNotFoundException();
                }
                else if (locks.size() > 1) {
                    throw new DatabaseException("Internal error, lock is not unique.");
                }
                
                return locks.iterator().next();
            }
        }
    }
    
    public Lock insert(final String session, final String table, final int fk) 
            throws SQLException, DatabaseException {
        
        if (isNullOrEmpty(session)) {
            throw new IllegalStateException("Session must not be empty.");
        }
        
        if (isNullOrEmpty(table)) {
            throw new IllegalStateException("Table must not be empty");
        }
        
        checkArgument(fk >= 0, "Foreign key must not be negative.");
        
        final String query =
                "INSERT INTO `Lock` (Lock_Uuid_Fk, Lock_Table, Lock_Row_ID) "
                        + "VALUES (:uuid, :table, :fk);";
        
        try (Connection con = DatabaseFactory.getConnection()) {
            try (NamedParameterStatement stmt = NamedParameterStatement.fromConnection(con, query)) {
                stmt.setParameter("uuid", session);
                stmt.setParameter("table", table);
                stmt.setParameter("fk", fk);
                
                int affectedRows = stmt.executeUpdate();
                
                if (affectedRows != 1) {
                    throw new SaveException();
                }
                
                return findByLock(session, table, fk);
            }
        }
    }
    
    public void delete(final String session, final String table, final int fk) 
            throws SQLException, DatabaseException {
        
        if (isNullOrEmpty(session)) {
            throw new IllegalStateException("Session must not be empty.");
        }
        
        if (isNullOrEmpty(table)) {
            throw new IllegalStateException("Table must not be empty");
        }
        
        checkArgument(fk >= 0, "Foreign key must not be negative.");
        
        final String query =
                "DELETE FROM `Lock` "
                        + "WHERE Lock_Uuid_Fk = :uuid "
                        + "AND Lock_Table = :table "
                        + "AND Lock_Row_ID = :fk;";
        
        try (Connection con = DatabaseFactory.getConnection()) {
            try (NamedParameterStatement stmt = NamedParameterStatement.fromConnection(con, query)) {
                stmt.setParameter("uuid", session);
                stmt.setParameter("table", table);
                stmt.setParameter("fk", fk);
                
                // Disable autocommit to use a transaction
                con.setAutoCommit(false);
                
                int affectedRows = stmt.executeUpdate();
                if (affectedRows == 1) {
                    con.commit();
                }
                else {
                    // Damn we couldn't release the lock correctly
                    con.rollback();
                    
                    LOG.error("Tried to delete (release) lock, but the DELETE statement "
                            + "affected " + affectedRows + " instead of 1!");
                    throw new DatabaseException("Could not release lock correctly.");
                }
            }
        }
    }
    
    private Collection<Lock> fromResultSet(ResultSet rs) throws SQLException {
        checkNotNull(rs, "Result must not be null.");
        Collection<Lock> lockList = new ArrayList<>();

        while (rs.next()) {
            String session = rs.getString("Lock_Uuid_Fk");
            String table = rs.getString("Lock_Table");
            int fk = rs.getInt("Lock_Row_ID");
            Timestamp timestamp = rs.getTimestamp("Lock_Timestamp");
            
            Lock lock = new Lock(session, table, fk, timestamp);

            lockList.add(lock);
        }

        return lockList;
    }
    
}
