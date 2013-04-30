package de.hswt.hrm.common.ui.swt.table;

import java.util.Comparator;

import org.eclipse.jface.viewers.ColumnLabelProvider;

public class ColumnDescription<T> {
    private final String header;
    private final ColumnLabelProvider labelProvider;
    private final Comparator<T> comparator;
    private final int width;
    
    public ColumnDescription(String header, ColumnLabelProvider labelProvider, 
            Comparator<T> comparator) {
        this(header, labelProvider, comparator, 200);
    }
    
    public ColumnDescription(String header, ColumnLabelProvider labelProvider, 
            Comparator<T> comparator, int width) {
        
        this.header = header;
        this.labelProvider = labelProvider;
        this.comparator = comparator;
        this.width = width;
    }

    public String getHeader() {
        return header;
    }

    public ColumnLabelProvider getLabelProvider() {
        return labelProvider;
    }

    public Comparator<T> getComparator() {
        return comparator;
    }

    public int getWidth() {
        return width;
    }
}
