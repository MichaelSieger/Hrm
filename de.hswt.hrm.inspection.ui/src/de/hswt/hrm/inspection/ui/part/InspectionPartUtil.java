package de.hswt.hrm.inspection.ui.part;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;

import com.google.common.base.Optional;

import de.hswt.hrm.common.ui.swt.table.ColumnDescription;
import de.hswt.hrm.common.ui.swt.wizards.WizardCreator;
import de.hswt.hrm.inspection.model.Inspection;
import de.hswt.hrm.inspection.ui.wizard.ReportCreationWizard;

public class InspectionPartUtil {

	public static Optional<Inspection> showInspectionCreateWizard(
			IEclipseContext context, Shell shell) {
		// TODO: partly move to extra plugin

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
        return null;
    }

    private static ColumnDescription<Inspection> getPlantClumn() {
        // TODO Auto-generated method stub
        return null;
    }

    private static ColumnDescription<Inspection> getTitleColumn() {
        // TODO Auto-generated method stub
        return null;
    }

}
