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
import de.hswt.hrm.inspection.model.Layout;
import de.hswt.hrm.misc.reportPreference.model.ReportPreference;
import de.hswt.hrm.misc.ui.preferenceswizard.PreferencesWizard;

public class ReportPreferenceUtil {

	public static Optional<Layout> showWizard(IEclipseContext context,
			Shell shell, Optional<Layout> pref) {

		PreferencesWizard ew = new PreferencesWizard(context,pref);
		ContextInjectionFactory.inject(ew, context);

		WizardDialog wd = WizardCreator.createWizardDialog(shell, ew);
		wd.open();
		return ew.getPreference();

	}

	public static List<ColumnDescription<Layout>> getColumns() {
		List<ColumnDescription<Layout>> columns = new ArrayList<>();

		columns.add(getNameColumn());
		columns.add(getTextColumn());

		return columns;
	}

	private static ColumnDescription<Layout> getNameColumn() {
		return new ColumnDescription<>("Name", new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Layout e = (Layout) element;
				return e.getName();
			}
		}, new Comparator<Layout>() {
			@Override
			public int compare(Layout e1, Layout e2) {
				return e1.getName().compareToIgnoreCase(e2.getName());
			}
		});
	}

	private static ColumnDescription<Layout> getTextColumn() {
		return new ColumnDescription<>("File name", new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Layout e = (Layout) element;
				return e.getFileName();
			}
		}, new Comparator<Layout>() {
			@Override
			public int compare(Layout e1, Layout e2) {
				return e1.getFileName().compareToIgnoreCase(e2.getFileName());
			}
		});
	}
}
