package de.hswt.hrm.catalog.ui.filter;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import de.hswt.hrm.catalog.model.Activity;
import de.hswt.hrm.catalog.model.ICatalogItem;

public class CatalogFilter extends ViewerFilter {

    private String searchString;

    public void setSearchString(String searchString) {
        this.searchString = searchString;

    }

    @Override
    public boolean select(Viewer viewer, Object parentElement, Object element) {

        boolean matches = false;

        if (searchString == null || searchString.length() == 0) {
            return true;
        }

        ICatalogItem i = (ICatalogItem) element;

        switch (searchString) {
        case "All":
            matches = true;
            break;
        case "Maßnahme":
            if (i instanceof Activity) {
                matches = true;
            }
            else
                matches = false;

        }

        return matches;

    }
}
