package de.hswt.hrm.main.ui;

import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EPartService;

public class MPartSwitcher {

    public static final String CONTACTS_ID = "de.hswt.hrm.contact.ui.contacts";
    public static final String PLACES_ID = "de.hswt.hrm.place.ui.places";
    public static final String PLANTS_ID = "de.hswt.hrm.plant.ui.plants";
    public static final String SCHEMES_ID = "de.hswt.hrm.scheme.ui.scheme";
    public static final String MAIN_ID = "de.hswt.hrm.main.ui.main";
    public static final String CATEGORY_ID = "de.hswt.hrm.component.ui.category";
    public static final String CATALOG_ID = "de.hswt.hrm.catalog.ui.catalog";
    public static final String SIDEBAR_ID = "de.hswt.hrm.main.ui.sidebar";
    public static final String INSPECTION_ID = "de.hswt.hrm.main.ui.inspection";
    public static final String OVERALL_ID = "de.hswt.hrm.main.ui.overall";

    public static void setPartVisible(EPartService service, String id) {
        MPart part = service.findPart(id);
        if (part == null) {
            return;
        }
        if (!part.isToBeRendered()) {
            part.setToBeRendered(true);
            service.createPart(id);
        }

        hideAll(service);

        part.setVisible(true);
        if (!id.equals(MAIN_ID)) {
            service.findPart(SIDEBAR_ID).setVisible(true);
        }
    }

    private static void hideAll(EPartService service) {
        service.findPart(CATALOG_ID).setVisible(false);
        service.findPart(CATEGORY_ID).setVisible(false);
        service.findPart(CONTACTS_ID).setVisible(false);
        // service.findPart(INSPECTION_ID).setVisible(false);
        service.findPart(MAIN_ID).setVisible(false);
        service.findPart(OVERALL_ID).setVisible(false);
        service.findPart(PLACES_ID).setVisible(false);
        service.findPart(PLANTS_ID).setVisible(false);
        // service.findPart(SCHEMES_ID).setVisible(false);
        service.findPart(SIDEBAR_ID).setVisible(false);
    }
}
