package de.hswt.hrm.report.latex;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

public class TargetPerformanceComparisonParser {

    private final String ROW_TARGET = ":componentTarget:";
    private final String ROW_PERFORMANCE = ":componentPerformance:";
    private final String ROW_TASK = ":componentTask:";

    private final String TABLE_COMPONENT_NAME = ":component:";
    private final String TABLE_RATING = ":microbiologyOverallRanking:";
    private final String TABLE_ROWS = ":rows:";
    private final String TABLE_IMG_HEADER = ":imagesHeader:";
    private final String TABLE_IMG_ROWS = ":imageRows:";

    private Path path;

    private String endTarget;

    // StateTarget target
    public String parse(Path path) throws IOException {
        this.path = path;
        this.parseRow();
        return endTarget;

    }

    private void parseTable() {

    }

    private void parseRow() throws IOException {
        Path pathRow = this.path;
        // TODO append file to path
        StringBuffer buffer = new StringBuffer();
        BufferedReader reader = Files.newBufferedReader(pathRow, Charset.defaultCharset());
        String line = null;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }

    }

}
