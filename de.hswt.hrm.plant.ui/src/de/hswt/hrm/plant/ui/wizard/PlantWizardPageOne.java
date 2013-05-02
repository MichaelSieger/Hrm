package de.hswt.hrm.plant.ui.wizard;

import java.net.URL;

import org.eclipse.e4.xwt.IConstants;
import org.eclipse.e4.xwt.forms.XWTForms;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.widgets.Composite;

public class PlantWizardPageOne extends WizardPage {

    private Composite container;
    
    public PlantWizardPageOne(String title) {
        super("wizardPage");
        setTitle(title);
        setDescription("Wizard Page description");
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
    }

}
