package de.hswt.hrm.catalog.ui.part;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.eclipse.jface.viewers.ColumnLabelProvider;

import de.hswt.hrm.catalog.model.Activity;
import de.hswt.hrm.catalog.model.Current;
import de.hswt.hrm.catalog.model.ICatalogItem;
import de.hswt.hrm.common.ui.swt.table.ColumnDescription;

public class CatalogPartUtil {
    
    
    
    public static List<ColumnDescription<ICatalogItem>> getColumns() {
        List<ColumnDescription<ICatalogItem>> columns = new ArrayList<>();
        columns.add(getPlaceColumn());
        columns.add(getNameColumn());
        columns.add(getDescColumn());
        return columns;
    }

    private static ColumnDescription<ICatalogItem> getPlaceColumn() {
        return new ColumnDescription<ICatalogItem>("Type", new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
                ICatalogItem p = (ICatalogItem) element;
                if (p instanceof Activity) {
                    return "Maßnahme";
                }
                else if (p instanceof Current) {
                    return "Ist";
                }
                else
                    return "Soll";
            }
        }, new Comparator<ICatalogItem>() {

            @Override
            public int compare(ICatalogItem o1, ICatalogItem o2) {
                // TODO better and (working) solution
                return "s".compareTo("i");
            }

        });
    }

    private static ColumnDescription<ICatalogItem> getNameColumn() {
        return new ColumnDescription<ICatalogItem>("Name", new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
                ICatalogItem i = (ICatalogItem) element;
                return i.getName();
            }
        }, new Comparator<ICatalogItem>() {

            @Override
            public int compare(ICatalogItem o1, ICatalogItem o2) {
                return o1.getName().compareToIgnoreCase(o2.getName());
            }

        });
    }

    private static ColumnDescription<ICatalogItem> getDescColumn() {
        return new ColumnDescription<ICatalogItem>("Description", new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
                ICatalogItem i = (ICatalogItem) element;
                return i.getText();
            }
        }, new Comparator<ICatalogItem>() {

            @Override
            public int compare(ICatalogItem o1, ICatalogItem o2) {
                // TODO better solution
                return o1.getText().compareToIgnoreCase(o2.getText());
            }

        });
    }

}
