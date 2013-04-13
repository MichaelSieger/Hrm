package de.hswt.hrm.common.database;

import java.sql.Statement;

import de.hswt.hrm.common.exception.NotImplementedException;

public class NamedParameterStatement {
    private final Statement stmt;
    private String query;
    
    public NamedParameterStatement(Statement stmt, String query) {
        this.stmt = stmt;
        this.query = query;
    }
    
    public static NamedParameterStatement fromStatement(Statement stmt, String query) {
        return new NamedParameterStatement(stmt , query);
    }
    
    private String escape(String query) {
        throw new NotImplementedException();
    }
}
