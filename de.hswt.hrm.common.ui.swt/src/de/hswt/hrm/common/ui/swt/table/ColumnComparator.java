package de.hswt.hrm.common.ui.swt.table;

import java.util.Comparator;
import java.util.List;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkArgument;

public class ColumnComparator<T> extends ViewerComparator {
    private final List<ColumnDescription<T>> descriptions;
    private int currentColIndex = -1;
    private Direction dir = Direction.ASCENDING;
    
    public ColumnComparator(final List<ColumnDescription<T>> descriptions) {
        super();
        
        checkNotNull(descriptions, "You have to specify the list of column descriptions.");
        checkArgument(descriptions.size() > 0, "You have to specify a non empty list of column descriptions.");
        this.descriptions = descriptions;
    }
    
    @Override
    public int compare(Viewer viewer, Object e1, Object e2) {
        Comparator<T> comparator = null;
        if (currentColIndex < 0) {
            // We use the first column if none is selected
            comparator = descriptions.get(0).getComparator();
        }
        else {
            comparator = descriptions.get(currentColIndex).getComparator();
        }
        
        // TODO check instance!
        @SuppressWarnings("unchecked")
		T o1 = (T) e1;
        @SuppressWarnings("unchecked")
        T o2 = (T) e2;
        
        int result = comparator.compare(o1, o2);
        
        if (dir == Direction.DESCENDING) {
            result = -result;
        }
        
        return result;
    }
    
    public void setCurrentColumnIndex(final int index) {
        this.currentColIndex = index;
    }
    
    public void setDirection(Direction dir) {
        this.dir = dir;
    }
}
