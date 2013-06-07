package de.hswt.hrm.main.ui.part;

import java.net.URL;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.xwt.DefaultLoadingContext;
import org.eclipse.e4.xwt.IConstants;
import org.eclipse.e4.xwt.XWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import de.hswt.hrm.main.ui.MPartSwitcher;

public class MainPart {

    @Inject
    EPartService service;

    private Button toContacts;
    private Button toPlaces;
    private Button toPlants;
    private Button toCatalog;
    private Button toCategory;
    private Button toInspection;
    private Button toOverall;

    @PostConstruct
    public void postConstruct(Composite parent) {
        URL url = MainPart.class.getClassLoader().getResource(
                "de/hswt/hrm/main/xwt/MainView" + IConstants.XWT_EXTENSION_SUFFIX);
        XWT.setLoadingContext(new DefaultLoadingContext(this.getClass().getClassLoader()));

        try {
            Composite comp = (Composite) XWT.load(parent, url);
            toContacts = (Button) XWT.findElementByName(comp, "toContacts");
            toPlaces = (Button) XWT.findElementByName(comp, "toPlaces");
            toPlants = (Button) XWT.findElementByName(comp, "toPlants");
            toCatalog = (Button) XWT.findElementByName(comp, "toCatalog");
            toCategory = (Button) XWT.findElementByName(comp, "toCategory");
            toOverall = (Button) XWT.findElementByName(comp, "toOverall");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        setNavigationButton();
    }

    private void setNavigationButton() {
        toContacts.addListener(SWT.Selection, new Listener() {
            @Override
            public void handleEvent(Event event) {
                MPartSwitcher.setPartVisible(service, MPartSwitcher.CONTACTS_ID);
            }
        });
        toPlaces.addListener(SWT.Selection, new Listener() {
            @Override
            public void handleEvent(Event event) {
                MPartSwitcher.setPartVisible(service, MPartSwitcher.PLACES_ID);
            }
        });
        toPlants.addListener(SWT.Selection, new Listener() {
            @Override
            public void handleEvent(Event event) {
                MPartSwitcher.setPartVisible(service, MPartSwitcher.PLANTS_ID);
            }
        });
        toCategory.addListener(SWT.Selection, new Listener() {
            @Override
            public void handleEvent(Event event) {
                MPartSwitcher.setPartVisible(service, MPartSwitcher.CATEGORY_ID);
            }
        });
        toCatalog.addListener(SWT.Selection, new Listener() {
            @Override
            public void handleEvent(Event event) {
                MPartSwitcher.setPartVisible(service, MPartSwitcher.CATALOG_ID);
            }
        });
        toOverall.addListener(SWT.Selection, new Listener() {
            @Override
            public void handleEvent(Event event) {
                MPartSwitcher.setPartVisible(service, MPartSwitcher.OVERALL_ID);
            }
        });
    }
}
