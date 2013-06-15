package de.hswt.hrm.component.ui.filter;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import de.hswt.hrm.component.model.Component;

public class ComponentFilter extends ViewerFilter {

    private String searchString;

    public void setSearchString(String substring) {
        searchString = (".*" + substring + ".*").toLowerCase();

    }

    @Override
    public boolean select(Viewer viewer, Object parentElement, Object element) {

        if (searchString == null || searchString.length() == 0) {
            return true;
        }

        Component c = (Component) element;
        // match Name
        if (c.getName().toLowerCase().matches(searchString)) {
            return true;
        }
        // match Category
        if (((String) c.getCategory().get().getName()).toLowerCase().matches(searchString)) {
            return true;
        }
        return false;

    }
}
