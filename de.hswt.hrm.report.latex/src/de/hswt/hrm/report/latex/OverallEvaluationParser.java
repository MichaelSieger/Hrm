package de.hswt.hrm.report.latex;

import de.hswt.hrm.inspection.model.Inspection;

public class OverallEvaluationParser {

    private Inspection inspection;

    public OverallEvaluationParser(Inspection inspection) {
        this.inspection = inspection;

    }

    public String parse() {
        return this.inspection.getSummary().toString();
    }

}
