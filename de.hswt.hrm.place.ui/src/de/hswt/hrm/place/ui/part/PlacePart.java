package de.hswt.hrm.place.ui.part;

import java.net.URL;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;

import org.eclipse.e4.xwt.IConstants;
import org.eclipse.e4.xwt.XWT;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Composite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.ui.swt.table.ColumnComparator;
import de.hswt.hrm.common.ui.swt.table.ColumnDescription;
import de.hswt.hrm.common.ui.swt.table.TableViewerController;
import de.hswt.hrm.place.model.Place;
import de.hswt.hrm.place.service.PlaceService;
import de.hswt.hrm.place.ui.filter.PlaceFilter;

public class PlacePart {
	private final static Logger LOG = LoggerFactory.getLogger(PlacePart.class);

	private TableViewer viewer;
	private Collection<Place> places = null;

	@PostConstruct
	public void postConstruct(Composite parent) {
		URL url = PlacePart.class.getClassLoader().getResource(
				"de/hswt/hrm/place/ui/xwt/PlaceView" + IConstants.XWT_EXTENSION_SUFFIX);
		
		try {
			final Composite composite = (Composite) XWT.load(parent, url);
			viewer = (TableViewer) XWT.findElementByName(composite, "placeTable");
			LOG.debug("XWT load, viewer: " + viewer);
			initializeTable(parent, viewer);
			refreshTable(parent);
			
		}
		catch (Exception e) {
			LOG.error("Could not load XWT file from resource", e);
		}
	}
	
	private void refreshTable(Composite parent) {
		try {
			places = PlaceService.findAll();
			viewer.setInput(places);
		} 
		catch (DatabaseException e) {
			LOG.error("Unable to retrieve list of places.", e);
			
			// TODO: übersetzen
			MessageDialog.openError(parent.getShell(), "Connection Error",
					"Could not load places from Database.");
		}
	}
	
	private void initializeTable(Composite parent, TableViewer viewer) {
	    List<ColumnDescription<Place>> columns = PlacePartUtil.getColumns();

	    // Create columns in tableviewer
	    TableViewerController<Place> filler = new TableViewerController<>(viewer);
	    filler.createColumns(columns);
	    
	    // Enable column selection
	    filler.createColumnSelectionMenu();
	    
	    // Enable sorting
	    ColumnComparator<Place> comparator = new ColumnComparator<>(columns);
	    filler.enableSorting(comparator);
	    
	    // Add dataprovider that handles our collection
        viewer.setContentProvider(ArrayContentProvider.getInstance());
        
        //Enable filtering
        viewer.addFilter(new PlaceFilter());
	}

}