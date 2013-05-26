package de.hswt.hrm.catalog.ui.part;

import java.net.URL;
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
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hswt.hrm.catalog.model.ICatalogItem;
import de.hswt.hrm.catalog.service.CatalogService;
import de.hswt.hrm.catalog.ui.event.CatalogEventHandler;
import de.hswt.hrm.catalog.ui.filter.CatalogFilter;
import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.locking.jdbc.ILockService;
import de.hswt.hrm.common.ui.swt.table.ColumnComparator;
import de.hswt.hrm.common.ui.swt.table.ColumnDescription;
import de.hswt.hrm.common.ui.swt.table.TableViewerController;
import de.hswt.hrm.common.ui.xwt.XwtHelper;

/**
 * This class will contain a simple TV to Display all available "Soll/Ist/Maßnahmen"
 * 
 */
public class CatalogPart {

    private static final Logger LOG = LoggerFactory.getLogger(CatalogPart.class);

    @Inject
    private CatalogService catalogService;

    @Inject
    @Optional
    private ILockService lockService;
    
    @Inject
    EPartService service;

    private TableViewer viewer;
    private Iterable<ICatalogItem> items;

    @PostConstruct
    public void postConstruct(Composite parent, IEclipseContext context) {

        URL url = CatalogPart.class.getClassLoader().getResource(
                "de/hswt/hrm/catalog/ui/xwt/CatalogView" + IConstants.XWT_EXTENSION_SUFFIX);

        try {
            // Create an instance of the event handler that is able to use DI
            CatalogEventHandler eventHandler = ContextInjectionFactory.make(
                    CatalogEventHandler.class, context);

            // TODO: partly move to extra plugin
            // Load XWT with injection ready event handler
            final Composite composite = XwtHelper.loadWithEventHandler(parent, url, eventHandler);
            LOG.debug("XWT load successfully.");

            viewer = (TableViewer) XWT.findElementByName(composite, "catalogTable");
            
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
            
            ((Button) XWT.findElementByName(composite, "match")).addListener(SWT.Selection, new Listener() {
                @Override
                public void handleEvent(Event event) {
                    service.findPart("Clients").setVisible(false);
                    service.findPart("Places").setVisible(false);
                    service.findPart("Plants").setVisible(false);
                    service.findPart("Scheme").setVisible(false);
                    service.findPart("Catalog").setVisible(false);
                    service.findPart("Category").setVisible(false);
                    service.findPart("Matched").setVisible(true);
                    service.showPart("Matched", PartState.VISIBLE);
                }
            });

            initializeTable(parent, viewer);
            refreshTable(parent);

        }
        catch (Exception e) {
            LOG.error("Could not load XWT file from resource", e);
        }

        if (catalogService == null) {
            LOG.error("catalogService not injected to CatalogPart.");
        }

        if (lockService != null) {
            LOG.debug("LockService injected to CatalogPart.");
        }

    }

    private void refreshTable(Composite parent) {

        try {
            items = catalogService.findAllCatalogItem();

            viewer.setInput(items);
        }
        catch (DatabaseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void initializeTable(Composite parent, TableViewer viewer2) {
        List<ColumnDescription<ICatalogItem>> columns = CatalogPartUtil.getColumns();

        // Create columns in tableviewer
        TableViewerController<ICatalogItem> filler = new TableViewerController<>(viewer);
        filler.createColumns(columns);

        // Enable column selection
        filler.createColumnSelectionMenu();

        // Enable sorting
        ColumnComparator<ICatalogItem> comparator = new ColumnComparator<>(columns);
        filler.enableSorting(comparator);

        // Add dataprovider that handles our collection
        viewer.setContentProvider(ArrayContentProvider.getInstance());

        // Enable filtering
        viewer.addFilter(new CatalogFilter());
    }

}