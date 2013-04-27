package de.hswt.hrm.place.ui.filter;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import de.hswt.hrm.place.model.Place;

public class PlaceFilter extends ViewerFilter {
	private String searchString;

    public void setSearchString(String substring) {
        searchString = (".*" + substring + ".*").toLowerCase();

    }

    @Override
    public boolean select(Viewer viewer, Object parentElement, Object element) {

        if (searchString == null || searchString.length() == 0) {
            return true;
        }

        Place p = (Place) element;
        // match placeName
        if (p.getPlaceName().toLowerCase().matches(searchString)) {
            return true;
        }
        // match location
        if (p.getLocation().toLowerCase().matches(searchString)) {
            return true;
        }
        // match area
        if (p.getArea().toLowerCase().matches(searchString)) {
            return true;
        }
        
        // match city
        if (p.getCity().toLowerCase().matches(searchString)) {
            return true;
        }

        return false;

    }
}
