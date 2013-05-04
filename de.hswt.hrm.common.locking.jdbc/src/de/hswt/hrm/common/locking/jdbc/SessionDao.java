package de.hswt.hrm.common.locking.jdbc;

final class SessionDao {
    private static SessionDao instance;
    
    private SessionDao() { }
    
    public static SessionDao getInstance() {
        if (instance == null) {
            instance = new SessionDao();
        }
        
        return instance;
    }

}
