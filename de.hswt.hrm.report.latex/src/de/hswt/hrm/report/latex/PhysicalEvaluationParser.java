package de.hswt.hrm.report.latex;

import java.io.IOException;
import java.util.Collection;

import de.hswt.hrm.inspection.model.Inspection;
import de.hswt.hrm.inspection.model.PhysicalRating;

public class PhysicalEvaluationParser {

    /*
     * Parsers
     */
    private PhysicalInspectionParser inspectionParser;
    private PhysicalParameterParser paramParser;
    private PhysicalOverallParser overAllParser;

    /*
     * initialized with constructor params
     */
    private String path;
    private Collection<PhysicalRating> ratings;
    private Inspection inspection;

    public PhysicalEvaluationParser(String path, Collection<PhysicalRating> ratings,
            Inspection inspection) {
        this.path = path;
        this.ratings = ratings;
        this.inspection = inspection;

    }

    public String parse() throws IOException {
        inspectionParser = new PhysicalInspectionParser(path, ratings);
        paramParser = new PhysicalParameterParser(path, inspection);
        overAllParser = new PhysicalOverallParser(path, inspectionParser, paramParser);
        StringBuffer buffer = new StringBuffer();
        buffer.append(inspectionParser.parse());
        buffer.append(paramParser.parse());
        buffer.append(overAllParser.parse());
        return buffer.toString();
    }

}
