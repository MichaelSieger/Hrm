package de.hswt.hrm.component.ui.event;

import org.eclipse.e4.xwt.XWT;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hswt.hrm.component.model.Category;
import de.hswt.hrm.component.ui.filter.CategoryFilter;
import de.hswt.hrm.scheme.service.CategoryService;

public class CategoryEventHandler {
    
    private final static Logger LOG = LoggerFactory.getLogger(CategoryEventHandler.class);
    private static final String DEFAULT_SEARCH_STRING = "Suche";
    private static final String EMPTY ="";
    private Category category;
    
    private final CategoryService categoryService = new CategoryService();

    /**
     * Called whenever the search text field is leaved.
     * If the field is blank, the DEFAULT_SEARCH_STRING is inserted.
     * @param event
     */
    public void leaveText(Event event) {
        Text text = (Text) event.widget;
        if (text.getText().isEmpty()) {
            text.setText(DEFAULT_SEARCH_STRING);
        }
        TableViewer tViewer = (TableViewer) XWT.findElementByName(text, "categoryTable");
        tViewer.refresh();
    }
    
    /**
     * Called whenever a Text is entered into the search text field.
     * @param event
     */
    public void onKeyUp(Event event) {
        Text searchText = (Text) event.widget;
        TableViewer tViewer = (TableViewer) XWT.findElementByName(searchText, "categoryTable");
        CategoryFilter filter = (CategoryFilter) tViewer.getFilters()[0];
        filter.setSearchString(searchText.getText());
        tViewer.refresh();
    }
    
    /**
     * Called whenever the search text field is entered
     * @param event
     */
    public void enterText(Event event) {
        Text text = (Text) event.widget;
        if (text.getText().equals(DEFAULT_SEARCH_STRING)) {
            text.setText(EMPTY);
        }
    }
    
    

}
