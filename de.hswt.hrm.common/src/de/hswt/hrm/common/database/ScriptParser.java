package de.hswt.hrm.common.database;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.google.common.base.Joiner;

public class ScriptParser {
    private final String DELIMITER = ";";
    private Statement stmt;
    
    public ScriptParser(Statement stmt) {
        this.stmt = stmt;
    }
    
    public Statement parse(Path path) throws IOException, SQLException {
        return parse(loadFile(path));
    }

    public Statement parse(List<String> sql) throws SQLException {
        splitStatements(sql);
        return parse(Joiner.on("").join(sql));
    }
    
    public Statement parse(String sql) throws SQLException {
        List<String> lines = Arrays.asList(sql.split("\n"));
        stripComments(lines);
        return parse(splitStatements(lines));
        
        
    }
    
    private Statement parse(String[] stmts) throws SQLException {
        for (String s : stmts) {
            stmt.addBatch(s);
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
        String content = Joiner.on("").join(sql);
        return content.split(DELIMITER);
    }
   
    private List<String> loadFile(Path path) throws IOException {
        List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
        return lines;
    }
}
