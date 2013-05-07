package de.hswt.hrm.place.ui.part;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.eclipse.e4.xwt.IConstants;
import org.eclipse.e4.xwt.XWT;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TableColumn;
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
	    
	    
	    // Create columns
	    List<ColumnDescription<Place>> columns = new ArrayList<>();
	    columns.add(new ColumnDescription<Place>(
	    		"Place Name",
	    		new ColumnLabelProvider() {
	    			@Override
	    			public String getText(Object element) {
	    				Place p = (Place) element;
	    				return p.getPlaceName();
	    			}
	    		}, 
	    		new Comparator<Place>() {

	    			@Override
	    			public int compare(Place o1, Place o2) {
	    				return o1.getPlaceName().compareTo(o2.getPlaceName());
	    			}

	    		}));
	    
	    columns.add(new ColumnDescription<>(
	    		"Postcode",
	    		new ColumnLabelProvider() {
	    			@Override
	    			public String getText(Object element) {
	    				Place p = (Place) element;
	    				return p.getPostCode();
	    			}
	    		},
	    		new Comparator<Place>() {
	    			@Override
	    			public int compare(Place o1, Place o2) {
	    				return o1.getPostCode().compareTo(o2.getPostCode());
	    			}
				}));
	    
	    columns.add(new ColumnDescription<>(
	    		"City",
	    		new ColumnLabelProvider() {
	    			@Override
	    			public String getText(Object element) {
	    				Place p = (Place) element;
	    				return p.getCity();
	    			}
	    		},
	    		new Comparator<Place>() {
	    			@Override
	    			public int compare(Place o1, Place o2) {
	    				return o1.getCity().compareTo(o2.getCity());
	    			}
				}));
	    
	    columns.add(new ColumnDescription<>(
	    		"Street",
	    		new ColumnLabelProvider() {
	    			@Override
	    			public String getText(Object element) {
	    				Place p = (Place) element;
	    				return p.getStreet();
	    			}
	    		},
	    		new Comparator<Place>() {
	    			@Override
	    			public int compare(Place o1, Place o2) {
	    				return o1.getStreet().compareTo(o2.getStreet());
	    			}
				}));
	    
	    columns.add(new ColumnDescription<>(
	    		"Street Number",
	    		new ColumnLabelProvider() {
	    			@Override
	    			public String getText(Object element) {
	    				Place p = (Place) element;
	    				return p.getStreetNo();
	    			}
	    		},
	    		new Comparator<Place>() {
	    			@Override
	    			public int compare(Place o1, Place o2) {
	    				return o1.getStreetNo().compareTo(o2.getStreetNo());
	    			}
				}));
	    
	    columns.add(new ColumnDescription<>(
	    		"Location",
	    		new ColumnLabelProvider() {
	    			@Override
	    			public String getText(Object element) {
	    				Place p = (Place) element;
	    				return p.getLocation();
	    			}
	    		},
	    		new Comparator<Place>() {
	    			@Override
	    			public int compare(Place o1, Place o2) {
	    				return o1.getLocation().compareTo(o2.getLocation());
	    			}
				}));
	    
	    columns.add(new ColumnDescription<>(
	    		"Area",
	    		new ColumnLabelProvider() {
	    			@Override
	    			public String getText(Object element) {
	    				Place p = (Place) element;
	    				return p.getArea();
	    			}
	    		},
	    		new Comparator<Place>() {
	    			@Override
	    			public int compare(Place o1, Place o2) {
	    				return o1.getArea().compareTo(o2.getArea());
	    			}
				}));
	    
	    Map<TableColumn, Comparator<Place>> comparators;
	    
	    TableViewerController<Place> filler = new TableViewerController<>(viewer);
	    comparators = filler.createColumns(columns);
	    filler.createColumnSelectionMenu();
	    
	    ColumnComparator<Place> comparator = new ColumnComparator<>(comparators);
	    filler.enableSorting(comparator);
	    
        viewer.setContentProvider(ArrayContentProvider.getInstance());
        viewer.addFilter(new PlaceFilter());
	}

}