package de.hswt.hrm.report.latex;

import java.awt.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;

import de.hswt.hrm.inspection.model.PhysicalRating;

public class PhysicalParameterParser {

    private final String PROPERTY = ":physicalParameterProperty:";
    private final String VALUE = ":physicalParameterValue:";
    private final String PARAM_GRADE = ":physicalParameterGrade:";
    private final String PARAM_WHEIGHTING = ":physicalParameterWheighting:";
    private final String PARAM_RATING = ":physicalParameterRating:";
    private final String PARAM_COMMENT = ":physicalParameterComment:";

    // private final String = "::";

    public String parse(Path path) {

        String target = "BOX";
        return target;
    }

    public String parseTable(Path pathTable) throws IOException {
        StringBuffer buffer = new StringBuffer();
        BufferedReader reader = Files.newBufferedReader(pathTable, Charset.defaultCharset());
        String line = null;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }

        String target = buffer.toString();

        // target.replace(,);

        return target;
    }

}
