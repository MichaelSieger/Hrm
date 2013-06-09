package de.hswt.hrm.catalog.ui.event;

import org.eclipse.e4.xwt.XWT;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.swt.widgets.Event;

import de.hswt.hrm.catalog.model.Catalog;
import de.hswt.hrm.catalog.model.ICatalogItem;

public class CatalogMatchingEventHandler {

    private final static String AT = "availableTarget";
    private final static String MT = "matchedTarget";
    private final static String AC = "availableCurrent";
    private final static String MC = "matchedCurrent";
    private final static String AA = "availableActivity";
    private final static String MA = "matchedActivity";

    /**
     * This Event is called whenever an entry is doubleClicked in the availableTarget ListViewer
     * 
     * @param event
     */
    public void availableTargetSelected(Event event) {

        ListViewer source = (ListViewer) XWT.findElementByName(event.widget, AT);
        ListViewer target = (ListViewer) XWT.findElementByName(event.widget, MT);

        moveEntry(source, target);

        if (target.getList().getItemCount() > 0) {
            target.getList().setEnabled(true);
            ((ListViewer) XWT.findElementByName(event.widget, AC)).getList().setEnabled(true);
        }

    }

    public void matchedTargetSelected(Event event) {

        ListViewer source = (ListViewer) XWT.findElementByName(event.widget, MT);
        ListViewer target = (ListViewer) XWT.findElementByName(event.widget, AT);
        moveEntry(source, target);

        if (source.getList().getItemCount() == 0) {
            ((ListViewer) XWT.findElementByName(event.widget, MT)).getList().setEnabled(false);
            ((ListViewer) XWT.findElementByName(event.widget, AC)).getList().setEnabled(false);
        }

    }

    public void availableCurrentSelected(Event event) {

        ListViewer source = (ListViewer) XWT.findElementByName(event.widget, AC);
        ListViewer target = (ListViewer) XWT.findElementByName(event.widget, MC);

        moveEntry(source, target);

        if (target.getList().getItemCount() > 0) {

            target.getList().setEnabled(true);
            ((ListViewer) XWT.findElementByName(event.widget, AA)).getList().setEnabled(true);

        }

    }

    public void matchedCurrentSelected(Event event) {

        ListViewer source = (ListViewer) XWT.findElementByName(event.widget, MC);
        ListViewer target = (ListViewer) XWT.findElementByName(event.widget, AC);
        moveEntry(source, target);

        if (source.getList().getItemCount() == 0) {
            ((ListViewer) XWT.findElementByName(event.widget, MC)).getList().setEnabled(false);
            ((ListViewer) XWT.findElementByName(event.widget, AA)).getList().setEnabled(false);
        }

    }

    public void availableActivitySelected(Event event) {

        ListViewer source = (ListViewer) XWT.findElementByName(event.widget, AA);
        ListViewer target = (ListViewer) XWT.findElementByName(event.widget, MA);
        moveEntry(source, target);

        if (source.getList().getItemCount() > 0) {
            target.getList().setEnabled(true);
        }

    }

    public void matchedActivitySelected(Event event) {

        ListViewer source = (ListViewer) XWT.findElementByName(event.widget, MA);
        ListViewer target = (ListViewer) XWT.findElementByName(event.widget, AA);
        moveEntry(source, target);

        if (source.getList().getItemCount() == 0) {
            source.getList().setEnabled(false);

        }

    }

    /**
     * This event occurs whenever an catalog entry is selected
     * 
     * @param event
     */
    public void onSelection(Event event) {

        ListViewer catalogs = (ListViewer) XWT.findElementByName(event.widget, "catalogs");
        Catalog c = (Catalog) catalogs.getElementAt(catalogs.getList().getSelectionIndex());
        if (c.getTargets().isEmpty()) {
            System.out.println("empty Targets, using defualts");
        }

        ListViewer alv = (ListViewer) XWT.findElementByName(event.widget, "availableTarget");
        alv.getList().setEnabled(true);
    }

    private void moveEntry(ListViewer source, ListViewer target) {

        ICatalogItem item = (ICatalogItem) source
                .getElementAt(source.getList().getSelectionIndex());
        if (item == null) {
            return;
        }

        target.add(item);
        source.remove(item);

    }

}
