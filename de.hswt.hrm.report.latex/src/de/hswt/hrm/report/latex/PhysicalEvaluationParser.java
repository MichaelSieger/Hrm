package de.hswt.hrm.report.latex;

import java.io.IOException;
import java.nio.file.Path;

public class PhysicalEvaluationParser {

    private PhysicalInspectionParser inspection = new PhysicalInspectionParser();
    private PhysicalParameterParser param = new PhysicalParameterParser();
    private PhysicalOverallParser overAll = new PhysicalOverallParser();

    public String parse(Path path) throws IOException {
        StringBuffer buffer = new StringBuffer();
        buffer.append(inspection.parse(path));
        buffer.append(param.parse(path));
        buffer.append(overAll.parse());
        return buffer.toString();
    }

}
