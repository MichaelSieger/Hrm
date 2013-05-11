package de.hswt.hrm.common.locking.jdbc;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;

import de.hswt.hrm.common.database.exception.DatabaseException;
import static com.google.common.base.Preconditions.checkArgument;


public class LockService implements ILockService {
    private final static Logger LOG = LoggerFactory.getLogger(LockService.class);
    private static final LockDao DAO = LockDao.getInstance();
    private final String session;
    
    public LockService() throws DatabaseException, SQLException {
        SessionService sessionService = SessionService.getInstance();
        // TODO: Change session label to computer- / username
        Session session = sessionService.startSession("Session from LockService ctor");
        
        this.session = session.getUuid();
    }
    
    public LockService(final String session) {
        this.session = session;
    }
    
    public Optional<Lock> getLock(final String table, final int fk) {
        try {
            Lock lock = DAO.insert(session, table, fk);
            return Optional.of(lock);
        }
        catch (DatabaseException|SQLException e) {
            LOG.error("Error while trying to get lock.", e);
            return Optional.absent();
        }
    }
    
    public boolean release(Lock lock) {
        try {
            DAO.delete(lock.getSession(), lock.getTable(), lock.getFk());
            return true;
        }
        catch (SQLException | DatabaseException e) {
            LOG.error("Error while trying to release lock.", e);
            return false;
        }
    }
    
    public boolean release(Optional<Lock> lock) {
        checkArgument(lock.isPresent(), "Cannot release non existing lock.");
        return release(lock.get());
    }
    
    // List of possible tables
    public static final String TBL_PLANT = "Plant"; 
}
