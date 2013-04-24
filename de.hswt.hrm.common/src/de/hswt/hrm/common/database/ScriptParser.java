package de.hswt.hrm.common.database;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.google.common.base.Joiner;
import static com.google.common.base.Strings.isNullOrEmpty;;

/**
 * Can be used to execute a .sql script or multiple statements (without static data) at once.
 */
public class ScriptParser {
    private static final String DELIMITER = ";";
    private Statement stmt;
    
    /**
     * Create a new ScriptParser that adds parsed queries to the given statement.
     * 
     * @param stmt Statement that will be filled with parsed queries.
     */
    public ScriptParser(Statement stmt) {
        this.stmt = stmt;
    }
    
    /**
     * Parse a .sql style text file.
     * 
     * @param path Path to the file.
     * @return Statement that has all queries applied as batch.
     * @throws IOException
     * @throws SQLException
     */
    public Statement parse(Path path) throws IOException, SQLException {
        return parse(loadFile(path));
    }
    
    /**
     * Parse a string with multiple sql queries (separated by ';').
     * 
     * @param sql Sql queries.
     * @return Statement that has all queries applied as batch.
     * @throws SQLException
     */
    public Statement parse(String sql) throws SQLException {
        String[] lines = sql.split(System.lineSeparator());
        return parse(new ArrayList<>(Arrays.asList(lines)));
    }
    
    private Statement parse(List<String> lines) throws SQLException {
        stripComments(lines);
        String[] stmts = splitStatements(lines);
        
        for (String sql : stmts) {
            if (isNullOrEmpty(sql.trim())) {
                continue;
            }
            
            if (!sql.endsWith(DELIMITER)) {
                sql += DELIMITER;
            }
            
            stmt.addBatch(sql);
        }
        
        return stmt;
    }
    
    private void stripComments(List<String> sql) {
        // Remove comments (beginning with "--")
        for (Iterator<String> iter = sql.iterator(); iter.hasNext(); ) {
            String l = iter.next();
            if (l.trim().startsWith("--")) {
                iter.remove();
            }
        }
    }
    
    private String[] splitStatements(List<String> sql) {
        // Join all lines and break on delimiter (create array of statements)
        String content = Joiner.on(" ").join(sql);
        return content.split(DELIMITER);
    }
   
    private List<String> loadFile(Path path) throws IOException {
        List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
        return lines;
    }
}
