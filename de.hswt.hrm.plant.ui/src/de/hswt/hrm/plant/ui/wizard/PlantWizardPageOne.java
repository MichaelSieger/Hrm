package de.hswt.hrm.plant.ui.wizard;

import java.net.URL;
import java.util.HashMap;

import org.eclipse.e4.xwt.IConstants;
import org.eclipse.e4.xwt.XWT;
import org.eclipse.e4.xwt.forms.XWTForms;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

public class PlantWizardPageOne extends WizardPage {

    private Composite container;
    
    public PlantWizardPageOne(String title) {
        super("wizardPage");
        setTitle(title);
        setDescription("Neue Anlage hinzufügen oder bestehende verändern.");
    }

    public void createControl(Composite parent) {
        URL url = PlantWizardPageOne.class.getClassLoader().getResource(
                "de/hswt/hrm/plant/ui/xwt/PlantWizardWindow" + IConstants.XWT_EXTENSION_SUFFIX);
        try {
            container = (Composite) XWTForms.load(parent, url);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        setControl(container);
        setKeyListener();
    }
    
    public HashMap<String, Text> getMandatoryWidgets() {
        HashMap<String, Text> widgets = new HashMap<String, Text>();
        widgets.put("name", (Text) XWT.findElementByName(container, "name"));
        widgets.put("place", (Text) XWT.findElementByName(container, "place"));
        widgets.put("nextInspection", (Text) XWT.findElementByName(container, "nextInspection"));
        widgets.put("inspectionIntervall", (Text) XWT.findElementByName(container, "inspectionIntervall"));
        widgets.put("scheme", (Text) XWT.findElementByName(container, "scheme"));
        return widgets;
    }
    
    @Override
    public boolean isPageComplete() {
        for (Text textField : getMandatoryWidgets().values()) {
            if (textField.getText().length() == 0) {
                return false;
            }
        }
        return true;
    }
    
    public void setKeyListener() {
        HashMap<String, Text> widgets = getMandatoryWidgets();
        for (Text text : widgets.values()) {

            text.addKeyListener(new KeyListener() {

                @Override
                public void keyPressed(KeyEvent e) {
                }

                @Override
                public void keyReleased(KeyEvent e) {
                    getWizard().getContainer().updateButtons();
                }
            });
        }
    }

}
