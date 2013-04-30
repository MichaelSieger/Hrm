package de.hswt.hrm.place.ui.part;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import javax.annotation.PostConstruct;

import org.eclipse.e4.xwt.IConstants;
import org.eclipse.e4.xwt.XWT;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.ui.swt.table.ColumnComparator;
import de.hswt.hrm.common.ui.swt.table.ColumnDescription;
import de.hswt.hrm.place.model.Place;
import de.hswt.hrm.place.service.PlaceService;
import de.hswt.hrm.place.ui.filter.PlaceFilter;
import de.hswt.hrm.place.ui.part.util.PlaceComparator;
import de.hswt.hrm.place.ui.part.util.PlacePartUtil;


public class PlacePart {
	
	private final static Logger LOG = LoggerFactory.getLogger(PlacePart.class);

	private TableViewer viewer;
    private PlaceFilter filter  = new PlaceFilter();
    private PlaceComparator p = new PlaceComparator();
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
	    
	    
	    // Create columns
	    List<ColumnDescription<Place>> columns = new ArrayList<>();
	    columns.add(new ColumnDescription<Place>("Place Name",
	            new ColumnLabelProvider() {
	        @Override
	        public String getText(Object element) {
	            Place p = (Place) element;
	            return p.getPlaceName();
	        }
	    } , 
	    new Comparator<Place>() {

	        @Override
	        public int compare(Place o1, Place o2) {
	            return o1.getPlaceName().compareTo(o2.getPlaceName());
	        }

	    }));
	    
	    /*
	     * private String placeName;
    private String postCode;
    private String city;
    private String street;
    private String streetNo;
    private String location;
    private String area;
	     */
	    
		PlacePartUtil.createColumns(parent, viewer, PlacePartUtil.getDefaultColumnHeaders(), p);
        viewer.setContentProvider(ArrayContentProvider.getInstance());
        viewer.addFilter(filter);
        viewer.setComparator(p);
	}

}