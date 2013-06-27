package de.hswt.hrm.report.latex.service;

import org.eclipse.e4.core.di.annotations.Creatable;

import de.hswt.hrm.inspection.model.Inspection;

@Creatable
public class ReportService {

    private Inspection inspection;

    public void superAwesomeParsingMethod() {
        if (inspection == null) {
            return;
        }
        System.out.println("Parsing Inspection with " + inspection.getTitle() + " ... NOT!");
    }

    public Inspection getInspection() {
        return inspection;
    }

    public void setInspection(Inspection inspection) {
        this.inspection = inspection;
    }

}
