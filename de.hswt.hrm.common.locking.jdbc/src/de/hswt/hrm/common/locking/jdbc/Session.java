package de.hswt.hrm.common.locking.jdbc;

import java.sql.Timestamp;

public final class Session {
    private final String uuid;
    private final String label;
    private final Timestamp timestamp;
    
    public Session(final String uuid, final String label, final Timestamp timestamp) {
        this.uuid = uuid;
        this.label = label;
        this.timestamp = timestamp;
    }

    public String getUuid() {
        return uuid;
    }

    public String getLabel() {
        return label;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((label == null) ? 0 : label.hashCode());
        result = prime * result + ((timestamp == null) ? 0 : timestamp.hashCode());
        result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
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
        Session other = (Session) obj;
        if (label == null) {
            if (other.label != null)
                return false;
        }
        else if (!label.equals(other.label))
            return false;
        if (timestamp == null) {
            if (other.timestamp != null)
                return false;
        }
        else if (!timestamp.equals(other.timestamp))
            return false;
        if (uuid == null) {
            if (other.uuid != null)
                return false;
        }
        else if (!uuid.equals(other.uuid))
            return false;
        return true;
    }
    
}
