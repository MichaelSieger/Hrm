package de.hswt.hrm.common.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;

import static com.google.common.base.Preconditions.*;

/**
 * Simple class that enables named parameters in prepared statements.
 * 
 * <p><b>Example Usage:</b>
 * <pre><code>
 * {@code
 * String query = "INSERT INTO (Col1, Col2) VALUES (:col1, :col2);";
 * NamedParameterStatement stmt = NamedParameterStatement.fromConnection(con, query);
 * stmt.addParameter("col1", "Some String");
 * stmt.addParameter("col2", 5);
 * stmt.executeUpdate();
 * stmt.close();
 * }
 * </code></pre>
 * </p>
 */
public class NamedParameterStatement implements AutoCloseable {
	private final static Logger LOG = LoggerFactory.getLogger(NamedParameterStatement.class);
    private final Statement stmt;
    private String query;
    private Map<String, Object> params = new HashMap<>();

    /**
     * @param stmt Underlying statement which should be used.
     * @param query Query containing named parameters.
     */
    public NamedParameterStatement(Statement stmt, String query) {
        this.stmt = stmt;
        
        // Maybe we have to remove this later..
        if (query.indexOf(';') < query.length() -1) {
            throw new IllegalArgumentException("The ';' Character is only allowed at the end of "
                    + "the statement.");
        }
        
        this.query = query;
    }
    
    /**
     * Create a NamedParameterStatement from given connection and query string.
     * 
     * @param con Connection used to create the underlying statement.
     * @param query Query containing named parameters.
     * @throws SQLException
     */
    public static NamedParameterStatement fromConnection(Connection con, String query) 
            throws SQLException {
        checkArgument(!con.isClosed(), "Connection must not be closed to create a statement.");

        return new NamedParameterStatement(con.createStatement(), query);
    }

    /**
     * Create a NamedParameterStatement with given statement and query string.
     * 
     * <p><b>Example Usage:</b>
     * <pre><code>
     * // Select statement (don't retrieve insert ID)
     * String query = "SELECT * FROM Table;";
     * NamedParameterStatement stmt = fromStatement(con.createStatement(), query);
     * // Input statement (retrieve insert ID)
     * String query = "INSERT INTO Table (Col1) VALUE ('SomeValue');";
     * Statement stmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
     * NamedParameterStatement stmt = fromStatement(stmt, query);
     * </code></pre>
     * </p>
     * @see {@link #NamedParameterStatement(Statement, String)}
     */
    public static NamedParameterStatement fromStatement(Statement stmt, String query) {
        return new NamedParameterStatement(stmt , query);
    }
    
    /**
     * Add a value for a named parameter.
     * 
     * @param name Key of the parameter.
     * @param value Value for the parameter.
     */
    public void setParameter(String name, Object value) {
    	if (value instanceof Optional<?>) {
    		LOG.error("Optional value provided as input.");
    		throw new IllegalArgumentException("Value should not be of the type optional.");
    	}
    	
        params.put(name, value);
    }
    
    /**
     * Add a couple of named parameters.
     * 
     * @param params Map of parameters.
     */
    public void setParameter(Map<String, Object> params) {
    	// FIXME: Also check all incomming values for optionals
        this.params.putAll(params);
    }
    
    /**
     * Executes the statement.
     * 
     * @return true if the first result is a {@link ResultSet}
     * @throws SQLException if an error occurred
     * @see PreparedStatement#execute()
     */
    public boolean execute() throws SQLException {
        return stmt.execute(parse(query));
    }

    /**
     * Executes the statement, which must be a query.
     * 
     * @return the query results
     * @throws SQLException if an error occurred
     * @see PreparedStatement#executeQuery()
     */
    public ResultSet executeQuery() throws SQLException {
        return stmt.executeQuery(parse(query));
    }

    /**
     * Executes the statement, which must be an SQL INSERT, UPDATE or DELETE statement;
     * or an SQL statement that returns nothing, such as a DDL statement.
     * 
     * @return number of rows affected
     * @throws SQLException if an error occurred
     * @see PreparedStatement#executeUpdate()
     */
    public int executeUpdate() throws SQLException {
        return stmt.executeUpdate(parse(query));
    }
    
    /**
     * Executes the statement, which must be an SQL INSERT, UPDATE or DELETE statement;
     * or an SQL statement that returns nothing, such as a DDL statement.
     * 
     * @param autoGeneratedKeys Flag indicating whether auto-generated keys should be made 
     *                          available for retrieval; one of the following 
     *                          constants: Statement.RETURN_GENERATED_KEYS 
     *                                     Statement.NO_GENERATED_KEYS
     * @return number of rows affected
     * @throws SQLException
     */
    public int executeUpdate(int autoGeneratedKeys) throws SQLException {
        return stmt.executeUpdate(parse(query), autoGeneratedKeys);
    }
    
    /**
     * @see {@link Statement#getGeneratedKeys()}
     */
    public ResultSet getGeneratedKeys() throws SQLException {
        return stmt.getGeneratedKeys();
    }

    /**
     * Closes the statement.
     * @throws SQLException if an error occurred
     * @see Statement#close()
     */
    public void close() throws SQLException {
    	if (stmt != null && !stmt.isClosed()) {
    		stmt.close();
    	}
    }

    /**
     * Adds the current set of parameters as a batch entry.
     * 
     * @throws SQLException if something went wrong
     */
    public void addBatch() throws SQLException {
        stmt.addBatch(parse(query));
    }

    /**
     * Executes all of the batched statements.
     * 
     * See {@link Statement#executeBatch()} for details.
     * @return update counts for each statement
     * @throws SQLException if something went wrong
     */
    public int[] executeBatch() throws SQLException {
        return stmt.executeBatch();
    }
    
    /**
     * @return The parsed query (named parameters replaced).
     */
    public String getParsedQuery() {
        return parse(query);
    }
    
    private String parse(final String query) {
        StringBuilder parsed = new StringBuilder();
        StringBuilder currentKey = new StringBuilder();
        boolean keyStartFound = false;
        String allowedChars = "_-.";
        
        for (int i = 0, len = query.length(); i < len; i++) {
            char c = query.charAt(i);
            
            if (c == ':' && !keyStartFound) {
                keyStartFound = true;
                continue;
            }
            
            if (keyStartFound) {
            	
                if (Character.isLetterOrDigit(c) ||allowedChars.indexOf(c) >= 0) {
                    currentKey.append(c);
                }
                else {
                    // Key finished
                    keyStartFound = false;
                    if (params.containsKey(currentKey.toString())) {
                        String value = getEscapedString(params.get(currentKey.toString()));
                        parsed.append(value);
                    }
                    else {
                        parsed.append(":").append(currentKey.toString());
                    }

                    // Check if we're at the next colon
                    if (c == ':') {
                        keyStartFound = true;
                    }
                    else {
                        parsed.append(c);
                    }
                    
                    // Create empty key
                    currentKey = new StringBuilder();
                }
            }
            else {
                parsed.append(c);
            }
        }
        
        return parsed.toString();
    }
    
    private String getEscapedString(Object value) {
        if (value == null) {
            return "null";
        }
        
        if (value instanceof Integer
                || value instanceof Long
                || value instanceof Byte
                || value instanceof Short
                || value instanceof Double
                || value instanceof Float) {

            return value.toString();
        }
        else if (value instanceof Boolean) {
            return ((Boolean) value) ? "TRUE" : "FALSE";
        }
        else {
            return "'" + escape(value.toString()) + "'";
        }

    }
    
    private String escape(String query) {
        // Escape single quotes
        query = query.replace("'", "''");
        
        return query;
    }
}
