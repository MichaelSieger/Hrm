package de.hswt.hrm.main.ui;

import javax.inject.Inject;

import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;

public class ViewSwitcher {

	@Inject
	private static MApplication application; 
	
	@Inject
	private static EModelService modelService;

	@Inject
	private static EPartService service;
	
    public static final String CONTACTS_ID = "de.hswt.hrm.contact.ui.contacts";
    public static final String PLACES_ID = "de.hswt.hrm.place.ui.places";
    public static final String PLANTS_ID = "de.hswt.hrm.plant.ui.plants";
    public static final String SCHEMES_ID = "de.hswt.hrm.scheme.ui.scheme";
    public static final String MAIN_ID = "de.hswt.hrm.main.ui.main";
    public static final String COMPONENTS_ID = "de.hswt.hrm.component.ui.components";
    public static final String CATALOG_ID = "de.hswt.hrm.catalog.ui.catalog";
    public static final String SIDEBAR_ID = "de.hswt.hrm.main.ui.sidebar";
    public static final String INSPECTION_ID = "de.hswt.hrm.inspection.ui.inspection";
    public static final String MISC_ID = "de.hswt.hrm.misc.ui.misc";
    public static final String MATCHING_ID = "de.hswt.hrm.catalog.ui.matching";

    public static void setPartVisible(String id) {

        if (modelService.find(id, application) == null) {
            return;
        }
    	
		MUIElement result = modelService.find("de.hswt.hrm.main.partSwitchToolBar", application);
		if (result != null) {
			if (id.equals(ViewSwitcher.MAIN_ID)) {
				result.setVisible(false);
			} else {
				result.setVisible(true);
			}
		}

    	MUIElement element = modelService.find(id, application);
    	
        if (element == null) {
            return;
        }
        if (!element.isToBeRendered()) {
            element.setToBeRendered(true);
//            service.createPart(id);
        }

		hideAll();
		
		element.setVisible(true);
	}
	
	private static void hideAll() {
		service.findPart(CATALOG_ID).setVisible(false);
		service.findPart(COMPONENTS_ID).setVisible(false);
		service.findPart(CONTACTS_ID).setVisible(false);
		service.findPart(MAIN_ID).setVisible(false);
		service.findPart(MISC_ID).setVisible(false);
		service.findPart(PLACES_ID).setVisible(false);
		service.findPart(PLANTS_ID).setVisible(false);
		service.findPart(INSPECTION_ID).setVisible(false);
//		service.findPart(SCHEMES_ID).setVisible(false);
//		service.findPart(SIDEBAR_ID).setVisible(false);
	}
}
