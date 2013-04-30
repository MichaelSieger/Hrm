package de.hswt.hrm.common.ui.swt.table;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;

public class ExtendedTableViewer extends TableViewer {

    public ExtendedTableViewer(Composite parent, int style) {
        super(parent, style);
    }

    public ExtendedTableViewer(Composite parent) {
        super(parent);
    }

    public ExtendedTableViewer(Table table) {
        super(table);
    }

    


}
