package de.hswt.hrm.catalog.ui.filter;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

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

        String s = "Maßnahme";
        if (s.matches(searchString)) {
            return true;
        }

        return false;

    }
}
