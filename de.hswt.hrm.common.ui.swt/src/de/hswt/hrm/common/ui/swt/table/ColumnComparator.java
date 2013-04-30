package de.hswt.hrm.common.ui.swt.table;

import java.util.Comparator;
import java.util.Map;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.widgets.TableColumn;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkArgument;

public class ColumnComparator<T> extends ViewerComparator {
    private final Map<TableColumn, Comparator<T>> comparators;
    private Direction dir = Direction.ASCENDING;
    private TableColumn currentCol;
    
    public ColumnComparator(Map<TableColumn, Comparator<T>> comparators) {
        super();
        
        checkNotNull(comparators, "You have to specify map of table comparators.");
        checkArgument(comparators.size() > 0, "You have to specify a non empty map of table comparators.");
        this.comparators = comparators;
    }
    
    @Override
    public int compare(Viewer viewer, Object e1, Object e2) {
        Comparator<T> comparator = comparators.get(currentCol);
        if (comparator == null) {
            // We use the first column if none is selected
        	comparator = comparators.values().iterator().next();
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
    
    public void setCurrentColumn(TableColumn curCol) {
        currentCol = curCol;
    }
    
    public void setDirection(Direction dir) {
        this.dir = dir;
    }
}
