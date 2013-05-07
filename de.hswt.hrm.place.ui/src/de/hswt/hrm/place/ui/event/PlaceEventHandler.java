package de.hswt.hrm.place.ui.event;

import java.util.Collection;

import org.eclipse.e4.xwt.XWT;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Text;

import com.google.common.base.Optional;

import de.hswt.hrm.place.model.Place;
import de.hswt.hrm.place.ui.filter.PlaceFilter;
import de.hswt.hrm.place.ui.part.PlacePartUtil;

public class PlaceEventHandler {
//    private final static Logger LOG = LoggerFactory.getLogger(PlaceEventHandler.class);
    private static final String DEFAULT_SEARCH_STRING = "Suche";
    private static final String EMPTY = "";
	 
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
        TableViewer tf = (TableViewer) XWT.findElementByName(text, "contactTable");
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
    
	public void onFocusOut(Event event) {
		Text text = (Text) event.widget;
		text.setText("Suche (Location,Area,City)");
		TableViewer tf = (TableViewer) XWT.findElementByName(text, "placeTable");
		tf.refresh();
	}

	@SuppressWarnings("unchecked")
    public void buttonSelected(Event event) {
        Button b = (Button) event.widget;
        Optional<Place> newPlace = PlacePartUtil.showWizard(event.display.getActiveShell(),
                Optional.<Place>absent());

        if (newPlace.isPresent()) {
            TableViewer tv = (TableViewer) XWT.findElementByName(b, "placeTable");
            Collection<Place> places = (Collection<Place>) tv.getInput();
            places.add(newPlace.get());
            tv.refresh();
        }
	}
	
	public void tableEntrySelected(Event event) {

        TableViewer tv = (TableViewer) XWT.findElementByName(event.widget, "placeTable");

        // obtain the place in the column where the doubleClick happend
        Place selectedPlace = (Place) tv.getElementAt(tv.getTable().getSelectionIndex());

        // Refresh the selected place with values from the database
//        try {
            
            // TODO: check how to handle that correctly (instance!!!)
//            selectedPlace = PlaceService.findById(selectedPlace.getId());
            
            Optional<Place> updatedPlace = PlacePartUtil.showWizard(
                    event.display.getActiveShell(), Optional.of(selectedPlace));

            if (updatedPlace.isPresent()) {
                tv.refresh();
            }
//        }
//        catch (DatabaseException e) {
//            LOG.error("Could not retrieve the place from database.", e);
//            
//            // TODO: übersetzen
//            MessageDialog.openError(event.display.getActiveShell(), "Connection Error",
//                    "Could not update selected place from database.");
//        }
    }

	public void onKeyUp(Event event) {
		Text searchText = (Text) event.widget;
		TableViewer tf = (TableViewer) XWT.findElementByName(searchText, "placeTable");
		PlaceFilter f = (PlaceFilter) tf.getFilters()[0];
		f.setSearchString(searchText.getText());
		tf.refresh();
	}

}
