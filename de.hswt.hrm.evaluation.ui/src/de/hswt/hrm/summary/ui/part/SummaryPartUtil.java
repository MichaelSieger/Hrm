package de.hswt.hrm.summary.ui.part;

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
import de.hswt.hrm.summary.model.Summary;
import de.hswt.hrm.summary.ui.wizzard.SummaryWizzard;

public class SummaryPartUtil {

	public static Optional<Summary> showWizard(IEclipseContext context,
			Shell shell, Optional<Summary> eval) {

		SummaryWizzard ew = new SummaryWizzard(context,eval);
		ContextInjectionFactory.inject(ew, context);

		WizardDialog wd = WizardCreator.createWizardDialog(shell, ew);
		wd.open();
		return ew.getEval();

	}

	public static List<ColumnDescription<Summary>> getColumns() {
		List<ColumnDescription<Summary>> columns = new ArrayList<>();

		columns.add(getNameColumn());
		columns.add(getTextColumn());

		return columns;
	}

	private static ColumnDescription<Summary> getNameColumn() {
		return new ColumnDescription<>("Name", new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Summary e = (Summary) element;
				return e.getName();
			}
		}, new Comparator<Summary>() {
			@Override
			public int compare(Summary e1, Summary e2) {
				return e1.getName().compareToIgnoreCase(e2.getName());
			}
		});
	}

	private static ColumnDescription<Summary> getTextColumn() {
		return new ColumnDescription<>("Text", new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Summary e = (Summary) element;
				return e.getText();
			}
		}, new Comparator<Summary>() {
			@Override
			public int compare(Summary e1, Summary e2) {
				return e1.getText().compareToIgnoreCase(e2.getText());
			}
		});
	}
}
