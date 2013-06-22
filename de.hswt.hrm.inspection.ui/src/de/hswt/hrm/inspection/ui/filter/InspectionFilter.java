package de.hswt.hrm.inspection.ui.filter;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import de.hswt.hrm.inspection.model.Inspection;

public class InspectionFilter extends ViewerFilter {

    private String searchString;

    public void setSearchString(String substring) {
        searchString = (".*" + substring + ".*").toLowerCase();

    }

    @Override
    public boolean select(Viewer viewer, Object parentElement, Object element) {
        if (searchString == null || searchString.length() == 0) {
            return true;
        }

        Inspection i = (Inspection) element;

        // Match inspection Title
        if (i.getTitle().toLowerCase().matches(searchString)) {
            return true;
        }
        // Match plant Description
        if (i.getPlant().getDescription().toLowerCase().matches(searchString)) {
            return true;
        }

        return false;

    }

}
