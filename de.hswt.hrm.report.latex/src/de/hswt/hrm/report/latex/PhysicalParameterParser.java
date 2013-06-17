package de.hswt.hrm.report.latex;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

public class PhysicalParameterParser {

    private final String COMMENT = "%";

    /*
     * keys of physicalparameterrow.tex
     */
    private final String PROPERTY = ":physicalParameterProperty:";
    private final String VALUE = ":physicalParameterValue:";
    private final String PARAM_GRADE = ":physicalParameterGrade:";
    private final String PARAM_WHEIGHTING = ":physicalParameterWheighting:";
    private final String PARAM_RATING = ":physicalParameterRating:";
    private final String PARAM_COMMENT = ":physicalParameterComment:";

    /*
     * keys of physicalparametertable.tex
     */
    private final String ROWS = ":rows";
    private final String GRADE_SUM = ":physicalParameterGradeSum:";
    private final String WHEIGHTING_SUM = ":physicalParameterWheightingSum:";
    private final String RATING_AV = ":physicalParameterRatingAverage:";

    // TODO unccoment follwing line when model ready
    // private Report report;
    private Path path;

    private String endTarget;
    private String rows;

    private float sumQuantifier;
    private float sumRatings;
    private float totalGrade;

    private StringBuffer buffer = new StringBuffer();

    public String parse(Path path) throws IOException {
        this.path = path;
        this.parseRow();
        this.parseTable();
        return this.endTarget;
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

        String target = buffer.toString();
        this.totalGrade = this.sumRatings / this.sumQuantifier;
        target.replace(ROWS, this.rows);
        target.replace(GRADE_SUM, String.valueOf(sumRatings));
        target.replace(GRADE_SUM, String.valueOf(sumRatings));
        target.replace(WHEIGHTING_SUM, String.valueOf(sumQuantifier));
        target.replace(RATING_AV, String.valueOf(this.totalGrade));

        this.endTarget = target;
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

        String preTarget;

        StringBuffer target = new StringBuffer();

        // TODO when model ready
        // uncomment the following two lines
        // this.sumRatings += report.getTempRating().tofloat();
        // this.sumRatings += report.getHumidityRating().tofloat();
        // this.sumQuantifier += report.getTempQuantifier();
        // this.sumQuantifier += report.getHumidityQuantifier();

        // - un-String the calls below..
        preTarget = buffer.toString();
        preTarget.replace(PROPERTY, "Temperatur");
        preTarget.replace(VALUE, "report.getAirTemperature()");
        preTarget.replace(PARAM_GRADE, "report.getTempRating()");
        preTarget.replace(PARAM_WHEIGHTING, "report.getTempQuantifier()");
        preTarget
                .replace(PARAM_RATING,
                        "String.valueOf(report.getTempRating().tofloat()*report.getQuantifier().tofloat())");
        preTarget.replace(PARAM_COMMENT, "report.getTempComment");
        target.append(preTarget);

        preTarget = buffer.toString();
        preTarget.replace(PROPERTY, "relative Luftfeuchtigkeit");
        preTarget.replace(VALUE, "report.getHumidity()");
        preTarget.replace(PARAM_GRADE, "report.getHumidityRating()");
        preTarget.replace(PARAM_WHEIGHTING, "report.getHumidityQuantifier()");
        preTarget.replace(PARAM_RATING,
                "String.valueOf(report.getHumidityRating()*report.getHumidityQuantifier())");
        preTarget.replace(PARAM_COMMENT, "report.getHumidityComment");
        target.append(preTarget);

        this.rows = target.toString();

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
