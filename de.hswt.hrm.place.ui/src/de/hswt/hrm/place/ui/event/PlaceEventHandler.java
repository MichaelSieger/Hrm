package de.hswt.hrm.place.ui.event;

import java.util.Collection;

import javax.inject.Inject;

import org.eclipse.e4.core.contexts.IEclipseContext;
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
import de.hswt.hrm.place.model.Place;
import de.hswt.hrm.place.service.PlaceService;
import de.hswt.hrm.place.ui.filter.PlaceFilter;
import de.hswt.hrm.place.ui.part.PlacePartUtil;

public class PlaceEventHandler {
    private final static Logger LOG = LoggerFactory.getLogger(PlaceEventHandler.class);
    private static final String DEFAULT_SEARCH_STRING = "Suche";
    private static final String EMPTY = "";
    private final IEclipseContext context;
    private final PlaceService placeService;
	 
    @Inject
    public PlaceEventHandler(IEclipseContext context, PlaceService placeService) {
        if (context == null) {
            LOG.error("EclipseContext was not injected to PlaceEventHandler.");
        }
        
        if (placeService == null) {
            LOG.error("PlaceService was not injected to PlaceEventHandler.");
        }
        
        this.context = context;
        this.placeService = placeService;
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
	
	public void tableEntrySelected(Event event) {

        TableViewer tv = (TableViewer) XWT.findElementByName(event.widget, "placeTable");

        // obtain the place in the column where the doubleClick happend
        Place selectedPlace = (Place) tv.getElementAt(tv.getTable().getSelectionIndex());

        // Refresh the selected place with values from the database
        try {
            placeService.refresh(selectedPlace);
            Optional<Place> updatedPlace = PlacePartUtil.showWizard(
                    context,
                    event.display.getActiveShell(),
                    Optional.of(selectedPlace));

            if (updatedPlace.isPresent()) {
                tv.refresh();
            }
        }
        catch (DatabaseException e) {
            LOG.error("Could not retrieve the place from database.", e);

            // TODO: übersetzen
            MessageDialog.openError(event.display.getActiveShell(), "Connection Error",
                    "Could not update selected place from database.");
        }
    }

}
