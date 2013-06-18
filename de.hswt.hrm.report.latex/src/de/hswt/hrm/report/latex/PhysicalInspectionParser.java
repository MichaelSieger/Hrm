package de.hswt.hrm.report.latex;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;

import de.hswt.hrm.inspection.model.PhysicalRating;

public class PhysicalInspectionParser {

    private final String INSPECTOIN_SAMPLE_POINT = ":physicalInspectionSamplingPoint:";
    private final String GRADE = ":physicalInspectionGrade:";
    private final String WHEIGHTING = ":physicalInspectionWheighting:";
    private final String RATING = ":physicalInspectionRating";
    private final String PHYS_COMMENT = ":physicalInspectionComment:";
    private final String ROWS = ":rows:";
    private final String GRADE_SUM = ":physicalInspectionGradeSum:";
    private final String WHEIGHTED_SUM = ":physicalInspectionWheightingSum:";
    private final String RATING_AV = ":physicalInspectionRatingAverage:";

    private final String COMMENT = "%";

    private String preTarget;
    private String target;
    private StringBuffer targetRow = new StringBuffer();

    private float sumRatings;
    private float sumQuantifier;
    private float totalGrade;

    private Path path;

    private Collection<PhysicalRating> ratings;

    private StringBuffer buffer = new StringBuffer();

    public String parse(Path path, Collection<PhysicalRating> ratings) throws IOException {
        this.ratings = ratings;
        this.path = path;
        this.parseRow();
        this.parseTable();

        return this.target;
    }

    private void parseTable() throws IOException {
        Path pathTable = this.path;
        // TODO append file to path
        buffer.setLength(0);
        BufferedReader reader = Files.newBufferedReader(pathTable, Charset.defaultCharset());
        String line = null;
        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (!line.startsWith(COMMENT)) {
                buffer.append(line);
                appendNewLine();
            }
        }

        target = null;
        target = buffer.toString();

        this.totalGrade = this.sumRatings / this.sumQuantifier;
        target.replace(ROWS, this.targetRow.toString());
        target.replace(GRADE_SUM, String.valueOf(this.sumRatings));
        target.replace(WHEIGHTED_SUM, String.valueOf(this.sumQuantifier));
        target.replace(RATING_AV, String.valueOf(this.totalGrade));

    }

    private void parseRow() throws IOException {
        Path pathRow = this.path;
        // TODO append file to path
        buffer.setLength(0);
        BufferedReader reader = Files.newBufferedReader(pathRow, Charset.defaultCharset());
        String line = null;
        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (!line.startsWith(COMMENT)) {
                buffer.append(line);
                appendNewLine();
            }
        }

        preTarget = null;
        targetRow = null;
        for (PhysicalRating rating : this.ratings) {
            // TODO when model ready
            // uncomment the following two lines
            // this.sumRatings += rating.getRating().tofloat();
            // this.sumQuantifier += rating.getComponent().getQuantifier();
            // - un-String the calls below..
            preTarget = buffer.toString();
            preTarget.replace(INSPECTOIN_SAMPLE_POINT, "rating.getComponent().getName()");
            preTarget.replace(GRADE, "rating.getRating()");
            preTarget.replace(WHEIGHTING, "rating.getComponent().getQuantifier()");
            preTarget
                    .replace(RATING,
                            "String.valueOf(rating.getRating().tofloat()*rating.getComponent().getQuantifier().tofloat())");
            preTarget.replace(PHYS_COMMENT, "rating.getComment()");
            targetRow.append(preTarget);
        }

    }

    /*
     * returns the totalGrade, calculated from the components.
     */
    public float getTotalGrade(Path path) throws IOException {
        this.path = path;
        this.parseRow();
        this.parseTable();
        return this.totalGrade;

    }

    private void appendNewLine() {
        buffer.append("\n");
    }

}
