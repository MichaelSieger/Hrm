package de.hswt.hrm.plant.ui.newplant;

import org.eclipse.jface.wizard.Wizard;

public class NewPlantWizard extends Wizard {
    
    private NewPlantWizardPageOne pageOne; 

    public NewPlantWizard() {
        setWindowTitle("New Wizard");
    }

    @Override
    public void addPages() {
        pageOne = new NewPlantWizardPageOne("Page One");
        addPage(pageOne);
    }

    @Override
    public boolean performFinish() {
        return false;
    }

}
