package de.hswt.hrm.contact.ui.filter;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

public class ContactFilter extends ViewerFilter {

    private String searchString;

    public void setSearchString(String substring) {
        this.searchString = ".*" + substring + ".*";
    }

    @Override
    public boolean select(Viewer viewer, Object parentElement, Object element) {
        // TODO Auto-generated method stub
        return false;
    }

}
