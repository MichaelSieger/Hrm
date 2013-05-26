package de.hswt.hrm.catalog.ui.filter;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import de.hswt.hrm.catalog.model.ICatalogItem;

public class CatalogFilter extends ViewerFilter {

    private String searchString;

    public void setSearchString(String substring) {
        searchString = (".*" + substring + ".*").toLowerCase();
    }

    @Override
    public boolean select(Viewer viewer, Object parentElement, Object element) {

        if (searchString == null || searchString.length() == 0) {
            return true;
        }

        ICatalogItem i = (ICatalogItem) element;

        if (i.getName().toLowerCase().matches(searchString)) {
            return true;
        }

        if (i.getText().toLowerCase().matches(searchString)) {
            return true;
        }

        return false;

    }
}
