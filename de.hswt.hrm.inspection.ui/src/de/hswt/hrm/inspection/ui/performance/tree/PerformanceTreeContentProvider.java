package de.hswt.hrm.inspection.ui.performance.tree;

import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import de.hswt.hrm.inspection.model.tree.TreeCurrent;
import de.hswt.hrm.inspection.model.tree.TreeTarget;

public class PerformanceTreeContentProvider implements ITreeContentProvider {

    private static final Object[] EMPTY_ARRAY = new Object[0];

    @Override
    public void dispose() {
    }

    @Override
    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Object[] getElements(Object inputElement) {
        if (inputElement instanceof List) {
            return ((List) inputElement).toArray();
        }
        else
            return EMPTY_ARRAY;

    }

    @Override
    public Object[] getChildren(Object parentElement) {
        if (parentElement instanceof TreeTarget) {
            TreeTarget target = (TreeTarget) parentElement;
            return target.getCurrents().toArray();
        }
        else if (parentElement instanceof TreeCurrent) {
            TreeCurrent current = (TreeCurrent) parentElement;
            return current.getActivities().toArray();
        }
        return EMPTY_ARRAY;
    }

    @Override
    public Object getParent(Object element) {
        return null;
    }

    @Override
    public boolean hasChildren(Object element) {
        if (element instanceof TreeTarget || element instanceof TreeCurrent) {
            return true;
        }
        return false;
    }

}
