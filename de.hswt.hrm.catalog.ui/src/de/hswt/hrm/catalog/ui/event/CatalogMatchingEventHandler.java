package de.hswt.hrm.catalog.ui.event;

import org.eclipse.e4.xwt.XWT;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Text;

import de.hswt.hrm.catalog.model.ICatalogItem;

public class CatalogMatchingEventHandler {

    private List targets;
    private List currents;
    private List activities;

    private void handleSelection(TableViewer tv) {

        // obtain the place in the column where the doubleClick happend
        ICatalogItem selectedEntry = (ICatalogItem) tv.getElementAt(tv.getTable()
                .getSelectionIndex());
        if (selectedEntry == null) {
            return;
        }

        Text desc = (Text) XWT.findElementByName(tv, "desc");
        desc.setText(selectedEntry.getText());

        Text name = (Text) XWT.findElementByName(tv, "name");
        name.setText(selectedEntry.getName());
    }

    public void onMouseDown_3(Event event) {

        ListViewer lv = (ListViewer) XWT.findElementByName(event.widget, "matchedTarget");
        handleSelection(lv);
    }

    private void handleSelection(ListViewer lv) {

        // obtain the place in the column where the doubleClick happend
        ICatalogItem selectedEntry = (ICatalogItem) lv.getElementAt(lv.getList()
                .getSelectionIndex());
        if (selectedEntry == null) {
            return;
        }

        Text desc = (Text) XWT.findElementByName(lv, "desc");
        desc.setText(selectedEntry.getText());

        Text name = (Text) XWT.findElementByName(lv, "name");
        name.setText(selectedEntry.getName());

    }

    public void onMouseDoubleClick_1(Event event) {
        ListViewer lv = (ListViewer) XWT.findElementByName(event.widget, "matchedTarget");
        ICatalogItem item = (ICatalogItem) lv.getElementAt(lv.getList().getSelectionIndex());

        if (item == null) {
            return;
        }

        TableViewer tv = (TableViewer) XWT.findElementByName(lv, "availableTarget");
        tv.add(item);
        lv.remove(item);
    }
    public void onSelection(Event event) {
    }

	public List getActivities() {
		return activities;
	}

	public void setActivities(List activities) {
		this.activities = activities;
	}
}
