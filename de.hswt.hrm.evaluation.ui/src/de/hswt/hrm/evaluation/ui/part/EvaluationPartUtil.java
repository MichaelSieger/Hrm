package de.hswt.hrm.evaluation.ui.part;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.swt.widgets.Shell;

import com.google.common.base.Optional;

import de.hswt.hrm.common.ui.swt.table.ColumnDescription;
import de.hswt.hrm.evaluation.model.Evaluation;

public class EvaluationPartUtil {

    public static Optional<Evaluation> showWizard(IEclipseContext context, Shell activeShell,
            Optional<Evaluation> absent, Collection<Evaluation> evaluations) {

        return null;
    }

    public static List<ColumnDescription<Evaluation>> getColumns() {
        List<ColumnDescription<Evaluation>> columns = new ArrayList<>();

        columns.add(getNameColumn());
        columns.add(getTextColumn());

        return columns;
    }

    private static ColumnDescription<Evaluation> getNameColumn() {
        return new ColumnDescription<>("Name", new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
                Evaluation e = (Evaluation) element;
                return e.getName();
            }
        }, new Comparator<Evaluation>() {
            @Override
            public int compare(Evaluation e1, Evaluation e2) {
                return e1.getName().compareToIgnoreCase(e2.getName());
            }
        });
    }

    private static ColumnDescription<Evaluation> getTextColumn() {
        return new ColumnDescription<>("Text", new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
                Evaluation e = (Evaluation) element;
                return e.getText();
            }
        }, new Comparator<Evaluation>() {
            @Override
            public int compare(Evaluation e1, Evaluation e2) {
                return e1.getText().compareToIgnoreCase(e2.getText());
            }
        });
    }
}
