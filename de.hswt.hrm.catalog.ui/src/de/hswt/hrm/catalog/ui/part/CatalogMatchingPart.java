package de.hswt.hrm.catalog.ui.part;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.EPartService.PartState;
import org.eclipse.e4.xwt.IConstants;
import org.eclipse.e4.xwt.XWT;
import org.eclipse.e4.xwt.forms.XWTForms;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

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

            ((Button) XWT.findElementByName(composite, "back2Main")).addListener(SWT.Selection,
                    new Listener() {
                        @Override
                        public void handleEvent(Event event) {
                            service.findPart("Clients").setVisible(false);
                            service.findPart("Places").setVisible(false);
                            service.findPart("Plants").setVisible(false);
                            service.findPart("Scheme").setVisible(false);
                            service.findPart("Catalog").setVisible(false);
                            service.findPart("Category").setVisible(false);
                            service.findPart("Matched").setVisible(false);
                            service.findPart("Main").setVisible(true);
                            service.showPart("Main", PartState.VISIBLE);
                        }
                    });

            targets = (ListViewer) XWT.findElementByName(composite, "availableTarget");
            currents = (ListViewer) XWT.findElementByName(composite, "availableCurrent");
            activities = (ListViewer) XWT.findElementByName(composite, "availableActivity");
            matchedActivities = (ListViewer) XWT.findElementByName(composite, "matchedActivity");
            matchedTargets = (ListViewer) XWT.findElementByName(composite, "matchedTarget");
            matchedCurrents = (ListViewer) XWT.findElementByName(composite, "matchedCurrent");
            catalogs = (ListViewer) XWT.findElementByName(composite, "catalogs");

            catalogs.setContentProvider(ArrayContentProvider.getInstance());
            catalogs.setLabelProvider(new LabelProvider() {
                @Override
                public String getText(Object element) {
                    Catalog c = (Catalog) element;
                    return c.getName();
                }
            });

            createLabelProvider(activities);
            createLabelProvider(matchedTargets);
            createLabelProvider(matchedActivities);
            createLabelProvider(matchedCurrents);

            Collection<Catalog> catalogsFromDB = catalogService.findAllCatalog();
            Collection<ICatalogItem> items = catalogService.findAllCatalogItem();
            fillAvailableViewer(items);

            catalogs.setInput(catalogsFromDB);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createLabelProvider(ListViewer lv) {

        lv.setContentProvider(ArrayContentProvider.getInstance());

        lv.setLabelProvider(new LabelProvider() {
            @Override
            public String getText(Object element) {
                ICatalogItem c = (ICatalogItem) element;
                return c.getName();
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

    }

}
