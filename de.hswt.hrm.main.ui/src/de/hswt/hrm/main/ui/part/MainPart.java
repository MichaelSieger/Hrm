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

import de.hswt.hrm.main.ui.MPartSwitcher;


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

	private void runCommand(String id) {
		System.out.println("inside run command");
		Map<String, String> m = new HashMap<String, String>();
		m.put("de.hswt.hrm.main.switchpart.idinput", id);
		ParameterizedCommand cmd = commandService.createCommand(COMMAND_ID, m);
    	handlerService.executeHandler(cmd);
	}

	private void setNavigationButton() {
		toContacts.addListener(SWT.Selection,new Listener() {
			@Override
			public void handleEvent(Event event) {
				runCommand(MPartSwitcher.CONTACTS_ID);
			}
		});
		toPlaces.addListener(SWT.Selection,new Listener() {
			@Override
			public void handleEvent(Event event) {
				runCommand(MPartSwitcher.PLACES_ID);
			}
		});
		toPlants.addListener(SWT.Selection,new Listener() {
			@Override
			public void handleEvent(Event event) {
				runCommand(MPartSwitcher.PLANTS_ID);
			}
		});
		toCategory.addListener(SWT.Selection,new Listener() {
			@Override
			public void handleEvent(Event event) {
				runCommand(MPartSwitcher.CATEGORY_ID);
			}
		});
		toCatalog.addListener(SWT.Selection,new Listener() {
			@Override
			public void handleEvent(Event event) {
				runCommand(MPartSwitcher.CATALOG_ID);
			}
		});
        toOverall.addListener(SWT.Selection, new Listener() {
            @Override
            public void handleEvent(Event event) {
				runCommand(MPartSwitcher.OVERALL_ID);
            }
        });
	}
}
