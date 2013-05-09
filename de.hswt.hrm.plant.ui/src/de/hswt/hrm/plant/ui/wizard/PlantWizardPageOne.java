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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hswt.hrm.plant.model.Plant;

public class PlantWizardPageOne extends WizardPage {

    private static final Logger LOG = LoggerFactory.getLogger(PlantWizardPageOne.class);
    
    private Composite container;
    private Plant plant;
    
    public PlantWizardPageOne(String title, Plant plant) {
        super(title);
        this.plant = plant;
        setDescription("Neue Anlage hinzufügen oder bestehende verändern.");
    }

    public void createControl(Composite parent) {
        URL url = PlantWizardPageOne.class.getClassLoader().getResource(
                "de/hswt/hrm/plant/ui/xwt/PlantWizardWindow" + IConstants.XWT_EXTENSION_SUFFIX);
        try {
            container = (Composite) XWTForms.load(parent, url);
        }
        catch (Exception e) {
            LOG.error("An error occured: ", e);
        }
        if (plant != null) {
            fillMandatoryFields(container);
        }
        setControl(container);
        setKeyListener();
        setPageComplete(false);
    }
    
    private void fillMandatoryFields(Composite Container) {
        Text t = (Text) XWT.findElementByName(container, "description");
        t.setText(plant.getDescription());
        //TODO place
        //TODO nextInspection
        //TODO scheme 
        t = (Text) XWT.findElementByName(container, "inspectionIntervall");
        t.setText(Integer.toString(plant.getInspectionInterval()));
    }
    
    
    
    public HashMap<String, Text> getMandatoryWidgets() {
        HashMap<String, Text> widgets = new HashMap<String, Text>();
        widgets.put("description", (Text) XWT.findElementByName(container, "description"));
        //TODO place
        //widgets.put("place", (Text) XWT.findElementByName(container, "place"));
        widgets.put("nextInspection", (Text) XWT.findElementByName(container, "nextInspection"));
        widgets.put("inspectionIntervall", (Text) XWT.findElementByName(container, "inspectionIntervall"));
        //TODO scheme
        //widgets.put("scheme", (Text) XWT.findElementByName(container, "scheme"));
        return widgets;
    }
    
    public HashMap<String, Text> getOptionalWidgets() {
        HashMap<String, Text> widgets = new HashMap<String, Text>();
        widgets.put("manufactor", (Text) XWT.findElementByName(container, "manufactor"));
        widgets.put("constructionYear", (Text) XWT.findElementByName(container, "constructionYear"));
        widgets.put("type", (Text) XWT.findElementByName(container, "type"));
        widgets.put("airPerformance", (Text) XWT.findElementByName(container, "airPerformance"));
        widgets.put("motorPower", (Text) XWT.findElementByName(container, "motorPower"));
        widgets.put("ventilatorPerformance", (Text) XWT.findElementByName(container, "ventilatorPerformance"));
        widgets.put("motorRPM", (Text) XWT.findElementByName(container, "motorRPM"));
        widgets.put("current", (Text) XWT.findElementByName(container, "current"));
        widgets.put("voltage", (Text) XWT.findElementByName(container, "voltage"));
        widgets.put("note", (Text) XWT.findElementByName(container, "note"));
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
    
    public Plant getPlant() {
        return plant;
    }

}
