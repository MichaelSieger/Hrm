package de.hswt.hrm.common.database;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Provides some common JDBC utility methods.
 */
public final class JdbcUtil {
    
    private JdbcUtil() { }

    /**
     * Parses an ID field (or FK).
     * 
     * @param rs ResultSet which contains the column.
     * @param column Name of the column that contains the ID.
     * @return The ID or '-1' if the columns value is null.
     * @throws SQLException 
     */
    public static int getId(final ResultSet rs, final String column) throws SQLException {
        Object value = rs.getObject(column);
        if (value == null) {
            return -1;
        }
        
        return (int) value;
    }
}
