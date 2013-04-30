package de.hswt.hrm.common.ui.swt.table;

import java.util.List;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.TableColumn;

public class TableViewerFiller {
    private final TableViewer viewer;
    private ColumnComparator comparator;
    
    public TableViewerFiller(final TableViewer viewer) {
        this.viewer = viewer;
    }
    
    public <T> void createColumns(final Composite parent, 
            final List<ColumnDescription<T>> columnDescriptions) {

        if (parent == null || columnDescriptions.size() < 1) {
            return;
        }

        for (int i = 0; i < columnDescriptions.size(); i++) {
            ColumnDescription<T> desc = columnDescriptions.get(i);
            TableViewerColumn col = createTableViewerColumn(desc.getHeader(), desc.getWidth(),
                    viewer, i);
            col.setLabelProvider(desc.getLabelProvider());
        }
    }
    
    public void createColumnSelectionMenu() {
        if (viewer.getTable().getColumns().length < 1) {
            return;
        }
        
        Menu headerMenu = new Menu(viewer.getTable());
        viewer.getTable().setMenu(headerMenu);
        
        for (TableColumn col : viewer.getTable().getColumns()) {
            createMenuItem(headerMenu, col, col.getWidth());
        }
    }
    
    public void enableSorting(ColumnComparator comparator) {
        setComparator(comparator);        
        
        if (viewer.getTable().getColumns().length < 1) {
            return;
        }
        
        SortingSelectionAdapter selectionAdapter = new SortingSelectionAdapter(viewer, 
                this.comparator);
        for (TableColumn col : viewer.getTable().getColumns()) {
            col.addSelectionListener(selectionAdapter);
        }
    }
    
    public void setComparator(ColumnComparator comparator) {
        this.comparator = comparator;
    }

    private static void createMenuItem(Menu parent, final TableColumn column, final int width) {
        final MenuItem itemName = new MenuItem(parent, SWT.CHECK);
        itemName.setText(column.getText());
        itemName.setSelection(column.getResizable());
        itemName.addListener(SWT.Selection, new Listener() {
            public void handleEvent(Event event) {
                if (itemName.getSelection()) {
                    column.setWidth(width);
                    column.setResizable(true);
                }
                else {
                    column.setWidth(0);
                    column.setResizable(false);
                }
            }
        });

    }

    private static TableViewerColumn createTableViewerColumn(String title, int bound,
            TableViewer viewer, int colNumber) {
        TableViewerColumn viewerColumn = new TableViewerColumn(viewer, SWT.NONE);
        TableColumn column = viewerColumn.getColumn();
        column.setText(title);
        column.setWidth(bound);
        column.setResizable(true);
        column.setMoveable(true);
        return viewerColumn;
    }

}
