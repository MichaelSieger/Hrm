package de.hswt.hrm.plant.ui.newplant;

import java.net.URL;

import org.eclipse.e4.xwt.IConstants;
import org.eclipse.e4.xwt.XWT;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

public class NewPlantWizardPageOne extends WizardPage {

    /**
     * Create the wizard.
     */
    
    private Composite container;
    
    public NewPlantWizardPageOne(String title) {
        super("wizardPage");
        setTitle(title);
        setDescription("Wizard Page description");
    }

    /**
     * Create contents of the wizard.
     * @param parent
     */
    public void createControl(Composite parent) {
        URL url = NewPlantWizardPageOne.class.getClassLoader().getResource(
                "de/hswt/hrm/plant/ui/newplant/NewPlantWizardPage"+IConstants.XWT_EXTENSION_SUFFIX);
        try {
            container = (Composite) XWT.load(parent, url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        setControl(container);
        setPageComplete(true);
    }

}
