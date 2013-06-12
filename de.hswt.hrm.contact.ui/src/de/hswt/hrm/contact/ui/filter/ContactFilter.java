package de.hswt.hrm.contact.ui.filter;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import de.hswt.hrm.contact.model.Contact;

public class ContactFilter extends ViewerFilter {

    private String searchString;

    public void setSearchString(String substring) {
        searchString = (".*" + substring + ".*").toLowerCase();

    }

    @Override
    public boolean select(Viewer viewer, Object parentElement, Object element) {

        if (searchString == null || searchString.length() == 0) {
            return true;
        }

        Contact c = (Contact) element;

        if (c.getName().toLowerCase().matches(searchString)) {
            return true;
        }

        // match city
        if (c.getCity().toLowerCase().matches(searchString)) {
            return true;
        }

        return false;

    }
}
