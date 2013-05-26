package de.hswt.hrm.catalog.ui.event;

import org.eclipse.e4.xwt.XWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.List;

public class CatalogMatchingEventHandler {

    private List targets;
    private List currents;
    private List activities;

    public void onAvailableTargetMouseDoubleClick(Event event) {

        List l = (List) event.widget;

        int i = l.getSelectionIndex();

        if (i == -1) {
            return;
        }

        String s = l.getItem(i);

        l.remove(s);
        List l2 = (List) XWT.findElementByName(l, "matchedTarget");
        l2.add(s);
        currents = (List) XWT.findElementByName(l, "availableCurrent");
        currents.setEnabled(true);

    }

    public void onMatchedTargetMouseDoubleClick(Event event) {

        List l = (List) event.widget;
        int i = l.getSelectionIndex();

        if (i == -1) {
            return;
        }

        String s = l.getItem(i);
        l.remove(s);
        List l2 = (List) XWT.findElementByName(l, "availableTarget");
        l2.add(s);
        String[] items = l2.getItems();
        java.util.Arrays.sort(items);
        l2.setItems(items);

        if (l.getItemCount() == 0) {
            currents = (List) XWT.findElementByName(l, "availableCurrent");
            currents.setEnabled(false);
        }

    }
}
