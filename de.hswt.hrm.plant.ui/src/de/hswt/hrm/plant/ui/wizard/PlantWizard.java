package de.hswt.hrm.plant.ui.wizard;

import org.eclipse.jface.wizard.Wizard;

public class PlantWizard extends Wizard {
    
    private PlantWizardPageOne first;

    public PlantWizard() {
        first = new PlantWizardPageOne("Erste Seite");
        setWindowTitle("Neue Anlage hinzufügen");
    }

    @Override
    public void addPages() {
        addPage(first);
    }

    @Override
    public boolean performFinish() {
        return false;
    }

}
