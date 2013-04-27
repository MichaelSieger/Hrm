package de.hswt.hrm.place.ui.part.util;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;

import de.hswt.hrm.place.model.Place;


public class PlaceComparator extends ViewerComparator {
	private int propertyIndex;
    private static final int DESCENDING = 1;
    private int direction = 0;

    public PlaceComparator() {
        this.propertyIndex = 0;
        direction = 0;
    }

    public int getDirection() {
        return direction == 0 ? SWT.DOWN : SWT.UP;
    }

    public void setColumn(int column) {
        if (column == this.propertyIndex) {
            // Same column as last sort; toggle the direction
            direction = 1 - direction;
        }
        else {
            // New column; do an ascending sort
            this.propertyIndex = column;
            direction = DESCENDING;
        }
    }

    @Override
    public int compare(Viewer viewer, Object e1, Object e2) {
        Place p1 = (Place) e1;
        Place p2 = (Place) e2;
        
        int rc = 0;
        switch (propertyIndex) {
        case 0:
            rc = p1.getPlaceName().compareTo(p2.getPlaceName());
            break;
        case 1:
            rc = p1.getPostCode().compareTo(p2.getPostCode());
            break;
        case 2:
            rc = p1.getCity().compareTo(p2.getCity());
            break;
        case 3:
        	rc = p1.getStreet().compareTo(p2.getStreet());
            break;
        case 4:
            rc = p1.getStreetNo().compareTo(p2.getStreetNo());
            break;
        case 5:
            rc = p1.getLocation().compareTo(p2.getLocation());
            break;
        case 6:
            rc = p1.getArea().compareTo(p2.getArea());
            break;
        default:
            rc = 0;
        }
        
        // If descending order, flip the direction
        if (direction == DESCENDING) {
            rc = -rc;
        }
        
        return rc;
    }
}
