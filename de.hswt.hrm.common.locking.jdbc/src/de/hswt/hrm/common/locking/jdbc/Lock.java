package de.hswt.hrm.common.locking.jdbc;

import java.sql.Timestamp;

/**
 * Represents a database lock for a specific id (FK) in a specific table.
 */
public final class Lock {
    private final String session;
    private final String table;
    private final int fk;
    private final Timestamp timestamp;
    
    public Lock(final String session, final String table, final int fk,
            final Timestamp timestamp) {
        
        this.session = session;
        this.table = table;
        this.fk = fk;
        this.timestamp = timestamp;
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

    public Timestamp getTimestamp() {
        return timestamp;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + fk;
        result = prime * result + ((session == null) ? 0 : session.hashCode());
        result = prime * result + ((table == null) ? 0 : table.hashCode());
        result = prime * result + ((timestamp == null) ? 0 : timestamp.hashCode());
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
        if (timestamp == null) {
            if (other.timestamp != null)
                return false;
        }
        else if (!timestamp.equals(other.timestamp))
            return false;
        return true;
    }
    
}
