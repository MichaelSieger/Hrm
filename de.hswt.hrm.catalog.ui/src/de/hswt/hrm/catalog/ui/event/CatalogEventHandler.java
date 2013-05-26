package de.hswt.hrm.catalog.ui.event;

import org.eclipse.e4.xwt.XWT;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Text;

import de.hswt.hrm.catalog.ui.filter.CatalogFilter;

public class CatalogEventHandler {

    private static final String DEFAULT_SEARCH_STRING = "Search";
    private static final String EMPTY = "";

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

    /**
     * This event is called whenever the Search text field is entered
     * 
     * @param event
     */
    public void enterText(Event event) {
        Text text = (Text) event.widget;
        if (text.getText().equals(DEFAULT_SEARCH_STRING)) {
            text.setText(EMPTY);
        }
    }

    public void leaveText(Event event) {

        Text text = (Text) event.widget;
        if (text.getText().isEmpty()) {
            text.setText(DEFAULT_SEARCH_STRING);
        }
        TableViewer tf = (TableViewer) XWT.findElementByName(text, "catalogTable");
        tf.refresh();

    }

    public void onKeyUp(Event event) {
        Text searchText = (Text) event.widget;
        TableViewer tf = (TableViewer) XWT.findElementByName(searchText, "catalogTable");
        CatalogFilter f = (CatalogFilter) tf.getFilters()[0];
        f.setSearchString(searchText.getText());
        tf.refresh();
    }
  
}
