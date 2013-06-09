package de.hswt.hrm.catalog.ui.event;

import javax.inject.Inject;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.xwt.XWT;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hswt.hrm.catalog.model.Catalog;
import de.hswt.hrm.catalog.model.ICatalogItem;
import de.hswt.hrm.catalog.model.Target;
import de.hswt.hrm.catalog.service.CatalogService;
import de.hswt.hrm.catalog.ui.filter.CatalogTextFilter;

public class CatalogMatchingEventHandler {

    private IEclipseContext context;
    private CatalogService catalogService;

    private ListViewer availableTarget;
    private ListViewer matchedTarget;
    private ListViewer availableCurrent;
    private ListViewer matchedCurrent;
    private ListViewer availableActivity;
    private ListViewer matchedActivity;

    private static final Logger LOG = LoggerFactory.getLogger(CatalogMatchingEventHandler.class);
    private static final String DEFAULT_SEARCH_STRING = "Search";

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

        // ListViewer catalogs = (ListViewer) XWT.findElementByName(event.widget, "catalogs");
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

        enableList(matchedTarget, availableCurrent);

    }

    public void matchedTargetSelected(Event event) {

        moveEntry(matchedTarget, availableTarget);
        disableLists(matchedTarget, matchedCurrent);

    }

    public void availableCurrentSelected(Event event) {

        moveEntry(availableCurrent, matchedCurrent);
        enableList(matchedCurrent, availableActivity);

    }

    public void matchedCurrentSelected(Event event) {

        moveEntry(matchedCurrent, availableCurrent);
        disableLists(matchedCurrent, availableActivity);

    }

    public void availableActivitySelected(Event event) {

        moveEntry(availableActivity, matchedActivity);
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

    private void enableList(ListViewer previous, ListViewer next) {

        if (previous.getList().getItemCount() > 0) {
            previous.getList().setEnabled(true);
            next.getList().setEnabled(true);
        }
    }

    private void disableLists(ListViewer first, ListViewer second) {

        if (first.getList().getItemCount() == 0) {
            first.getList().setEnabled(false);
            second.getList().setEnabled(false);
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

        availableTarget.getList().setEnabled(true);

    }

    public void enterText(Event event) {
        Text text = (Text) event.widget;
        if (text.getText().equals(DEFAULT_SEARCH_STRING)) {
            text.setText("");
        }
    }

    public void leaveText(Event event) {

        Text text = (Text) event.widget;
        if (text.getText().isEmpty()) {
            text.setText(DEFAULT_SEARCH_STRING);
        }
        availableActivity.refresh();
        availableCurrent.refresh();
        availableTarget.refresh();
        matchedActivity.refresh();
        matchedCurrent.refresh();
        matchedTarget.refresh();

    }

    public void onKeyUp(Event event) {
        Text searchText = (Text) event.widget;
        CatalogTextFilter ctf = (CatalogTextFilter) availableActivity.getFilters()[1];
        ctf.setSearchString(searchText.getText());
        availableActivity.refresh();
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
