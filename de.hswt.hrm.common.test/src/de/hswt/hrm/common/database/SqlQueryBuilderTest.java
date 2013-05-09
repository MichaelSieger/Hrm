package de.hswt.hrm.common.database;

import org.junit.Test;
import static org.junit.Assert.*;

public class SqlQueryBuilderTest {
    @Test
    public void testSimpleSelectStatement() {
        final String expected = "SELECT col1, col2, col3 FROM `table`;";
        
        SqlQueryBuilder builder = new SqlQueryBuilder();
        final String actual = builder.buildSelectStatement("table", "col1", "col2", "col3");
        assertEquals("Wrong select statement build.", expected, actual);
    }
}
