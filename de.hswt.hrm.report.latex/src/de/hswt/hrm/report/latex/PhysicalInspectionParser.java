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
    private final String COMMENT = ":physicalInspectionComment:";
    private final String ROWS = ":rows:";
    private final String GRADE_SUM = ":physicalInspectionGradeSum:";
    private final String WHEIGHTED_SUM = ":physicalInspectionWheightingSum:";
    private final String RATING_AV = ":physicalInspectionRatingAverage:";

    private String endTarget;
    private int sumRatings;
    private int sumQuantifier;
    private int totalGrade;

    private Path path;
    private Collection<PhysicalRating> ratings;
    private String rows;

    public String parse(Path path, Collection<PhysicalRating> ratings) throws IOException {
        this.ratings = ratings;
        this.path = path;
        this.parseRow();
        this.parseTable();

        return this.endTarget;
    }

    private void parseTable() throws IOException {
        Path pathTable = this.path;
        // TODO append file to path
        StringBuffer buffer = new StringBuffer();
        BufferedReader reader = Files.newBufferedReader(pathTable, Charset.defaultCharset());
        String line = null;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }
        String target;
        target = buffer.toString();

        this.totalGrade = this.sumRatings / this.sumQuantifier;
        target.replace(ROWS, this.rows);
        target.replace(GRADE_SUM, String.valueOf(this.sumRatings));
        target.replace(WHEIGHTED_SUM, String.valueOf(this.sumQuantifier));
        target.replace(RATING_AV, String.valueOf(this.totalGrade));
        this.endTarget = target;

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

        String preTarget;

        StringBuffer target = new StringBuffer();
        for (PhysicalRating rating : this.ratings) {
            // TODO when model ready
            // uncomment the following two lines
            // this.sumRatings += rating.getRating().toInt();
            // this.sumQuantifier += rating.getComponent().getQuantifier();
            // - un-String the calls below..
            preTarget = buffer.toString();
            preTarget.replace(INSPECTOIN_SAMPLE_POINT, "rating.getComponent().getName()");
            preTarget.replace(GRADE, "rating.getRating()");
            preTarget.replace(WHEIGHTING, "rating.getComponent().getQuantifier()");
            preTarget
                    .replace(RATING,
                            "String.valueOf(rating.getRating().toInt()*rating.getComponent().getQuantifier().toInt())");
            preTarget.replace(COMMENT, "rating.getComment()");
            target.append(preTarget);
        }
        this.rows = target.toString();

    }

    /*
     * returns the totalGrade, calculated from the components.
     */
    public int getTotalGrade(Path path) throws IOException {
        this.path = path;
        this.parseRow();
        this.parseTable();
        return this.totalGrade;

    }

}
