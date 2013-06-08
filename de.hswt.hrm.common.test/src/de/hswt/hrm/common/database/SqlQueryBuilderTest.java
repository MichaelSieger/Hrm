package de.hswt.hrm.common.database;

import org.junit.Test;
import static org.junit.Assert.*;

public class SqlQueryBuilderTest {
    @Test
    public void testSimpleSelectStatement() {
        final String expected = "SELECT col1, col2, col3 FROM table;";
        
        SqlQueryBuilder builder = new SqlQueryBuilder();
        final String actual = builder.select("table", "col1", "col2", "col3").toString();
        assertEquals("Wrong select statement build.", expected, actual);
    }
    
    @Test
    public void testSimpleSelectStatementWithEscapedTable() {
        final String expected = "SELECT col1, col2, col3 FROM `table`;";
        
        SqlQueryBuilder builder = new SqlQueryBuilder();
        builder.setEscapeTableName(true);
        final String actual = builder.select("table", "col1", "col2", "col3").toString();
        assertEquals("Wrong select statement build.", expected, actual);
    }
    
    @Test
    public void testSelectStatmentWithWhere() {
        final String expected = "SELECT col1, col2 FROM table WHERE col2 = :col2;";
        
        SqlQueryBuilder builder = new SqlQueryBuilder();
        builder.select("table", "col1", "col2");
        builder.where("col2");
        assertEquals("Wrong select statement build.", expected, builder.toString());
    }
    
    @Test
    public void testSelectStatmentMultipleWhere() {
        final String expected = "SELECT col1, col2 FROM table WHERE col2 = :col2 AND col3 = :col3;";
        
        SqlQueryBuilder builder = new SqlQueryBuilder();
        builder.select("table", "col1", "col2");
        builder.where("col2").and("col3");
        assertEquals("Wrong select statement build.", expected, builder.toString());
    }
    
    @Test
    public void testUpdateStatementWithWhere() {
        final String expected = "UPDATE `table` SET col1 = :col1, col2 = :col2 WHERE id = :id;";
        
        SqlQueryBuilder builder = new SqlQueryBuilder();
        builder.setEscapeTableName(true);
        builder.update("table", "col1", "col2");
        builder.where("id");
        
        assertEquals("Wrong update statement build.", expected, builder.toString());
    }
    
    @Test
    public void testInsertStatement() {
        final String expected = "INSERT INTO `table`(col1, col2) VALUES(:col1, :col2);";
        
        SqlQueryBuilder builder = new SqlQueryBuilder();
        builder.setEscapeTableName(true);
        builder.insert("table", "col1", "col2");
        
        assertEquals("Wrong insert statement build.", expected, builder.toString());
    }
    
    @Test
    public void testJoinedSelectStatement() {
    	final String expected = "SELECT col1 FROM firstTable JOIN secondTable"
    			+ " ON secondTable.colX = firstTable.colY;";
    	
    	SqlQueryBuilder builder = new SqlQueryBuilder();
    	builder.select("firstTable", "col1");
    	builder.join("secondTable", "colY", "colX");
    	
    	assertEquals("Join parsed wrongly.", expected, builder.toString());
    	
    }
}
