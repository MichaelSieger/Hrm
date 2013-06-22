package de.hswt.hrm.inspection.ui.part;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;

import com.google.common.base.Optional;

import de.hswt.hrm.common.ui.swt.table.ColumnDescription;
import de.hswt.hrm.common.ui.swt.wizards.WizardCreator;
import de.hswt.hrm.inspection.model.Inspection;
import de.hswt.hrm.inspection.ui.wizard.ReportCreationWizard;

public class InspectionPartUtil {

    public static Optional<Inspection> showInspectionCreateWizard(IEclipseContext context,
            Shell shell) {
        // Create wizard with injection support
        ReportCreationWizard wizard = new ReportCreationWizard();
        ContextInjectionFactory.inject(wizard, context);

        // Show wizard
        WizardDialog wd = WizardCreator.createWizardDialog(shell, wizard);
        wd.open();
        return wizard.getInspection();
    }

    public static List<ColumnDescription<Inspection>> getColumns() {
        List<ColumnDescription<Inspection>> columns = new ArrayList<>();
        columns.add(getTitleColumn());
        columns.add(getPlantClumn());
        columns.add(getReportDateColumn());
        columns.add(getInspectionDateColumn());
        columns.add(getNextInspectionDateColumn());
        columns.add(getStyleColumn());
        return columns;
    }

    private static ColumnDescription<Inspection> getTitleColumn() {
        return new ColumnDescription<>("Title", new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
                Inspection i = (Inspection) element;
                return i.getTitle();
            }
        }, new Comparator<Inspection>() {
            @Override
            public int compare(Inspection i1, Inspection i2) {
                return i1.getTitle().compareToIgnoreCase(i2.getTitle());
            }
        });
    }

    private static ColumnDescription<Inspection> getPlantClumn() {
        return new ColumnDescription<>("Plant Description", new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
                Inspection i = (Inspection) element;
                return i.getPlant().getDescription();
            }
        }, new Comparator<Inspection>() {
            @Override
            public int compare(Inspection i1, Inspection i2) {
                return i1.getPlant().getDescription()
                        .compareToIgnoreCase(i2.getPlant().getDescription());
            }
        });
    }

    private static ColumnDescription<Inspection> getReportDateColumn() {
        return new ColumnDescription<>("Report Date", new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
                Inspection i = (Inspection) element;
                return i.getReportDate().toString();
            }
        }, new Comparator<Inspection>() {
            @Override
            public int compare(Inspection i1, Inspection i2) {
                return i1.getReportDate().toString()
                        .compareToIgnoreCase(i2.getReportDate().toString());
            }
        });
    }

    private static ColumnDescription<Inspection> getInspectionDateColumn() {
        return new ColumnDescription<>("Inspection Date", new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
                Inspection i = (Inspection) element;
                return i.getJobDate().toString();
            }
        }, new Comparator<Inspection>() {
            @Override
            public int compare(Inspection i1, Inspection i2) {
                return i1.getJobDate().toString().compareToIgnoreCase(i2.getJobDate().toString());
            }
        });
    }

    private static ColumnDescription<Inspection> getNextInspectionDateColumn() {
        return new ColumnDescription<>("Next Inspection Date", new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
                Inspection i = (Inspection) element;
                return i.getNextInspectionDate().toString();
            }
        }, new Comparator<Inspection>() {
            @Override
            public int compare(Inspection i1, Inspection i2) {
                return i1.getNextInspectionDate().toString()
                        .compareToIgnoreCase(i2.getNextInspectionDate().toString());
            }
        });
    }

    private static ColumnDescription<Inspection> getStyleColumn() {
        return new ColumnDescription<>("Layout", new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
                Inspection i = (Inspection) element;
                return i.getLayout().getName();
            }
        }, new Comparator<Inspection>() {
            @Override
            public int compare(Inspection i1, Inspection i2) {
                return i1.getLayout().getName().compareToIgnoreCase(i2.getLayout().getName());
            }
        });

    }

}
