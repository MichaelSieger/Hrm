package de.hswt.hrm.report.latex;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import de.hswt.hrm.component.model.Attribute;
import de.hswt.hrm.contact.model.Contact;
import de.hswt.hrm.inspection.model.Inspection;
import de.hswt.hrm.inspection.model.PhysicalRating;
import de.hswt.hrm.report.latex.collectors.TargetPerformancePhotoStates;

public class MainParser {

    /*
     * Parsers
     */
    private FileWriter fileWriter;
    private OverallEvaluationParser overallEvaluationParser;
    private PhysicalEvaluationParser physicalEvaluationParser;
    private ReportDataParser reportDataParser;
    private TargetPerformanceComparisonParser targetPerformanceComperisonParser;
    private ComponentsParser componentsParser;

    /*
     * initialized with constructor params
     */
    private Collection<Map<Attribute, String>> componentAttributes;
    private String path;
    private Collection<TargetPerformancePhotoStates> componentsTable;
    private Inspection inspection;
    private Collection<PhysicalRating> ratings;

    public MainParser(String path, Inspection inspection,
            Collection<TargetPerformancePhotoStates> componentsTable,
            Collection<Map<Attribute, String>> componentAttributes,
            Collection<PhysicalRating> ratings, Contact contactCustomer, Contact contactContractor,
            Contact conctactController) {
        this.path = path;
        this.componentsTable = componentsTable;
        this.inspection = inspection;
        this.componentAttributes = componentAttributes;
        this.ratings = ratings;

    }

    // TODO dirPath & fileName
    public void parse() throws IOException {
        fileWriter = new FileWriter();
        fileWriter.writeToFile("", "", new OverallEvaluationParser(inspection).parse());
        fileWriter.writeToFile("dirPath", "fileName",
                new OverallEvaluationParser(inspection).parse());
        fileWriter.writeToFile("dirPath", "fileName", new PhysicalEvaluationParser(path, ratings,
                inspection).parse());
        fileWriter.writeToFile("dirPath", "fileName",
                new ReportDataParser(path, inspection).parse());
        fileWriter.writeToFile("dirPath", "fileName", new TargetPerformanceComparisonParser(path,
                componentsTable).parse());
        fileWriter.writeToFile("dirPath", "fileName", new ComponentsParser(path, inspection,
                componentAttributes).parse());

    }

}
