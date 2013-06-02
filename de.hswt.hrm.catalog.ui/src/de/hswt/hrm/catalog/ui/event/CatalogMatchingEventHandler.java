package de.hswt.hrm.catalog.ui.event;

import org.eclipse.e4.xwt.XWT;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.swt.widgets.Event;

import de.hswt.hrm.catalog.model.Catalog;
import de.hswt.hrm.catalog.model.ICatalogItem;

public class CatalogMatchingEventHandler {

    /**
     * This Event is called whenever an entry is doubleClicked in the availableTarget ListViewer
     * 
     * @param event
     */
    public void onMouseDoubleClick(Event event) {

        ListViewer alv = (ListViewer) XWT.findElementByName(event.widget, "availableTarget");
        ListViewer mlv = (ListViewer) XWT.findElementByName(event.widget, "matchedTarget");

        ICatalogItem item = (ICatalogItem) alv.getElementAt(alv.getList().getSelectionIndex());
        if (item == null) {
            return;
        }

        mlv.add(item);
        alv.remove(item);

    }

    /**
     * This event occurs whenever an catalog entry is selected
     * 
     * @param event
     */
    public void onSelection(Event event) {

        ListViewer catalogs = (ListViewer) XWT.findElementByName(event.widget, "catalogs");
        Catalog c = (Catalog) catalogs.getElementAt(catalogs.getList().getSelectionIndex());

        ListViewer alv = (ListViewer) XWT.findElementByName(event.widget, "availableTarget");
        alv.getList().setEnabled(true);
    }
}
