package de.hswt.hrm.common.locking.jdbc;

/**
 * Represents a database lock for a specific id (FK) in a specific table.
 */
public final class Lock {
    private final int id;
    private final String session;
    private final String table;
    private final int fk;
    
    public Lock(final int id, final String session, final String table, final int fk) {
        this.id = id;
        this.session = session;
        this.table = table;
        this.fk = fk;
    }

    public int getId() {
        return id;
    }

    public String getSession() {
        return session;
    }

    public String getTable() {
        return table;
    }

    public int getFk() {
        return fk;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + fk;
        result = prime * result + id;
        result = prime * result + ((session == null) ? 0 : session.hashCode());
        result = prime * result + ((table == null) ? 0 : table.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Lock other = (Lock) obj;
        if (fk != other.fk)
            return false;
        if (id != other.id)
            return false;
        if (session == null) {
            if (other.session != null)
                return false;
        }
        else if (!session.equals(other.session))
            return false;
        if (table == null) {
            if (other.table != null)
                return false;
        }
        else if (!table.equals(other.table))
            return false;
        return true;
    }
    
}
