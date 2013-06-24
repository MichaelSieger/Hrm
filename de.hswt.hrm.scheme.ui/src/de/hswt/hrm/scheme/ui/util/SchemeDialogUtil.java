package de.hswt.hrm.scheme.ui.util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.eclipse.jface.viewers.ColumnLabelProvider;

import de.hswt.hrm.common.ui.swt.table.ColumnDescription;
import de.hswt.hrm.component.model.Attribute;

public final class SchemeDialogUtil {

    private SchemeDialogUtil() {

    }

    public static List<ColumnDescription<Attribute>> getColumns() {
        List<ColumnDescription<Attribute>> columns = new ArrayList<>();
        columns.add(getNameColumn());
        columns.add(getValueColumn());
        return columns;
    }

    private static ColumnDescription<Attribute> getNameColumn() {
        return new ColumnDescription<Attribute>("Name", new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
                Attribute a = (Attribute) element;
                return a.getName();
            }
        }, new Comparator<Attribute>() {

            @Override
            public int compare(Attribute a1, Attribute a2) {

                return a1.getName().compareToIgnoreCase(a2.getName());
            }

        });
    }

    private static ColumnDescription<Attribute> getValueColumn() {
        return new ColumnDescription<Attribute>("Value", new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
                return "";

            }
        }, new Comparator<Attribute>() {

            @Override
            public int compare(Attribute a1, Attribute a2) {

                return a1.getName().compareToIgnoreCase(a2.getName());
            }

        });
    }
}
