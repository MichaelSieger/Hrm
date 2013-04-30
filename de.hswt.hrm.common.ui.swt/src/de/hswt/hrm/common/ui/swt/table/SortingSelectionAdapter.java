package de.hswt.hrm.common.ui.swt.table;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.TableColumn;

public class SortingSelectionAdapter extends SelectionAdapter {
    private final TableViewer viewer;
    private final ColumnComparator comparator;
    private TableColumn lastSelection;
    private Direction dir = Direction.ASCENDING;
    
    public SortingSelectionAdapter(final TableViewer viewer, final ColumnComparator comparator) {
        super();
        
        this.viewer = viewer;
        this.comparator = comparator;
    }

    @Override
    public void widgetSelected(SelectionEvent e) {
        if ( !(e.getSource() instanceof TableColumn)) {
            throw new IllegalArgumentException("Can only sort TableColumns.");
        }
        
        TableColumn currentCol = (TableColumn) e.getSource();
        
        if (lastSelection != null && lastSelection.equals(currentCol)) {
            if (dir == Direction.ASCENDING) {
                dir = Direction.DESCENDING;
            }
            else {
                dir = Direction.ASCENDING;
            }
        }
        
//        int computed = Arrays.asList(viewer.getTable().getColumns()).indexOf(e.getSource());
        comparator.setCurrentColumn(currentCol);
        comparator.setDirection(dir);
        viewer.getTable().setSortDirection(dir.getValue());
        viewer.getTable().setSortColumn(currentCol);
        viewer.refresh();

    }
}
