package de.hswt.hrm.report.latex;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;

import de.hswt.hrm.inspection.model.Inspection;
import de.hswt.hrm.inspection.model.PhysicalRating;

public class PhysicalEvaluationParser {

    private PhysicalInspectionParser inspectionParser;
    private PhysicalParameterParser paramParser;
    private PhysicalOverallParser overAllParser;

    public String parse(String path, Collection<PhysicalRating> ratings, Inspection inspection) throws IOException {
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
