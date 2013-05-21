package de.hswt.hrm.catalog.ui.event;

import org.eclipse.e4.xwt.XWT;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Event;

import de.hswt.hrm.catalog.ui.filter.CatalogFilter;

public class CatalogEventHandler {

    public void onSelection(Event event) {

        Button b = (Button) event.widget;
        TableViewer tf = (TableViewer) XWT.findElementByName(b, "catalogTable");
        CatalogFilter f = (CatalogFilter) tf.getFilters()[0];

        if (b.getText().equalsIgnoreCase("all")) {
            f.setSearchString("");
            tf.refresh();
            return;
        }

        else if (b.getText().equalsIgnoreCase("maßnahme")) {
            f.setSearchString(b.getText());
            tf.refresh();
            return;
        }
        f.setSearchString(b.getText());
        tf.refresh();
    }

}
