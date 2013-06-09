package de.hswt.hrm.catalog.ui.part;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.xwt.IConstants;
import org.eclipse.e4.xwt.XWT;
import org.eclipse.e4.xwt.forms.XWTForms;
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

public class CatalogMatchingPart {

    @Inject
    CatalogService catalogService;
    @Inject
    EPartService service;

    ListViewer targets;
    ListViewer currents;
    ListViewer activities;
    ListViewer catalogs;
    ListViewer matchedTargets;
    ListViewer matchedCurrents;
    ListViewer matchedActivities;

    @PostConstruct
    public void postConstruct(Composite parent, IEclipseContext context) {

        URL url = CatalogPart.class.getClassLoader().getResource(
                "de/hswt/hrm/catalog/ui/xwt/CatalogMatchingView" + IConstants.XWT_EXTENSION_SUFFIX);

        try {
            final Composite composite = (Composite) XWTForms.load(parent, url);

            // obtain all ListViewer
            targets = (ListViewer) XWT.findElementByName(composite, "availableTarget");
            currents = (ListViewer) XWT.findElementByName(composite, "availableCurrent");
            activities = (ListViewer) XWT.findElementByName(composite, "availableActivity");
            matchedActivities = (ListViewer) XWT.findElementByName(composite, "matchedActivity");
            matchedTargets = (ListViewer) XWT.findElementByName(composite, "matchedTarget");
            matchedCurrents = (ListViewer) XWT.findElementByName(composite, "matchedCurrent");
            catalogs = (ListViewer) XWT.findElementByName(composite, "catalogs");

            initalize(activities);
            initalize(targets);
            initalize(currents);
            initalize(matchedTargets);
            initalize(matchedActivities);
            initalize(matchedCurrents);
            initializeCatalogs(catalogs);

            Collection<Catalog> catalogsFromDB = catalogService.findAllCatalog();

            Collection<ICatalogItem> items = catalogService.findAllCatalogItem();

            fillAvailableViewer(items);

            // catalogs.setInput(catalogsFromDB);
            /*
             * Dummy Data until DB is incomplete
             */

            ArrayList<Catalog> dummy = new ArrayList<>();
            dummy.add(new Catalog("Catalog 1"));
            dummy.add(new Catalog("Catalog 2"));
            catalogs.setInput(dummy);

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

        targets.setInput(t);
        currents.setInput(c);
        activities.setInput(a);

        targets.getList().setEnabled(false);
        currents.getList().setEnabled(false);
        activities.getList().setEnabled(false);

        matchedActivities.getList().setEnabled(false);
        matchedCurrents.getList().setEnabled(false);
        matchedTargets.getList().setEnabled(false);

    }

}
