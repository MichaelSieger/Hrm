package de.hswt.hrm.report.latex;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class PhysicalOverallParser {

    private static final String FILE_DIR = "templates";
    private static final String FILE_NAME_PROP = "physicaloverallgraderow.properties";
    private static final String FILE_NAME_TABLE = "physicaloverallgradetable.tex";

    private final String INSPECTION_RATING_AV = ":physicalInspectionRatingAverage:";
    private final String PARAM_RATING_AV = ":physicalParameterRatingAverage:";

    private final String ROWS = ":rows:";
    private final String OVERALL_RATING = ":physicalOverallRating:";

    private final String COMMENT = "%";

    private float inspection_av;
    private float param_av;

    private String path;

    private PhysicalInspectionParser inspectionParser;
    PhysicalParameterParser paramParser;

    private Properties prop = new Properties();

    private StringBuffer bufferRow = new StringBuffer();
    private StringBuffer bufferTable = new StringBuffer();

    public PhysicalOverallParser(String path, PhysicalInspectionParser inspectionParser,
            PhysicalParameterParser paramParser) {
        this.path = path;
        this.inspectionParser = inspectionParser;
        this.paramParser = paramParser;

    }

    public String parse() throws IOException {

        this.inspection_av = inspectionParser.getTotalGrade(this.path);
        this.param_av = paramParser.getTotalGrade();

        prop.load(Files.newInputStream(Paths.get(path, FILE_DIR, FILE_NAME_PROP)));

        bufferRow.setLength(0);

        bufferRow.append(prop.getProperty("physical.overall.inspection").replace(
                INSPECTION_RATING_AV, String.valueOf(this.inspection_av)));
        bufferRow.append("\n");
        bufferRow.append(prop.getProperty("physical.overall.parameter").replace(PARAM_RATING_AV,
                String.valueOf(this.param_av)));

        bufferTable.setLength(0);
        Path pathTable = FileSystems.getDefault().getPath(this.path, FILE_DIR, FILE_NAME_TABLE);
        BufferedReader reader = Files.newBufferedReader(pathTable, Charset.defaultCharset());
        String line = null;
        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (!line.startsWith(COMMENT)) {
                bufferTable.append(line);
                appendNewLineTable();
            }
        }

        String target = bufferTable.toString();
        target = target.replace(ROWS, bufferRow.toString());
        target = target.replace(OVERALL_RATING,
                String.valueOf(Math.round((this.inspection_av + this.param_av) / 2 * 10F) / 10F));

        return target;
    }

    private void appendNewLineTable() {
        bufferTable.append("\n");
    }

}
