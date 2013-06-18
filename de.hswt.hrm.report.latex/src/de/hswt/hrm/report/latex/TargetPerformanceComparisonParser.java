package de.hswt.hrm.report.latex;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;

import de.hswt.hrm.component.model.Component;

public class TargetPerformanceComparisonParser {

    private final String ROW_TARGET = ":componentTarget:";
    private final String ROW_PERFORMANCE = ":componentPerformance:";
    private final String ROW_TASK = ":componentTask:";

    private final String TABLE_COMPONENT_NAME = ":component:";
    private final String TABLE_RATING = ":microbiologyOverallRanking:";
    private final String TABLE_ROWS = ":rows:";
    private final String TABLE_IMG_HEADER = ":imagesHeader:";
    private final String TABLE_IMG_ROWS = ":imageRows:";
    private final String COMMENT = "%";

    private Path path;

    private String line;
    private String target;
    private StringBuffer endTarget = new StringBuffer();
    private StringBuffer rowEndTarget = new StringBuffer();

    private BufferedReader reader;

    // TODO set right generic
    private Collection<Collection<Component>> componentsTable;

    private StringBuffer buffer = new StringBuffer();

    public String parse(Path path, Collection<Collection<Component>> componentsTable)
            throws IOException {
        this.componentsTable = componentsTable;
        this.path = path;
        this.parseTable();
        return endTarget.toString();

    }

    private void parseTable() throws IOException {
        Path pathTable = this.path;
        // TODO append file to path
        buffer.setLength(0);
        reader = null;
        reader = Files.newBufferedReader(pathTable, Charset.defaultCharset());
        line = null;
        while ((line = reader.readLine()) != null) {
            // do not print comments
            line = line.trim();
            if (!line.startsWith(COMMENT)) {
                buffer.append(line);
                appendNewLine();
            }
        }
        // TODO set right generic
        for (Collection<Component> component : componentsTable) {
            this.parseRow(component);
            target = null;
            target = buffer.toString();
            // TODO set models models
            target.replace(TABLE_COMPONENT_NAME, "");
            target.replace(TABLE_RATING, "");
            target.replace(TABLE_ROWS, this.rowEndTarget.toString());
            target.replace(TABLE_IMG_HEADER, "");
            target.replace(TABLE_IMG_ROWS, "");

            endTarget.append(target);

        }

    }

    // TODO set right generic
    private void parseRow(Collection<Component> components) throws IOException {
        rowEndTarget = null;
        reader = null;
        Path pathRow = this.path;
        // TODO append file to path
        buffer.setLength(0);
        // get template
        reader = Files.newBufferedReader(pathRow, Charset.defaultCharset());
        line = null;
        while ((line = reader.readLine()) != null) {
            // do not print comments
            line = line.trim();
            if (!line.startsWith(COMMENT)) {
                buffer.append(line);
                appendNewLine();
            }
        }
        // create rows
        // TODO set models models
        for (Component component : components) {
            target = buffer.toString();
            target.replace(ROW_TARGET, "");
            target.replace(ROW_PERFORMANCE, "");
            target.replace(ROW_TASK, "");
            rowEndTarget.append(target);
        }

    }

    private void appendNewLine() {
        buffer.append("\n");
    }

}
