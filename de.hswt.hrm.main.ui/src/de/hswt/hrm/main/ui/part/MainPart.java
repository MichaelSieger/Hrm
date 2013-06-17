package de.hswt.hrm.main.ui.part;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.e4.core.commands.ECommandService;
import org.eclipse.e4.core.commands.EHandlerService;
import org.eclipse.e4.xwt.DefaultLoadingContext;
import org.eclipse.e4.xwt.IConstants;
import org.eclipse.e4.xwt.XWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import de.hswt.hrm.main.ui.ViewSwitcher;


public class MainPart {
	
	private static final String COMMAND_ID = "de.hswt.hrm.main.switchpart";

	private static final String COMMAND_PARAMETER_ID = "de.hswt.hrm.main.switchpart.idinput";
	
	@Inject
	EHandlerService handlerService;
	
	@Inject
	ECommandService commandService;
	
	private Button toContacts;
	private Button toPlaces;
	private Button toPlants;
	private Button toCatalog;
	private Button toCategory;
	private Button toInspection;
	private Button toMisc;

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
            toMisc = (Button) XWT.findElementByName(comp, "toMisc");
            toInspection = (Button) XWT.findElementByName(comp, "toInspection");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        setNavigationButton();
    }

	private void runCommand(String id) {
		Map<String, String> m = new HashMap<String, String>();
		m.put(COMMAND_PARAMETER_ID, id);
		ParameterizedCommand cmd = commandService.createCommand(COMMAND_ID, m);
    	handlerService.executeHandler(cmd);
	}

	private void setNavigationButton() {
		toContacts.addListener(SWT.Selection,new Listener() {
			@Override
			public void handleEvent(Event event) {
				runCommand(ViewSwitcher.CONTACTS_ID);
			}
		});
		toPlaces.addListener(SWT.Selection,new Listener() {
			@Override
			public void handleEvent(Event event) {
				runCommand(ViewSwitcher.PLACES_ID);
			}
		});
		toPlants.addListener(SWT.Selection,new Listener() {
			@Override
			public void handleEvent(Event event) {
				runCommand(ViewSwitcher.PLANTS_ID);
			}
		});
		toCategory.addListener(SWT.Selection,new Listener() {
			@Override
			public void handleEvent(Event event) {
				runCommand(ViewSwitcher.CATEGORY_ID);
			}
		});
		toCatalog.addListener(SWT.Selection,new Listener() {
			@Override
			public void handleEvent(Event event) {
				runCommand(ViewSwitcher.CATALOG_ID);
			}
		});
        toMisc.addListener(SWT.Selection, new Listener() {
            @Override
            public void handleEvent(Event event) {
				runCommand(ViewSwitcher.MISC_ID);
            }
        });
        toInspection.addListener(SWT.Selection, new Listener() {
            @Override
            public void handleEvent(Event event) {
				runCommand(ViewSwitcher.INSPECTION_ID);
            }
        });
	}
}
