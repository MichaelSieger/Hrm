package de.hswt.hrm.plant.ui.shared.filter;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import de.hswt.hrm.plant.model.Plant;

public class PlantFilter extends ViewerFilter {

    private String searchString;

    public void setSearchString(String substring) {
        searchString = (".*" + substring + ".*").toLowerCase();

    }

    @Override
    public boolean select(Viewer viewer, Object parentElement, Object element) {

        if (searchString == null || searchString.length() == 0) {
            return true;
        }

        Plant p = (Plant) element;
        // match Description
        if (p.getDescription().toLowerCase().matches(searchString)) {
            return true;
        }

        if (p.getPlace().get().getPlaceName().toLowerCase().matches(searchString)) {
            return true;
        }

        return false;

    }

}
