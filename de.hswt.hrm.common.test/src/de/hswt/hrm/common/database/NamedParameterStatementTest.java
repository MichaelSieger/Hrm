package de.hswt.hrm.common.database;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

public class NamedParameterStatementTest {
    
    @Test
    public void testSimpleStatements() throws SQLException {
        final String query = "INSERT INTO (Col1, Col2) VALUES (:col1, :col2);";
        
        try (NamedParameterStatement stmt = new NamedParameterStatement(null, query)) {
	        stmt.setParameter("col1", 5);
	        stmt.setParameter("col2", "Some Name");
	        
	        assertEquals("Simple statement could not be parsed correctly.",
	                "INSERT INTO (Col1, Col2) VALUES (5, 'Some Name');", 
	                stmt.getParsedQuery());
        }
    }
    
    @Test
    public void testWithSamePrefixes() throws SQLException {
        final String query = "INSERT INTO (Col1, Col2, Col3) VALUES (:col1, :prefix, :prefixExt);";
        
        try (NamedParameterStatement stmt = new NamedParameterStatement(null, query)) {
	        stmt.setParameter("col1", 5);
	        stmt.setParameter("prefix", "val2");
	        stmt.setParameter("prefixExt", "val3");
	        
	        assertEquals("Prefixed keys not replaced correctly.",
	                "INSERT INTO (Col1, Col2, Col3) VALUES (5, 'val2', 'val3');", 
	                stmt.getParsedQuery());
        }
    }
    
}