package de.hswt.hrm.report.latex;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;

import de.hswt.hrm.report.latex.collectors.TargetPerformanceActivity;
import de.hswt.hrm.report.latex.collectors.TargetPerformancePhotoStates;

public class TargetPerformanceComparisonParser {

    private static final String FILE_DIR = "templates";
    private static final String FILE_NAME_TABLE = "targetperformancecomparisontable.tex";
    private static final String FILE_NAME_ROW = "targetperformancecomparisonrow.tex";
    private static final String FILE_NAME_IMG_HEADER = "targetperformancecomparisonimageheader.tex";

    private final String ROW_TARGET = ":componentTarget:";
    private final String ROW_PERFORMANCE = ":componentPerformance:";
    private final String ROW_TASK = ":componentTask:";

    private final String TABLE_COMPONENT_NAME = ":component:";
    private final String TABLE_RATING = ":microbiologyOverallRanking:";
    private final String TABLE_ROWS = ":rows:";
    private final String TABLE_IMG_HEADER = ":imagesHeader:";
    private final String TABLE_IMG_ROWS = ":imageRows:";

    private final String COMMENT = "%";

    private String path;
    private String line;
    private String target;
    private StringBuffer endTarget = new StringBuffer();
    private StringBuffer rowEndTarget = new StringBuffer();
    private StringBuffer buffer = new StringBuffer();
    private StringBuffer bufferImg = new StringBuffer();
    private BufferedReader reader;

    private TargetPerformanceComparisonImageParser imgParser;
    private Collection<TargetPerformancePhotoStates> componentsTables;

    public TargetPerformanceComparisonParser(String path,
            Collection<TargetPerformancePhotoStates> componentsTable) {
        this.path = path;
        this.componentsTables = componentsTable;
    }

    public String parse() throws IOException {
        this.parseTable();
        return endTarget.toString();
    }

    private void parseTable() throws IOException {
        Path pathTable = FileSystems.getDefault().getPath(this.path, FILE_DIR, FILE_NAME_TABLE);
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

        Path pathImgHeader = FileSystems.getDefault().getPath(this.path, FILE_DIR,
                FILE_NAME_IMG_HEADER);
        bufferImg.setLength(0);
        reader = null;
        reader = Files.newBufferedReader(pathImgHeader, Charset.defaultCharset());
        line = null;
        while ((line = reader.readLine()) != null) {
            // do not print comments
            line = line.trim();
            if (!line.startsWith(COMMENT)) {
                bufferImg.append(line);
                appendNewLine();
            }
        }

        for (TargetPerformancePhotoStates component : componentsTables) {
            this.imgParser = new TargetPerformanceComparisonImageParser();
            this.parseRow(component.getTargetPerformanceActivity());
            target = null;
            target = buffer.toString();
            target.replace(TABLE_COMPONENT_NAME, component.getTargetName());
            target.replace(TABLE_RATING, component.getTargetGrade());
            target.replace(TABLE_ROWS, this.rowEndTarget.toString());
            if (!component.getPhotos().isEmpty()) {
                target.replace(TABLE_IMG_HEADER, bufferImg.toString());
                target.replace(TABLE_IMG_ROWS,
                        this.imgParser.parse(this.path, component.getPhotos()));
            }

            endTarget.append(target);

        }

    }

    private void parseRow(Collection<TargetPerformanceActivity> states) throws IOException {
        rowEndTarget = null;
        reader = null;
        Path pathRow = FileSystems.getDefault().getPath(this.path, FILE_DIR, FILE_NAME_ROW);
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
        for (TargetPerformanceActivity state : states) {
            target = buffer.toString();
            target.replace(ROW_TARGET, state.getTarget().toString());
            target.replace(ROW_PERFORMANCE, state.getCurrent().toString());
            target.replace(ROW_TASK, state.getTarget().toString());
            rowEndTarget.append(target);
        }

    }

    private void appendNewLine() {
        buffer.append("\n");
    }

}
