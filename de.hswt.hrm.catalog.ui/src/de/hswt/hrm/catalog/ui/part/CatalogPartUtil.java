package de.hswt.hrm.catalog.ui.part;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;

import com.google.common.base.Optional;

import de.hswt.hrm.catalog.model.Activity;
import de.hswt.hrm.catalog.model.Current;
import de.hswt.hrm.catalog.model.ICatalogItem;
import de.hswt.hrm.catalog.model.Target;
import de.hswt.hrm.catalog.ui.wizzard.CatalogWizard;
import de.hswt.hrm.common.ui.swt.table.ColumnDescription;

public final class CatalogPartUtil {

    private final static String TARGET = "Soll";
    private final static String CURRENT = "Ist";
    private final static String ACTIVITY = "Maßnahme";

    public CatalogPartUtil() {

    }

    public static Optional<ICatalogItem> showWizard(IEclipseContext context, Shell shell,
            Optional<ICatalogItem> item) {
        // TODO: partly move to extra plugin

        // Create wizard with injection support
        CatalogWizard wizard = new CatalogWizard(item);
        ContextInjectionFactory.inject(wizard, context);

        // Show wizard
        WizardDialog wd = new WizardDialog(shell, wizard);
        wd.open();
        return wizard.getItem();

    }

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
                    return ACTIVITY;
                }
                else if (p instanceof Current) {
                    return CURRENT;
                }
                else if (p instanceof Target) {
                    return TARGET;
                }

                return "";
            }
        }, new Comparator<ICatalogItem>() {

            @Override
            public int compare(ICatalogItem o1, ICatalogItem o2) {

                return compareItems(o1, o2);
            }
        });
    }

    private static int compareItems(ICatalogItem o1, ICatalogItem o2) {
        if (o1 instanceof Activity) {
            if (o2 instanceof Current) {
                return ACTIVITY.compareToIgnoreCase(CURRENT);
            }
            return ACTIVITY.compareToIgnoreCase(TARGET);

        }
        else if (o1 instanceof Current) {
            if (o2 instanceof Activity) {
                return CURRENT.compareTo(ACTIVITY);
            }
            return CURRENT.compareTo(TARGET);
        }

        else if (o1 instanceof Target) {
            if (o2 instanceof Activity) {
                TARGET.compareTo(ACTIVITY);
            }
            return TARGET.compareTo(CURRENT);
        }
        return 0;

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
