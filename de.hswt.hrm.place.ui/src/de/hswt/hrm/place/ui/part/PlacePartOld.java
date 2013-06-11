package de.hswt.hrm.place.ui.part;

import java.net.URL;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.EPartService.PartState;
import org.eclipse.e4.xwt.IConstants;
import org.eclipse.e4.xwt.XWT;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.locking.jdbc.ILockService;
import de.hswt.hrm.common.ui.swt.table.ColumnComparator;
import de.hswt.hrm.common.ui.swt.table.ColumnDescription;
import de.hswt.hrm.common.ui.swt.table.TableViewerController;
import de.hswt.hrm.common.ui.xwt.XwtHelper;
import de.hswt.hrm.place.model.Place;
import de.hswt.hrm.place.service.PlaceService;
import de.hswt.hrm.place.ui.event.PlaceEventHandler;
import de.hswt.hrm.place.ui.filter.PlaceFilter;

public class PlacePartOld {
    private final static Logger LOG = LoggerFactory.getLogger(PlacePartOld.class);

    @Inject
    private PlaceService placeService;
    
    @Inject
    EPartService service;

    @Inject
    @Optional
    private ILockService lockService;
    
    private TableViewer viewer;
    private Collection<Place> places = null;

    @PostConstruct
    public void postConstruct(Composite parent, IEclipseContext context) {
        URL url = PlacePartOld.class.getClassLoader().getResource(
                "de/hswt/hrm/place/ui/xwt/PlaceView" + IConstants.XWT_EXTENSION_SUFFIX);

        try {
            // Create an instance of the event handler that is able to use DI
            PlaceEventHandler eventHandler = ContextInjectionFactory.make(PlaceEventHandler.class,
                    context);

            // Load XWT with injection ready event handler
            final Composite composite = XwtHelper.loadWithEventHandler(parent, url, eventHandler);
            LOG.debug("XWT load successfully.");

            viewer = (TableViewer) XWT.findElementByName(composite, "placeTable");
            initializeTable(parent, viewer);
            refreshTable(parent);
            
            ((Button) XWT.findElementByName(composite, "back2Main")).addListener(SWT.Selection, new Listener() {
 				@Override
 				public void handleEvent(Event event) {
 					service.findPart("Clients").setVisible(false);
 					service.findPart("Places").setVisible(false);
 					service.findPart("Plants").setVisible(false);
 					service.findPart("Scheme").setVisible(false);
 					service.findPart("Catalog").setVisible(false);
 					service.findPart("Category").setVisible(false);
 					service.findPart("Main").setVisible(true);
 					service.showPart("Main", PartState.VISIBLE);
 				}
 			});

        }
        catch (Exception e) {
            LOG.error("Could not load XWT file from resource", e);
        }

        if (placeService == null) {
            LOG.error("PlaceService not injected to PlacePart.");
        }

        if (lockService != null) {
            LOG.debug("LockService injected to PlacePart.");
        }
    }

    private void refreshTable(Composite parent) {
        try {
            places = placeService.findAll();
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

        // Enable filtering
        viewer.addFilter(new PlaceFilter());
    }

}