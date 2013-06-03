package de.hswt.hrm.catalog.ui.filter;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import de.hswt.hrm.catalog.model.Activity;
import de.hswt.hrm.catalog.model.Current;
import de.hswt.hrm.catalog.model.ICatalogItem;
import de.hswt.hrm.catalog.model.Target;

public class CatalogSelectionFilter extends ViewerFilter {

    private boolean targetSelected = false;
    private boolean currentSelected = false;
    private boolean activitySelected = false;
    private boolean allSelected = false;

    @Override
    public boolean select(Viewer viewer, Object parentElement, Object element) {

        if (!allSelected && !targetSelected && !currentSelected && !activitySelected) {
            return true;
        }

        ICatalogItem item = (ICatalogItem) element;

        if (allSelected) {
            return true;
        }

        if (activitySelected && item instanceof Activity) {
            return true;
        }

        else if (currentSelected && item instanceof Current) {
            return true;
        }

        else if (targetSelected && item instanceof Target) {
            return true;
        }

        return false;

    }

    public void setTargetSelected(boolean targetSelected) {
        this.targetSelected = targetSelected;
    }

    public void setCurrentSelected(boolean currentSelected) {
        this.currentSelected = currentSelected;
    }

    public void setActivitySelected(boolean activitySelected) {
        this.activitySelected = activitySelected;
    }

    public void setAllSelected(boolean allSelected) {
        this.allSelected = allSelected;
    }
}
