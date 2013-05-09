package de.hswt.hrm.common.database;

import static com.google.common.base.Preconditions.checkArgument;

import com.google.common.base.Joiner;

public class SqlQueryBuilder {
    private StringBuilder query = new StringBuilder();
    private Type type = Type.UNSET;
    private boolean escapeTableName = false;
    
    public SqlQueryBuilder() { }
    
    public SqlQueryBuilder select(final String table, final String... columns) {
        checkArgument(columns.length > 0, "You must at least provide one column.");
        
        if (type != Type.UNSET) {
            throw new IllegalStateException("You can only start one type of query.");
        }
        
        type = Type.SELECT;
        
        Joiner joiner = Joiner.on(", ").skipNulls();
        
        query.append("SELECT ");
        joiner.appendTo(query, columns);
        query.append(" FROM ").append(escapeTableName(table));
        
        return this;
    }
    
    private String escapeTableName(final String table) {
        if (escapeTableName) {
            return "`" + table + "`";
        }
        else {
            return table;
        }
    }
    
    public SqlQueryBuilder update(final String table, final String... columns) {
        checkArgument(columns.length > 0, "You must at least provide one column.");
        
        if (type != Type.UNSET) {
            throw new IllegalStateException("You can only start one type of query.");
        }
        
        type = Type.UPDATE;
        
        query.append("UPDATE ").append(escapeTableName(table));
        query.append(" SET ");
        
        for (String col : columns) {
            query.append(col).append(" = :").append(col).append(", ");
        }
        query.delete(query.length() -2, query.length());
        
        return this;
    }
    
    public SqlQueryBuilder insert(final String table, final String... columns) {
        checkArgument(columns.length > 0, "You must at least provide one column.");
        
        if (type != Type.UNSET) {
            throw new IllegalStateException("You can only start one type of query.");
        }
        
        type = Type.INSERT;
        
        Joiner joiner = Joiner.on(", ").skipNulls();
        
        query.append("INSERT INTO ").append(escapeTableName(table));
        query.append("(");
        joiner.appendTo(query, columns);
        query.append(")");
        query.append(" VALUES(");
        
        for (String col : columns) {
            query.append(":").append(col).append(", ");
        }
        query.delete(query.length() -2, query.length());
        query.append(")");
        
        return this;
    }
    
    public SqlQueryBuilder where(final String column) {
        if (type == Type.UNSET) {
            throw new IllegalStateException("You have to start with a call to 'select' or"
                    + " 'update'");
        }
        else if (type == Type.INSERT) {
            throw new IllegalStateException("Where cannot be used on a insert statement.");
        }
        
        query.append(" WHERE ");
        query.append(column).append(" = :").append(column);
        
        return this;
    }
    
    public SqlQueryBuilder and(final String column) {
        if (type == Type.UNSET) {
            throw new IllegalArgumentException("You have to start with a call to 'select' or"
                    + " 'update'");
        }
        else if (type == Type.INSERT) {
            throw new IllegalStateException("And cannot be used on a insert statement.");
        }
        
        query.append(" AND ");
        query.append(column).append(" = :").append(column);
        
        return this;
    }
    
    public SqlQueryBuilder or(final String column) {
        if (type == Type.UNSET) {
            throw new IllegalArgumentException("You have to start with a call to 'select' or"
                    + " 'update'");
        }
        else if (type == Type.INSERT) {
            throw new IllegalStateException("Or cannot be used on a insert statement.");
        }
        
        query.append(" OR ");
        query.append(column).append(" = :").append(column);
        
        return this;
    }
    
    @Override
    public String toString() {
        query.append(";");
        return query.toString();
    }
    
    public boolean isEscapeTableName() {
        return escapeTableName;
    }

    public void setEscapeTableName(boolean escapeTableName) {
        if (type != Type.UNSET) {
            throw new IllegalStateException("You can only change escape mode if statement"
                    + " generation has not startet.");
        }
        
        this.escapeTableName = escapeTableName;
    }
    
    private static enum Type {
        SELECT,
        INSERT,
        UPDATE,
        UNSET
    }
}
