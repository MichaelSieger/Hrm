package de.hswt.hrm.report.latex;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;

import de.hswt.hrm.inspection.model.Inspection;
import de.hswt.hrm.inspection.model.Performance;
import de.hswt.hrm.inspection.model.PhysicalRating;
import de.hswt.hrm.inspection.service.InspectionService;
import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.component.model.Component;
import de.hswt.hrm.component.service.ComponentService;

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

    private InspectionService insService;

    private Inspection inspection;

    private String path;
    private String line;
    private String target;
    private StringBuffer endTarget = new StringBuffer();
    private StringBuffer rowEndTarget = new StringBuffer();
    private StringBuffer buffer = new StringBuffer();
    private StringBuffer bufferRow = new StringBuffer();
    private StringBuffer bufferImg = new StringBuffer();
    private BufferedReader reader;
    private String oldElement;
    private String targetName;
    private String targetGrade;

    private TargetPerformanceComparisonImageParser imgParser;
    private Collection<Performance> componentsTables;

    public TargetPerformanceComparisonParser(String path, Collection<Performance> componentsTable,
            InspectionService insService, Inspection inspection, ComponentService compService) {
        this.path = path;
        this.insService = insService;
        this.inspection = inspection;
        this.componentsTables = componentsTable;
    }

    public String parse() throws IOException, DatabaseException {
        this.parseTable();
        return endTarget.toString();
    }

    private void parseTable() throws IOException, DatabaseException {
        this.imgParser = new TargetPerformanceComparisonImageParser();
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
                buffer.append("\n");
            }
        }

        rowEndTarget = null;
        reader = null;
        Path pathRow = FileSystems.getDefault().getPath(this.path, FILE_DIR, FILE_NAME_ROW);
        bufferRow.setLength(0);
        // get template
        reader = Files.newBufferedReader(pathRow, Charset.defaultCharset());
        line = null;
        while ((line = reader.readLine()) != null) {
            // do not print comments
            line = line.trim();
            if (!line.startsWith(COMMENT)) {
                bufferRow.append(line);
                bufferRow.append("\n");
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
                bufferImg.append("\n");

            }
        }

        oldElement = componentsTables.iterator().next().getSchemeComponent().getComponent()
                .getName();

        Performance[] arrComTa = (Performance[]) componentsTables.toArray();
        Performance component;
        for (int i = 0; i < arrComTa.length; i++) {
            component = arrComTa[i];
            if (component.getSchemeComponent().getComponent().getName().equals(oldElement)) {
                this.parseRow2(component);
                targetName = component.getSchemeComponent().getComponent().getName();
                Component c = component.getSchemeComponent().getComponent();

                List<PhysicalRating> p = (List<PhysicalRating>) insService
                        .findPhysicalRating(inspection);

                for (PhysicalRating pr : p) {
                    if (pr.getComponent().getComponent().getName().equals(c))
                        ;
                    targetGrade = String.valueOf(pr.getRating());
                }

            }
            else {
                target = buffer.toString();
                target = target.replace(TABLE_COMPONENT_NAME, targetName);
                target = target.replace(TABLE_RATING, targetGrade);
                target = target.replace(TABLE_ROWS, this.rowEndTarget.toString());
                if (!insService.findPhoto(component).isEmpty()) {
                    target = target.replace(TABLE_IMG_HEADER, bufferImg.toString());
                    target = target.replace(TABLE_IMG_ROWS,
                            this.imgParser.parse(path, insService.findPhoto(component)));
                }
                else {
                    target = target.replace(TABLE_IMG_HEADER, "");
                    target = target.replace(TABLE_IMG_ROWS, "");
                }
                oldElement = component.getSchemeComponent().getComponent().getName();
                rowEndTarget.setLength(0);
                endTarget.append(target);
                i--;

            }

            // this.parseRow(component.getTargetPerformanceActivity());
            // target = null;
            //
            //
            // target = target.replace(TABLE_COMPONENT_NAME, component.getTargetName());
            // target = target.replace(TABLE_RATING, component.getTargetGrade());
            // target = target.replace(TABLE_ROWS, this.rowEndTarget.toString());
            // if (!picService.) {
            // target = target.replace(TABLE_IMG_HEADER, bufferImg.toString());
            // target = target.replace(TABLE_IMG_ROWS,
            // this.imgParser.parse(this.path, component.getPhotos()));
            // }
            // else {
            // target = target.replace(TABLE_IMG_HEADER, "");
            // target = target.replace(TABLE_IMG_ROWS, "");
            // }
            //
            // endTarget.append(target);

        }

    }

    private void parseRow2(Performance component) {
        target = bufferRow.toString();
        target = target.replace(ROW_TARGET, component.getTarget().getText());
        target = target.replace(ROW_PERFORMANCE, component.getCurrent().getText());
        target = target.replace(ROW_TASK, component.getActivity().getText());
        rowEndTarget.append(target);

    }
    //
    // private void parseRow(Collection<TargetPerformanceActivity> states) throws IOException {
    // rowEndTarget = null;
    // reader = null;
    // Path pathRow = FileSystems.getDefault().getPath(this.path, FILE_DIR, FILE_NAME_ROW);
    // buffer.setLength(0);
    // // get template
    // reader = Files.newBufferedReader(pathRow, Charset.defaultCharset());
    // line = null;
    // while ((line = reader.readLine()) != null) {
    // // do not print comments
    // line = line.trim();
    // if (!line.startsWith(COMMENT)) {
    // buffer.append(line);
    // appendNewLine();
    // }
    // }
    // // create rows
    // for (TargetPerformanceActivity state : states) {
    // target = buffer.toString();
    // target = target.replace(ROW_TARGET, state.getTarget().toString());
    // target = target.replace(ROW_PERFORMANCE, state.getCurrent().toString());
    // target = target.replace(ROW_TASK, state.getTarget().toString());
    // rowEndTarget.append(target);
    // }
    //
    // }
    //
    // private void appendNewLine() {
    // buffer.append("\n");
    // }

}
