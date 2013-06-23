package de.hswt.hrm.misc.ui.PriorityComposite;

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
import de.hswt.hrm.misc.priority.model.Priority;
import de.hswt.hrm.misc.ui.PriorityWizard.PriorityWizard;

public class PriorityPartUtil {

	public static Optional<Priority> showWizard(IEclipseContext context,
			Shell shell, Optional<Priority> prio) {

		PriorityWizard ew = new PriorityWizard(context,prio);
		ContextInjectionFactory.inject(ew, context);

		WizardDialog wd = WizardCreator.createWizardDialog(shell, ew);
		wd.open();
		return ew.getPriority();

	}

	public static List<ColumnDescription<Priority>> getColumns() {
		List<ColumnDescription<Priority>> columns = new ArrayList<>();

		columns.add(getNameColumn());
		columns.add(getTextColumn());
		columns.add(getPriority());

		return columns;
	}

	private static ColumnDescription<Priority> getNameColumn() {
		return new ColumnDescription<>("Name", new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Priority e = (Priority) element;
				return e.getName();
			}
		}, new Comparator<Priority>() {
			@Override
			public int compare(Priority e1, Priority e2) {
				return e1.getName().compareToIgnoreCase(e2.getName());
			}
		});
	}

	private static ColumnDescription<Priority> getTextColumn() {
		return new ColumnDescription<Priority>("Text", new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Priority e = (Priority) element;
				return e.getText();
			}
		}, new Comparator<Priority>() {
			@Override
			public int compare(Priority e1, Priority e2) {
				return e1.getText().compareToIgnoreCase(e2.getText());
			}
		});
	}
	
    private static ColumnDescription<Priority> getPriority() {
        return new ColumnDescription<>("Priority", new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
                Priority prio = (Priority) element;
                return Integer.toString(prio.getPriority());
            }
        }, new Comparator<Priority>() {
            @Override
            public int compare(Priority c1, Priority c2) {
                String prio1 = Integer.toString(c1.getPriority());
                String prio2 = Integer.toString(c2.getPriority());
                return prio1.compareToIgnoreCase(prio2);
            }
        });
    }
}
