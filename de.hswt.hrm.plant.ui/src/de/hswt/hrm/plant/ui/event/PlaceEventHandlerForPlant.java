package de.hswt.hrm.plant.ui.event;

import java.util.Collection;

import javax.inject.Inject;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.xwt.XWT;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;

import de.hswt.hrm.place.model.Place;
import de.hswt.hrm.place.ui.filter.PlaceFilter;
import de.hswt.hrm.place.ui.part.PlacePartUtil;

public class PlaceEventHandlerForPlant {
    private final static Logger LOG = LoggerFactory.getLogger(PlaceEventHandlerForPlant.class);
    private static final String DEFAULT_SEARCH_STRING = "Suche";
    private static final String EMPTY = "";
   @Inject
    private IEclipseContext context;
    /**
     * This event is called whenever the Search Text Field is leaved. If the the field is blank, the
     * value of the Field {@link #DEFAULT_SEARCH_STRING} is inserted.
     * 
     * @param event
     *            Event which occured in SWT
     *            
     *            */
    
    @Inject
    public PlaceEventHandlerForPlant(IEclipseContext context) {
        if (context == null) {
            LOG.error("EclipseContext was not injected to PlantEventHandler.");
        }        
        this.context = context;
    }
    
    public void leaveText(Event event) {

        Text text = (Text) event.widget;
        if (text.getText().isEmpty()) {
            text.setText(DEFAULT_SEARCH_STRING);
        }
        TableViewer tf = (TableViewer) XWT.findElementByName(text, "placeTable");
        tf.refresh();

    }
    
    public void tableEntrySelected(Event event) {}
    
    public void buttonSelected(Event event) {
        Button b = (Button) event.widget;
        Optional<Place> newPlace = PlacePartUtil.showWizard(
                context, 
                event.display.getActiveShell(),
                Optional.<Place>absent());

        if (newPlace.isPresent()) {
            TableViewer tv = (TableViewer) XWT.findElementByName(b, "placeTable");
            Collection<Place> places = (Collection<Place>) tv.getInput();
            places.add(newPlace.get());
            tv.refresh();
        }
    	
    	
    	
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

	public void onKeyUp(Event event) {
		Text searchText = (Text) event.widget;
		TableViewer tf = (TableViewer) XWT.findElementByName(searchText, "placeTable");
		PlaceFilter f = (PlaceFilter) tf.getFilters()[0];
		f.setSearchString(searchText.getText());
		tf.refresh();
	}
}
