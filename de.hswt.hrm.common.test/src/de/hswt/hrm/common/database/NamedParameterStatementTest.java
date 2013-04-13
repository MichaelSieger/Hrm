package de.hswt.hrm.common.database;

import static org.junit.Assert.*;
import org.junit.Test;

public class NamedParameterStatementTest {
    
    @Test
    public void testSimpleStatements() {
        final String query = "INSERT INTO (Col1, Col2) VALUES (:col1, :col2);";
        
        NamedParameterStatement stmt = new NamedParameterStatement(null, query);
        stmt.addParameter("col1", 5);
        stmt.addParameter("col2", "Some Name");
        
        assertEquals("INSERT INTO (Col1, Col2) VALUES (5, 'Some Name');", stmt.getParsedQuery());
    }
    
}
