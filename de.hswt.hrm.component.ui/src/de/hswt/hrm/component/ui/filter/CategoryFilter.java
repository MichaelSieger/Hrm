package de.hswt.hrm.component.ui.filter;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import de.hswt.hrm.component.model.Category;


public class CategoryFilter extends ViewerFilter {
    
    private String searchString;

    public void setSearchString(String substring) {
        searchString = (".*" + substring + ".*").toLowerCase();
    }

    @Override
    public boolean select(Viewer viewer, Object parentElement, Object element) {
        
        if (searchString == null || searchString.length() == 0) {
            return true;
        }
        Category cat = (Category) element;
        // match name
        if (cat.getName().toLowerCase().matches(searchString)) {
            return true;
        }
        // match defaultQuantifier
        String s1 = Integer.toString(cat.getDefaultQuantifier());
        if (s1.matches(searchString)) {
            return true;
        }
        // match defaultBoolRating
        if (cat.getDefaultBoolRating()) {
            s1 = "ja";
        } else {
            s1 = "nein";
        }
        if (s1.matches(searchString)) {
            return true;
        }
        // match width
        s1 = Integer.toString(cat.getWidth());
        if (s1.matches(searchString)) {
            return true;
        }
        // match height
        s1 = Integer.toString(cat.getHeight());
        if (s1.matches(searchString)) {
            return true;
        }
        
        return false;
    }

}
