package de.hswt.hrm.common.database;

import static com.google.common.base.Preconditions.checkArgument;

import com.google.common.base.Joiner;

public class SqlQueryBuilder {
    
    private boolean escapeTableName = false;
    
    public SqlQueryBuilder() { }
    
    public SqlQueryBuilder(final boolean escapeTableName) {
        this.escapeTableName = escapeTableName;
    }
    
    public String buildSelectStatement(final String table, final String... columns) {
        checkArgument(columns.length > 0, "You must at least provide one column.");
        
        Joiner joiner = Joiner.on(", ").skipNulls();
        
        StringBuilder query = new StringBuilder();
        query.append("SELECT ");
        query.append(joiner.join(columns));
        query.append(" FROM ");
        
        if (escapeTableName) {
            query.append("`").append(table).append("`");
        }
        else {
            query.append(table);
        }
        
        query.append(";");
        return query.toString();
    }

    public boolean isEscapeTableName() {
        return escapeTableName;
    }

    public void setEscapeTableName(boolean escapeTableName) {
        this.escapeTableName = escapeTableName;
    }
    
    
}
