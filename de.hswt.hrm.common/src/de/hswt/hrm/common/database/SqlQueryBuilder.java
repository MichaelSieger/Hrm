package de.hswt.hrm.common.database;

import static com.google.common.base.Preconditions.checkArgument;

import com.google.common.base.Joiner;

/**
 * Helper to build SQL statements for NamedParameterStatement (given columns are automatically
 * inserted with a corresponding key if necessary).
 * 
 * <p>
 * <b>Usage example:</b> <br />
 * <code><pre>
 * {@code
 * SqlQueryBuilder builder = new SqlQueryBuilder();
 * builder.setEscapeTableName(true);
 * builder.select("table", "col1", "col2");
 * builder.where("col2").and("col3");
 * builder.toString() // -> SELECT col1, col2 FROM `table` WHERE col2 = :col2 AND col3 = :col3;
 * }
 * </pre></code>
 * </p>
 * <p>
 * Runtime exceptions are thrown on improper usage.<br />
 * E. g.:
 * <code><pre>
 * {@code
 * SqlQueryBuilder builder = new SqlQueryBuilder();
 * builder.select("table", "col1");
 * builder.update("table", "col2"); // -> throws IllegalStateException as select was called first
 * 
 * builder = new SqlQueryBuilder();
 * builder.insert("table", "col1");
 * builder.where("id"); // -> throws IllegalStateException as where doesn't make sense on insert statement
 * }
 * </pre></code>
 * </p>
 */
public class SqlQueryBuilder {
    private StringBuilder query = new StringBuilder();
    private Type type = Type.UNSET;
    private boolean escapeTableName = false;
    
    public SqlQueryBuilder() { }
    
    /**
     * Start building a SELECT statement.
     * @param table FROM part of the statement.
     * @param columns Which columns should be selected.
     * @return
     * 
     * @throws IllegalArgumentException if no column is given.
     * @throws IllegalStateException if another statement type was started (check usage).
     */
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
    
    /**
     * Start building an UPDATE statement.
     * @param table Table which should be updated.
     * @param columns Which columns should be updated.
     * @return
     * 
     * @throws IllegalArgumentException if no column is given.
     * @throws IllegalStateException if another statement type was started (check usage).
     */
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
    
    /**
     * Start building an INSERT statement.
     * @param table Where should be inserted to.
     * @param columns Which should be set.
     * @return
     * 
     * @throws IllegalArgumentException if no column is given.
     * @throws IllegalStateException if another statement type was started (check usage).
     */
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
    
    /**
     * Add WHERE clause to statement.
     * 
     * @param column Column which should be checked.
     * @return
     * 
     * @throws IllegalStateException if no statement was started (check usage).
     * @throws IllegalStateException if an UPDATE statement was startet.
     */
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
    
    /**
     * Add AND to statement.
     * @param column Column which should be checked.
     * @return
     * 
     * @throws IllegalStateException if no statement was started (check usage).
     * @throws IllegalStateException if an UPDATE statement was startet.
     */
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
    
    /**
     * Add OR to statement.
     * @param column Column which should be checked.
     * @return
     * 
     * @throws IllegalStateException if no statement was started (check usage).
     * @throws IllegalStateException if an UPDATE statement was startet.
     */
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
    
    /**
     * Build the query. Will return an empty string if no statement was started."
     */
    @Override
    public String toString() {
        if (type == Type.UNSET) {
            return "";
        }
        
        query.append(";");
        return query.toString();
    }
    
    public boolean isEscapeTableName() {
        return escapeTableName;
    }

    /**
     * Set if table name should be escaped (.. Table .. vs. .. `Table` ..).
     * 
     * @param escapeTableName
     */
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
