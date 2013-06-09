package de.hswt.hrm.catalog.ui.event;

import javax.inject.Inject;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.xwt.XWT;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.swt.widgets.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hswt.hrm.catalog.model.Catalog;
import de.hswt.hrm.catalog.model.ICatalogItem;
import de.hswt.hrm.catalog.model.Target;
import de.hswt.hrm.catalog.service.CatalogService;

public class CatalogMatchingEventHandler {

    private IEclipseContext context;
    private CatalogService catalogService;
    private ListViewer availableTarget;
    private ListViewer matchedTarget;

    private ListViewer availableCurrent;
    private ListViewer matchedCurrent;

    private ListViewer availableActivity;
    private ListViewer matchedActivity;

    private final static Logger LOG = LoggerFactory.getLogger(CatalogMatchingEventHandler.class);

    @Inject
    public CatalogMatchingEventHandler(IEclipseContext context, CatalogService catalogService) {
        if (context == null) {
            LOG.error("EclipseContext was not injected to PlaceEventHandler.");
        }

        if (catalogService == null) {
            LOG.error("PlaceService was not injected to PlaceEventHandler.");
        }

        this.context = context;
        this.catalogService = catalogService;

    }

    /**
     * This Event is called whenever an entry is doubleClicked in the availableTarget ListViewer
     * 
     * @param event
     */
    public void availableTargetSelected(Event event) {

        // Catalog c = (Catalog) catalogs.getElementAt(catalogs.getList().getSelectionIndex());
        //
        Target t = (Target) moveEntry(availableTarget, matchedTarget);
        // try {
        //
        // if (t == null) {
        // System.out.println("t is null");
        // return;
        // }
        // else
        // System.out.println(t.getName());
        //
        // catalogService.addToCatalog(c, t);
        // }
        // catch (SaveException e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }

        if (matchedTarget.getList().getItemCount() > 0) {
            matchedTarget.getList().setEnabled(true);
            availableCurrent.getList().setEnabled(true);
        }

    }

    public void matchedTargetSelected(Event event) {

        moveEntry(matchedTarget, availableTarget);

        if (matchedTarget.getList().getItemCount() == 0) {
            matchedTarget.getList().setEnabled(false);
            availableCurrent.getList().setEnabled(false);
        }

    }

    public void availableCurrentSelected(Event event) {

        moveEntry(availableCurrent, matchedCurrent);

        if (matchedCurrent.getList().getItemCount() > 0) {

            matchedCurrent.getList().setEnabled(true);
            availableActivity.getList().setEnabled(true);

        }

    }

    public void matchedCurrentSelected(Event event) {

        moveEntry(matchedCurrent, availableCurrent);

        if (matchedCurrent.getList().getItemCount() == 0) {
            matchedCurrent.getList().setEnabled(false);
            availableActivity.getList().setEnabled(false);
        }

    }

    public void availableActivitySelected(Event event) {

        if (availableActivity.getList().getItemCount() > 0) {
            matchedActivity.getList().setEnabled(true);
        }

    }

    public void matchedActivitySelected(Event event) {

        moveEntry(matchedActivity, availableActivity);

        if (matchedActivity.getList().getItemCount() == 0) {
            matchedActivity.getList().setEnabled(false);

        }

    }

    /**
     * This event occurs whenever an catalog entry is selected
     * 
     * @param event
     */
    public void onSelection(Event event) {

        ListViewer catalogs = (ListViewer) XWT.findElementByName(event.widget, "catalogs");
        Catalog c = (Catalog) catalogs.getElementAt(catalogs.getList().getSelectionIndex());
        if (c.getTargets().isEmpty()) {
            System.out.println("empty Targets, using defaults");
        }
        else
            System.out.println("found matched target");

        ListViewer alv = (ListViewer) XWT.findElementByName(event.widget, "availableTarget");
        alv.getList().setEnabled(true);

    }

    private ICatalogItem moveEntry(ListViewer source, ListViewer target) {

        ICatalogItem item = (ICatalogItem) source
                .getElementAt(source.getList().getSelectionIndex());
        if (item == null) {
            return null;
        }

        target.add(item);
        source.remove(item);

        return item;

    }

    public void setContext(IEclipseContext context) {
        this.context = context;
    }

    public void setCatalogService(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    public void setAvTarget(ListViewer availableTarget) {
        this.availableTarget = availableTarget;
    }

    public void setMaTarget(ListViewer marchedTarget) {
        this.matchedTarget = marchedTarget;
    }

    public void setAvCurrent(ListViewer availableCurrent) {
        this.availableCurrent = availableCurrent;
    }

    public void setMaCurrent(ListViewer matchedCurrent) {
        this.matchedCurrent = matchedCurrent;
    }

    public void setAvActivity(ListViewer availableActivity) {
        this.availableActivity = availableActivity;
    }

    public void setMaActivity(ListViewer matchedActivity) {
        this.matchedActivity = matchedActivity;
    }

}
