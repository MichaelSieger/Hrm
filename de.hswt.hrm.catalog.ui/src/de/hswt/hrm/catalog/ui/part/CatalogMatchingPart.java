package de.hswt.hrm.catalog.ui.part;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.xwt.IConstants;
import org.eclipse.e4.xwt.XWT;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.widgets.Composite;

import de.hswt.hrm.catalog.model.Activity;
import de.hswt.hrm.catalog.model.Catalog;
import de.hswt.hrm.catalog.model.Current;
import de.hswt.hrm.catalog.model.ICatalogItem;
import de.hswt.hrm.catalog.model.Target;
import de.hswt.hrm.catalog.service.CatalogService;
import de.hswt.hrm.catalog.ui.event.CatalogMatchingEventHandler;
import de.hswt.hrm.catalog.ui.filter.CatalogTextFilter;
import de.hswt.hrm.common.ui.xwt.XwtHelper;

public class CatalogMatchingPart {

    @Inject
    CatalogService catalogService;

    ListViewer catalogs;
    ListViewer availableTargets;
    ListViewer availablecurrents;
    ListViewer availableactivities;
    ListViewer matchedTargets;
    ListViewer matchedCurrents;
    ListViewer matchedActivities;

    @PostConstruct
    public void postConstruct(Composite parent, IEclipseContext context) {

        URL url = CatalogPartOld.class.getClassLoader().getResource(
                "de/hswt/hrm/catalog/ui/xwt/CatalogMatchingView" + IConstants.XWT_EXTENSION_SUFFIX);

        try {

            CatalogMatchingEventHandler cmeh = ContextInjectionFactory.make(
                    CatalogMatchingEventHandler.class, context);

            final Composite composite = (Composite) XwtHelper.loadFormWithEventHandler(parent, url,
                    cmeh);

            // obtain all ListViewer
            availableTargets = (ListViewer) XWT.findElementByName(composite, "availableTarget");
            availablecurrents = (ListViewer) XWT.findElementByName(composite, "availableCurrent");
            availableactivities = (ListViewer) XWT
                    .findElementByName(composite, "availableActivity");
            matchedActivities = (ListViewer) XWT.findElementByName(composite, "matchedActivity");
            matchedTargets = (ListViewer) XWT.findElementByName(composite, "matchedTarget");
            matchedCurrents = (ListViewer) XWT.findElementByName(composite, "matchedCurrent");
            catalogs = (ListViewer) XWT.findElementByName(composite, "catalogs");

            // Pass them to the event Handler
            cmeh.setAvActivity(availableactivities);
            cmeh.setAvCurrent(availablecurrents);
            cmeh.setAvTarget(availableTargets);
            cmeh.setMaActivity(matchedActivities);
            cmeh.setMaCurrent(matchedCurrents);
            cmeh.setMaTarget(matchedTargets);

            // Fill them with data
            initalize(availableactivities);
            initalize(availableTargets);
            initalize(availablecurrents);
            initalize(matchedTargets);
            initalize(matchedActivities);
            initalize(matchedCurrents);
            initializeCatalogs(catalogs);

            Collection<Catalog> catalogsFromDB = catalogService.findAllCatalog();

            Collection<ICatalogItem> items = catalogService.findAllCatalogItem();

            fillAvailableViewer(items);

            catalogs.setInput(catalogsFromDB);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initializeCatalogs(ListViewer catalogs2) {
        catalogs.setContentProvider(ArrayContentProvider.getInstance());
        catalogs.setLabelProvider(new LabelProvider() {
            @Override
            public String getText(Object element) {
                Catalog c = (Catalog) element;
                return c.getName();
            }
        });
    }

    /**
     * This method creates the labelprovider,contentprovider and comperator
     * 
     * @param lv
     */
    private void initalize(ListViewer lv) {

        lv.setContentProvider(ArrayContentProvider.getInstance());

        lv.setLabelProvider(new LabelProvider() {
            @Override
            public String getText(Object element) {
                ICatalogItem c = (ICatalogItem) element;
                return c.getName();
            }
        });

        lv.setComparator(new ViewerComparator() {

            @Override
            public int compare(Viewer viewer, Object e1, Object e2) {

                ICatalogItem ic1 = (ICatalogItem) e1;
                ICatalogItem ic2 = (ICatalogItem) e2;
                return ic1.getName().compareToIgnoreCase(ic2.getName());

            }

        });
    }

    private void fillAvailableViewer(Collection<ICatalogItem> items) {

        Collection<Activity> a = new ArrayList<>();
        Collection<Current> c = new ArrayList<>();
        Collection<Target> t = new ArrayList<>();

        for (ICatalogItem i : items) {
            if (i instanceof Activity) {
                a.add((Activity) i);
            }
            else if (i instanceof Current) {
                c.add((Current) i);
            }
            else {
                t.add((Target) i);
            }
        }

        availableTargets.setInput(t);
        availablecurrents.setInput(c);
        availableactivities.setInput(a);

        availableactivities.addFilter(new CatalogTextFilter());
        availablecurrents.addFilter(new CatalogTextFilter());
        availableTargets.addFilter(new CatalogTextFilter());

        matchedTargets.addFilter(new CatalogTextFilter());
        matchedActivities.addFilter(new CatalogTextFilter());
        matchedCurrents.addFilter(new CatalogTextFilter());

        availableTargets.getList().setEnabled(false);
        availablecurrents.getList().setEnabled(false);
        availableactivities.getList().setEnabled(false);

        matchedActivities.getList().setEnabled(false);
        matchedCurrents.getList().setEnabled(false);
        matchedTargets.getList().setEnabled(false);

    }

    public ListViewer getTargets() {
        return availableTargets;
    }

    public ListViewer getCurrents() {
        return availablecurrents;
    }

    public ListViewer getActivities() {
        return availableactivities;
    }

    public ListViewer getCatalogs() {
        return catalogs;
    }

    public ListViewer getMatchedTargets() {
        return matchedTargets;
    }

    public ListViewer getMatchedCurrents() {
        return matchedCurrents;
    }

    public ListViewer getMatchedActivities() {
        return matchedActivities;
    }

}
