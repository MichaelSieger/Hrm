package de.hswt.hrm.misc.ui.part;

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
import de.hswt.hrm.misc.reportPreferences.model.ReportPreference;
import de.hswt.hrm.misc.ui.PreferencesWizard.PreferencesWizard;

public class ReportPreferenceUtil {

	public static Optional<ReportPreference> showWizard(IEclipseContext context,
			Shell shell, Optional<ReportPreference> pref) {

		PreferencesWizard ew = new PreferencesWizard(context,pref);
		ContextInjectionFactory.inject(ew, context);

		WizardDialog wd = WizardCreator.createWizardDialog(shell, ew);
		wd.open();
		return ew.getPreference();

	}

	public static List<ColumnDescription<ReportPreference>> getColumns() {
		List<ColumnDescription<ReportPreference>> columns = new ArrayList<>();

		columns.add(getNameColumn());
		columns.add(getTextColumn());

		return columns;
	}

	private static ColumnDescription<ReportPreference> getNameColumn() {
		return new ColumnDescription<>("Name", new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				ReportPreference e = (ReportPreference) element;
				return e.getName();
			}
		}, new Comparator<ReportPreference>() {
			@Override
			public int compare(ReportPreference e1, ReportPreference e2) {
				return e1.getName().compareToIgnoreCase(e2.getName());
			}
		});
	}

	private static ColumnDescription<ReportPreference> getTextColumn() {
		return new ColumnDescription<>("FileName", new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				ReportPreference e = (ReportPreference) element;
				return e.getFileName();
			}
		}, new Comparator<ReportPreference>() {
			@Override
			public int compare(ReportPreference e1, ReportPreference e2) {
				return e1.getFileName().compareToIgnoreCase(e2.getFileName());
			}
		});
	}
}