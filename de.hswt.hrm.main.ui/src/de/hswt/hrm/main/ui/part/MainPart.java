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
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;

import de.hswt.hrm.i18n.I18n;
import de.hswt.hrm.i18n.I18nFactory;
import de.hswt.hrm.main.ui.ViewSwitcher;


public class MainPart {
	
	private static final String COMMAND_ID = "de.hswt.hrm.main.switchpart";

	private static final String COMMAND_PARAMETER_ID = "de.hswt.hrm.main.switchpart.idinput";
	
	private static final I18n I18N = I18nFactory.getI18n(MainPart.class);
	
	@Inject
	EHandlerService handlerService;
	
	@Inject
	ECommandService commandService;
	
	private Button toContacts;
	private Button toPlaces;
	private Button toPlants;
	private Button toCatalog;
	private Button toComponents;
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
            toComponents = (Button) XWT.findElementByName(comp, "toComponents");
            toMisc = (Button) XWT.findElementByName(comp, "toMisc");
            toInspection = (Button) XWT.findElementByName(comp, "toInspection");
            translate(comp);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        setNavigationButton();
    }
    
    private void translate(Composite comp) {
        setLabelText(comp, "lblToContacts", "\n"+I18N.tr("Contacts")+":\n\t-"
                +I18N.tr("Add contacts")+"\n\t-"+I18N.tr("Edit contacts"));
        
        setLabelText(comp, "lblToPlaces", "\n"+I18N.tr("Places")+":\n\t-"
                +I18N.tr("Add places")+"\n\t-"+I18N.tr("Edit places"));
        
        setLabelText(comp, "lblToPlants", I18N.tr("Plants")+":\n\t-"
                +I18N.tr("Add plants")+"\n\t-"+I18N.tr("Edit plants"));
        
        setLabelText(comp, "lblToCatalog", I18N.tr("Catalog")+":\n\t-"
                +I18N.tr("Add catalog")+"\n\t-"+I18N.tr("Edit catalog"));
        
        setLabelText(comp, "lblToComponents", I18N.tr("Components")+"\n\t-"
                +I18N.tr("Add components")+"\n\t-"+I18N.tr("Edit components"));
        
        setLabelText(comp, "lblToInspection", I18N.tr("Inspection")+"\n\t-"
                +I18N.tr("Add inspection")+"\n\t-"+I18N.tr("Edit inspection"));
        
        setLabelText(comp, "lblToMisc", I18N.tr("Miscellaneous")+"\n\t-"
                +I18N.tr("Summaries")+"\n\t-"+I18N.tr("Comments")+"\n\t-"
                +I18N.tr("Priorities")+"\n\t-"+I18N.tr("Preferences"));
    }
    
    private void setLabelText(Composite comp, String labelName, String text) {
        Label l = (Label) XWT.findElementByName(comp, labelName);
        if (l == null) {
            return;
        }
        l.setText(text);
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
		toComponents.addListener(SWT.Selection,new Listener() {
			@Override
			public void handleEvent(Event event) {
				runCommand(ViewSwitcher.COMPONENTS_ID);
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
