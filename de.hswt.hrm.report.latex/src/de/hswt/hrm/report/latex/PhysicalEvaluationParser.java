package de.hswt.hrm.report.latex;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;

import de.hswt.hrm.inspection.model.PhysicalRating;

public class PhysicalEvaluationParser {

    private PhysicalInspectionParser inspection = new PhysicalInspectionParser();
    private PhysicalParameterParser param = new PhysicalParameterParser();
    private PhysicalOverallParser overAll = new PhysicalOverallParser();

    public String parse(Path path, Collection<PhysicalRating> ratings) throws IOException {
        StringBuffer buffer = new StringBuffer();
        buffer.append(inspection.parse(path, ratings));
        buffer.append(param.parse(path));
        buffer.append(overAll.parse(path.toString(), inspection, param));
        return buffer.toString();
    }

}
