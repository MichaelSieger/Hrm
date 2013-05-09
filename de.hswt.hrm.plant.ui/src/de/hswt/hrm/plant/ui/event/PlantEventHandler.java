package de.hswt.hrm.plant.ui.event;

import java.util.Collection;

import org.eclipse.e4.xwt.XWT;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Text;

import com.google.common.base.Optional;

import de.hswt.hrm.plant.model.Plant;

public class PlantEventHandler {

    private static final String DEFAULT_SEARCH_STRING = "Suche";
    private static final String EMPTY = "";
    private Plant plant;

    /**
     * This event is called whenever the Search Text Field is leaved. If the the field is blank, the
     * value of the Field {@link #DEFAULT_SEARCH_STRING} is inserted.
     * 
     * @param event
     *            Event which occured in SWT
     */
    public void leaveText(Event event) {

        Text text = (Text) event.widget;
        if (text.getText().isEmpty()) {
            text.setText(DEFAULT_SEARCH_STRING);
        }
        TableViewer tf = (TableViewer) XWT.findElementByName(text, "PlantTable");
        tf.refresh();

    }

    /**
     * This Event is called whenever the add buttion is pressed.
     * 
     * @param event
     */
    @SuppressWarnings("unchecked")
    public void buttonSelected(Event event) {
        plant = null;
        Button b = (Button) event.widget;
        Optional<Plant> newPlant = PlantPartUtil.showWizard(event.display.getActiveShell(),
                Optional.fromNullable(Plant));

        TableViewer tv = (TableViewer) XWT.findElementByName(b, "PlantTable");

        Collection<Plant> contacs = (Collection<Plant>) tv.getInput();
        if (newPlant.isPresent()) {
            contacs.add(newPlant.get());
            tv.refresh();
        }
    }

    /**
     * This event is called whenever a Text is entered into the Search textField
     * 
     * @param event
     */
    public void onKeyUp(Event event) {

        Text searchText = (Text) event.widget;
        TableViewer tv = (TableViewer) XWT.findElementByName(searchText, "PlantTable");
        PlantFilter filter = (PlantFilter) tv.getFilters()[0];
        filter.setSearchString(searchText.getText());
        tv.refresh();

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

    /**
     * This method is called whenever a doubleClick onto the Tableviewer occurs. It obtains the
     * Plant from the selected column of the TableViewer. The Plant is passed to the
     * PlantWizard. When the Wizard has finished, the Plant will be updated in the Database
     * 
     * @param event
     *            Event which occured within SWT
     */
    public void tableEntrySelected(Event event) {

        TableViewer tv = (TableViewer) XWT.findElementByName(event.widget, "PlantTable");

        // obtain the Plant in the column where the doubleClick happend
        Plant selectedPlant = (Plant) tv.getElementAt(tv.getTable().getSelectionIndex());

        Optional<Plant> updatePlant = PlantPartUtil.showWizard(
                event.display.getActiveShell(), Optional.fromNullable(selectedPlant));

        if (updatePlant.isPresent()) {
            tv.refresh();
        }
    }

}
