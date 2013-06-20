package de.hswt.hrm.inspection.ui.wizard;

import javax.inject.Inject;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.jface.wizard.Wizard;

import com.google.common.base.Optional;

import de.hswt.hrm.inspection.model.Inspection;

public class ReportCreationWizard extends Wizard {

	@Inject
	private IEclipseContext context;

	private ReportCreationWizardPageOne pageOne;
	
	private Optional<Inspection> inspection;
	
	public ReportCreationWizard() {
		setWindowTitle("Report creation");
	}

	@Override
	public void addPages() {
		pageOne = new ReportCreationWizardPageOne();
		ContextInjectionFactory.inject(pageOne, context);
		addPage(pageOne);
	}

	@Override
	public boolean performFinish() {
		inspection = Optional.fromNullable(pageOne.getInspection());
		return true;
	}

	@Override
	public boolean performCancel() {
		inspection = Optional.absent();
		return super.performCancel();
	}
	
	public Optional<Inspection> getInspection() {
		return inspection;
	}
}
