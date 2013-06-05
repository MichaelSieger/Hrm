package de.hswt.hrm.main.ui.sidebarPart;

import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.EPartService.PartState;
import org.eclipse.e4.xwt.XWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

public final class SidebarPartUtil {
	
    private SidebarPartUtil() {
    }    
    
    public static void setListenerForToolbar(final EPartService service, ToolBar bar){    	
    	((ToolItem) XWT.findElementByName(bar, "toPlaces")).addListener(SWT.Selection, new Listener() {			
			@Override
			public void handleEvent(Event event) {
				setPartsVisibility(service, false ,true,false,false,false,false,false,true);
			}
		});
    	((ToolItem) XWT.findElementByName(bar, "toPlants")).addListener(SWT.Selection, new Listener() {			
			@Override
			public void handleEvent(Event event) {
				setPartsVisibility(service, false ,false,true,false,false,false,false,true);
			}
		});
    	((ToolItem) XWT.findElementByName(bar, "toCatalog")).addListener(SWT.Selection, new Listener() {			
			@Override
			public void handleEvent(Event event) {
				setPartsVisibility(service, false ,false,false,false,true,false,false,true);
			}
		});
    	((ToolItem) XWT.findElementByName(bar, "toCategory")).addListener(SWT.Selection, new Listener() {			
			@Override
			public void handleEvent(Event event) {
				setPartsVisibility(service, false ,false,false,false,false,true,false,true);
			}
		});
    	((ToolItem) XWT.findElementByName(bar, "toMain")).addListener(SWT.Selection, new Listener() {			
			@Override
			public void handleEvent(Event event) {
				setPartsVisibility(service, false ,false,false,false,false,false,true,false);
			}
		});
    	((ToolItem) XWT.findElementByName(bar, "toContacts")).addListener(SWT.Selection, new Listener() {			
			@Override
			public void handleEvent(Event event) {
				setPartsVisibility(service, true ,false,false,false,false,false,false,true);
			}
		});   	
    	
    }
    
   private static void setPartsVisibility(EPartService service , boolean clients, boolean places, boolean plants, boolean scheme, boolean catalog, boolean category, boolean main,boolean sidebar){
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
		service.findPart("Sidebar").setVisible(sidebar);
		
    }

}
