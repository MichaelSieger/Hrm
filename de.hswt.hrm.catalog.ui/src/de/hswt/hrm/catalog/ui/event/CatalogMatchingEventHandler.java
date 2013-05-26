package de.hswt.hrm.catalog.ui.event;

import org.eclipse.e4.xwt.XWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.List;

public class CatalogMatchingEventHandler {

    private List targets;
    private List currents;
    private List activities;

    /**
     * This event occurs whenever an entry in the upper table is selected
     * 
     * @param event
     */
    public void onMatchedTargetMouseDoubleClick(Event event) {

        List matched = (List) event.widget;
        int i = matched.getSelectionIndex();

        if (i == -1) {
            return;
        }

        String s = matched.getItem(i);
        matched.remove(s);
        List l2 = (List) XWT.findElementByName(matched, "availableTarget");
        l2.add(s);
        String[] items = l2.getItems();
        java.util.Arrays.sort(items);
        l2.setItems(items);

        if (matched.getItemCount() == 1) {
            currents = (List) XWT.findElementByName(matched, "availableCurrent");
            currents.setEnabled(true);
            targets = (List) XWT.findElementByName(matched, "availableTarget");
            targets.setEnabled(false);
        }

        else if (matched.getItemCount() == 0) {
            currents = (List) XWT.findElementByName(matched, "availableCurrent");
            currents.setEnabled(false);
            targets = (List) XWT.findElementByName(matched, "availableTarget");
            targets.setEnabled(true);
        }

    }
}
