package de.hswt.hrm.main.ui.part;

import java.net.URL;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.EPartService.PartState;
import org.eclipse.e4.xwt.DefaultLoadingContext;
import org.eclipse.e4.xwt.IConstants;
import org.eclipse.e4.xwt.XWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;


public class MainPart {

	@Inject
	EPartService service;
	Button toContacts;
	Button toPlaces;
	Button toPlants;
	Button toScheme;
	Button toCatalog;
	Button toCategory;
	
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
            toScheme = (Button) XWT.findElementByName(comp, "toScheme");
            toCatalog = (Button) XWT.findElementByName(comp, "toCatalog");
            toCategory = (Button) XWT.findElementByName(comp, "toCategory");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        setNavigationButton();
        
    }

	private void setNavigationButton() {
		toContacts.addListener(SWT.Selection,new Listener() {
			@Override
			public void handleEvent(Event event) {
				setPartsVisibility(true, false, false, false, false, false, false);				
			}
		});
		toPlaces.addListener(SWT.Selection,new Listener() {
			@Override
			public void handleEvent(Event event) {
				setPartsVisibility(false, true, false, false, false, false, false);				
			}
		});
		toPlants.addListener(SWT.Selection,new Listener() {
			@Override
			public void handleEvent(Event event) {
				setPartsVisibility(false, false, true, false, false, false, false);				
			}
		});
		toScheme.addListener(SWT.Selection,new Listener() {
			@Override
			public void handleEvent(Event event) {
				setPartsVisibility(false, false, false, true, false, false, false);				
			}
		});
		toCategory.addListener(SWT.Selection,new Listener() {
			@Override
			public void handleEvent(Event event) {
				setPartsVisibility(false, false, false, false, false, true, false);				
			}
		});
		toCatalog.addListener(SWT.Selection,new Listener() {
			@Override
			public void handleEvent(Event event) {
				setPartsVisibility(false, false, false, false, true, false, false);				
			}
		});

		
	}
    private void setPartsVisibility(boolean clients, boolean places, boolean plants, boolean scheme, boolean catalog, boolean category, boolean main){
		service.findPart("Clients").setVisible(clients);
		service.findPart("Places").setVisible(places);
		service.findPart("Plants").setVisible(plants);
		service.findPart("Scheme").setVisible(scheme);
		service.findPart("Main").setVisible(main);
		service.findPart("Category").setVisible(category);
		service.findPart("Catalog").setVisible(catalog);  
		if(catalog)
			service.showPart("Catalog", PartState.VISIBLE);
		if(category)
			service.showPart("Category", PartState.VISIBLE);
    }
}
