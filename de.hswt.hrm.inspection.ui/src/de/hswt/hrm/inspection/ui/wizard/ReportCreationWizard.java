package de.hswt.hrm.inspection.ui.wizard;

import javax.inject.Inject;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.jface.wizard.Wizard;

import com.google.common.base.Optional;

import de.hswt.hrm.common.database.exception.SaveException;
import de.hswt.hrm.i18n.I18n;
import de.hswt.hrm.i18n.I18nFactory;
import de.hswt.hrm.inspection.model.Inspection;
import de.hswt.hrm.inspection.service.InspectionService;

public class ReportCreationWizard extends Wizard {

	@Inject
	private IEclipseContext context;
	
	@Inject
	private InspectionService inspectionService;
	
	private static final I18n I18N = I18nFactory.getI18n(ReportCreationWizard.class);

	private ReportCreationWizardPageOne pageOne;
	
	private Optional<Inspection> inspection;
	
	public ReportCreationWizard() {
		setWindowTitle(I18N.tr("Report creation"));
	}

	@Override
	public void addPages() {
		pageOne = new ReportCreationWizardPageOne();
		ContextInjectionFactory.inject(pageOne, context);
		addPage(pageOne);
	}

	@Override
	public boolean performFinish() {
		try {
			inspection = Optional.fromNullable(inspectionService.insert(pageOne.getInspection()));
		} catch (SaveException e) {
			
		}
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
