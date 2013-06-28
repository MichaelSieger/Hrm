package de.hswt.hrm.inspection.ui.wizard;

import java.lang.reflect.InvocationTargetException;

import javax.inject.Inject;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.jface.wizard.Wizard;

import de.hswt.hrm.inspection.model.Inspection;
import de.hswt.hrm.inspection.ui.runner.ReportExportRunner;

public class ReportExportWizard extends Wizard {

	@Inject
	private IEclipseContext context;

	private ReportExportWizardPageOne pageOne;
	
	private Inspection inspection;
	
	public ReportExportWizard(Inspection inspection) {
		setWindowTitle("Report export");
		this.inspection = inspection;
	}

	@Override
	public void addPages() {
		pageOne = new ReportExportWizardPageOne();
		ContextInjectionFactory.inject(pageOne, context);
		addPage(pageOne);
	}

	@Override
	public boolean performFinish() {
		ReportExportRunner runner = new ReportExportRunner(
				inspection, pageOne.getRootPath(), pageOne.isKeepFiles());
		ContextInjectionFactory.inject(runner, context);
		try {
			getContainer().run(true, true, runner);
		} catch (InvocationTargetException | InterruptedException e) {
			e.printStackTrace();
		}
		
		return true;
	}
}
