package de.hswt.hrm.summary.ui.filter;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import de.hswt.hrm.summary.model.Summary;

public class SummaryFilter extends ViewerFilter {

    private String searchString;

    public void setSearchString(String substring) {
        searchString = (".*" + substring + ".*").toLowerCase();
    }

    @Override
    public boolean select(Viewer viewer, Object parentElement, Object element) {
        if (searchString == null || searchString.length() == 0) {
            return true;
        }

        Summary e = (Summary) element;

        if (e.getName().toLowerCase().matches(searchString)) {
            return true;
        }

        if (e.getText().toLowerCase().matches(searchString)) {
            return true;
        }

        return false;
    }

}
