package de.hswt.hrm.report.latex;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class PhysicalOverallParser {

    private final String INSPECTION_RATING_AV = ":physicalInspectionRatingAverage:";
    private final String PARAM_RATING_AV = ":physicalParameterRatingAverage:";

    private final String ROWS = ":rows:";
    private final String OVERALL_RATING = ":physicalOverallRating:";

    private float inspection_av;
    private float param_av;
    private String path;
    private Properties prop = new Properties();
    private StringBuffer bufferRow = new StringBuffer();
    private StringBuffer bufferTable = new StringBuffer();
    private Path p = Paths.get(path);

    public String parse(String path, PhysicalInspectionParser inspectionParser,
            PhysicalParameterParser paramParser) throws IOException {
        // TODO care about path !!!
        this.path = path;

        this.inspection_av = inspectionParser.getTotalGrade(p);
        this.param_av = paramParser.getTotalGrade(p);

        prop.load(Files.newInputStream(Paths.get(path, "templates",
                "physicaloverallgraderow.properties")));

        // TODO optional component !!
        bufferRow.append(prop.getProperty("physical.overall.inspection").replace(
                INSPECTION_RATING_AV, String.valueOf(this.inspection_av)));
        appendNewLine();
        bufferRow.append(prop.getProperty("physical.overall.parameter").replace(PARAM_RATING_AV,
                String.valueOf(this.param_av)));

        Path pathTable = this.p;
        // TODO append file to path
        BufferedReader reader = Files.newBufferedReader(pathTable, Charset.defaultCharset());
        String line = null;
        while ((line = reader.readLine()) != null) {
            bufferTable.append(line);
        }

        String target = bufferTable.toString();
        // TODO obtional component
        target.replace(ROWS, bufferRow.toString());
        target.replace(OVERALL_RATING, String.valueOf((this.inspection_av + this.param_av) / 2));

        return target;
    }

    private void appendNewLine() {
        bufferRow.append("\n");
    }

}
