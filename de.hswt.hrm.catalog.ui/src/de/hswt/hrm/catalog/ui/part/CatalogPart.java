package de.hswt.hrm.catalog.ui.part;

import java.net.URL;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.xwt.IConstants;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Composite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hswt.hrm.catalog.model.ICatalogItem;
import de.hswt.hrm.catalog.service.CatalogService;
import de.hswt.hrm.common.locking.jdbc.ILockService;

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

    private TableViewer viewer;
    private Collection<ICatalogItem> items;

    @PostConstruct
    public void postConstruct(Composite parent) {

        URL url = CatalogPart.class.getClassLoader().getResource(
                "de/hswt/hrm/place/ui/xwt/PlaceView" + IConstants.XWT_EXTENSION_SUFFIX);

        // try {
        // // Create an instance of the event handler that is able to use DI
        // // Catalog eventHandler = ContextInjectionFactory.make(PlaceEventHandler.class,
        // // context);
        //
        // // TODO: partly move to extra plugin
        // // Load XWT with injection ready event handler
        // final Composite composite = XwtHelper.loadWithEventHandler(parent, url, eventHandler);
        // LOG.debug("XWT load successfully.");
        //
        // viewer = (TableViewer) XWT.findElementByName(composite, "placeTable");
        // initializeTable(parent, viewer);
        // refreshTable(parent);

        // }
        // catch (Exception e) {
        // LOG.error("Could not load XWT file from resource", e);
        // }

    }

}