package de.hswt.hrm.contact.model;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;

public class ContactComperator extends ViewerComparator {

    @Override
    public int compare(Viewer viewer, Object e1, Object e2) {
        Contact c1 = (Contact) e1;
        Contact c2 = (Contact) e2;
        return c1.getLastName().compareTo(c2.getLastName());

    }

}
