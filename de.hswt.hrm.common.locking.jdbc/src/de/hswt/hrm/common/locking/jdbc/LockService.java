package de.hswt.hrm.common.locking.jdbc;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;
import com.google.common.base.Strings;

import de.hswt.hrm.common.Config;
import de.hswt.hrm.common.database.exception.DatabaseException;
import static com.google.common.base.Preconditions.checkArgument;


public class LockService implements ILockService {
    private final static Logger LOG = LoggerFactory.getLogger(LockService.class);
    private static final LockDao DAO = LockDao.getInstance();
    private final String sessionUuid;
    
    // TODO: Maybe its better to just throw an error if no session is available
    // and put session creation to somewhere else ...
    public LockService() throws DatabaseException, SQLException {
        // Check if we have already a running session or want to start a new one
        Config cfg = Config.getInstance();
        String uuid = cfg.getProperty(Config.Keys.SESSION_UUID, null);
        
        SessionService sessionService = SessionService.getInstance();
        String sessionUuid = null;
        if (!Strings.isNullOrEmpty(uuid)) {
            // Try to find session in DB -> ensures that we have a valid session
            try {
                Session session = sessionService.findSessionByUuid(uuid);
                sessionUuid = session.getUuid();
            }
            catch (DatabaseException | SQLException e) {
                LOG.error("Session in config is invalid.", e);
            }
        }
        
        if (Strings.isNullOrEmpty(sessionUuid)) {
            // We have no valid session -> create a new one
            // TODO: Change session label to computer- / username
            Session session = sessionService.startSession("Lavel from LockService ctor");
            sessionUuid = session.getUuid();
            cfg.setProperty(Config.Keys.SESSION_UUID, session.getUuid());
        }
        
        this.sessionUuid = sessionUuid;
    }
    
    public LockService(final String session) {
        this.sessionUuid = session;
    }
    
    @Override
    public boolean hasLockFor(String table, int fk) {
        try {
            Lock lock = DAO.findByLock(sessionUuid, table, fk);
            if (lock == null) {
                return false;
            }
            
            if (lock.getSession().equals(sessionUuid)
                && lock.getTable().equals(table)
                && lock.getFk() == fk) {
                    return true;
            }
        }
        catch (SQLException | DatabaseException e) {
            LOG.error("Error retrieving lock (check for lock).", e);
        }
        
        return false;
    } 
    
    @Override
    public Optional<Lock> getLock(final String table, final int fk) {
        try {
            Lock lock = DAO.insert(sessionUuid, table, fk);
            return Optional.of(lock);
        }
        catch (DatabaseException|SQLException e) {
            LOG.error("Error while trying to get lock.", e);
            return Optional.absent();
        }
    }
    
    @Override
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
    
    @Override
    public boolean release(Optional<Lock> lock) {
        checkArgument(lock.isPresent(), "Cannot release non existing lock.");
        return release(lock.get());
    }
}
