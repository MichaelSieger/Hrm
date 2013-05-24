package de.hswt.hrm.component.ui.event;

import java.util.Collection;

import org.eclipse.e4.xwt.XWT;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;

import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.component.model.Category;
import de.hswt.hrm.component.ui.filter.CategoryFilter;
import de.hswt.hrm.component.ui.part.CategoryPartUtil;
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
    
    /**
     * Called whenever a doubleClick into the TableViewer occurs. It obtains
     * the category from the selected line if the TableViewer. The category
     * is passed to the CategoryWizard. When the Wizard has finished, the
     * category will be updated in the database.
     * @param event
     */
    @SuppressWarnings("static-access")
    public void tableEntrySelected(Event event) {
        TableViewer tViewer = (TableViewer) XWT.findElementByName(event.widget, "categoryTable");
        
        // Obtain the category in the line where the doubleClick happened
        Category selectedCat = (Category) tViewer.getElementAt(tViewer.getTable().getSelectionIndex());
        if (selectedCat == null) {
            return;
        }
        try {
            categoryService.refresh(selectedCat);
            Optional<Category> updatedCat = CategoryPartUtil.showWizard(event.display.getActiveShell(), Optional.of(selectedCat));
            if (updatedCat.isPresent()) {
                tViewer.refresh();
            }
        } catch (DatabaseException e) {
            LOG.error("Could not retrieve the category from database.", e);
            //TODO: Übersetzung ok?
            MessageDialog.openError(event.display.getActiveShell(), "Connection Error",
                    "Gewählte Kategorie kann nicht aus der Datenbank aktualisiert werden.");
        }
    }
    
    /**
     * Called whenever the add button is pressed.
     * @param event
     */
    public void buttonSelected(Event event) {
        category = null;
        Button b = (Button) event.widget;
        Optional<Category> newCat = CategoryPartUtil.showWizard(event.display.getActiveShell(), Optional.fromNullable(category));
        TableViewer tViewer = (TableViewer) XWT.findElementByName(b, "categoryTable");
        @SuppressWarnings("unchecked")
        Collection<Category> categories = (Collection<Category>) tViewer.getInput();
        if (newCat.isPresent()) {
            categories.add(newCat.get());
            tViewer.refresh();
        }
    }

}
