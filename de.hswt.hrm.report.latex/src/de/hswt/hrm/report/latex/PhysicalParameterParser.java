package de.hswt.hrm.report.latex;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

import de.hswt.hrm.inspection.model.PhysicalRating;

public class PhysicalParameterParser {

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
    private int sumQuantifier;
    private int sumRatings;
    private int totalGrade;

    // private final String = "::";

    public String parse(Path path) throws IOException {
        this.path = path;
        this.parseRow();
        this.parseTable();
        return this.endTarget;
    }

    public void parseTable() throws IOException {
        Path pathTable = this.path;
        // TODO append file to path
        StringBuffer buffer = new StringBuffer();
        BufferedReader reader = Files.newBufferedReader(pathTable, Charset.defaultCharset());
        String line = null;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
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

    public void parseRow() throws IOException {
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

        // TODO when model ready
        // uncomment the following two lines
        // this.sumRatings += report.getTempRating().toInt();
        // this.sumRatings += report.getHumidityRating().toInt();
        // this.sumQuantifier += report.getTempQuantifier();
        // this.sumQuantifier += report.getHumidityQuantifier();

        // - un-String the calls below..
        preTarget = buffer.toString();
        preTarget.replace(PROPERTY, "Temperatur");
        preTarget.replace(VALUE, "report.getAirTemperature()");
        preTarget.replace(PARAM_GRADE, "report.getTempRating()");
        preTarget.replace(PARAM_WHEIGHTING, "report.getTempQuantifier()");
        preTarget.replace(PARAM_RATING,
                "String.valueOf(report.getTempRating().toInt()*report.getQuantifier().toInt())");
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
    public int getTotalGrade(Path path) throws IOException {
        this.path = path;
        this.parseRow();
        this.parseTable();
        return this.totalGrade;

    }

}
