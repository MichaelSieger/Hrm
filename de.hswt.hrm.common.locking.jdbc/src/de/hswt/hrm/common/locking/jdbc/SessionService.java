package de.hswt.hrm.common.locking.jdbc;

import java.sql.SQLException;

import de.hswt.hrm.common.database.exception.DatabaseException;

public final class SessionService {
    private static final SessionDao DAO = SessionDao.getInstance();
    private static SessionService instance;
    
    
    private SessionService() { }
    
    public static SessionService getInstance() {
        if (instance == null) {
            instance = new SessionService();
        }
        
        return instance;
    }
    
    public Session startSession(final String label) throws DatabaseException, SQLException {
        return DAO.insert(label);
    }
    
    public void endSession(Session session) throws SQLException, DatabaseException {
        DAO.delete(session.getUuid());
    }
    
    public Session findSessionByUuid(final String uuid) throws DatabaseException, SQLException {
        return DAO.findByUuid(uuid);
    }
}
