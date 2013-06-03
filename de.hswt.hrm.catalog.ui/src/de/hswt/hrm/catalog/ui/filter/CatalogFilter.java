package de.hswt.hrm.catalog.ui.filter;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import de.hswt.hrm.catalog.model.Activity;
import de.hswt.hrm.catalog.model.Current;
import de.hswt.hrm.catalog.model.ICatalogItem;
import de.hswt.hrm.catalog.model.Target;

public class CatalogFilter extends ViewerFilter {

    // private String searchString;
    //

    private boolean targetSelected = false;
    private boolean currentSelected = false;
    private boolean activitySelected = false;
    private boolean allSelected = false;

    // public void setSearchString(String substring) {
    // searchString = (".*" + substring + ".*").toLowerCase();
    // }
    //
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

        //
        // if (searchString == null || searchString.length() == 0) {
        // return true;
        // }
        //
        // ICatalogItem i = (ICatalogItem) element;
        //
        // if (i.getName().toLowerCase().matches(searchString)) {
        // return true;
        // }
        //
        // if (i.getText().toLowerCase().matches(searchString)) {
        // return true;
        // }
        //
        // return false;

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
