package de.hswt.hrm.plant.ui.wizard;

import org.eclipse.jface.wizard.Wizard;

import de.hswt.hrm.plant.model.Plant;

public class PlantWizard extends Wizard {
    
    private PlantWizardPageOne first;

    public PlantWizard() {
        Plant p = null;
        first = new PlantWizardPageOne("Erste Seite", p);
        setWindowTitle("Neue Anlage hinzufügen");
    }

    @Override
    public void addPages() {
        addPage(first);
    }
    
    @Override
    public boolean canFinish() {
        return first.isPageComplete();
    }

    @Override
    public boolean performFinish() {
        if (first.getPlant() == null) {
            return insertNewPlant();
        } else {
            return editExistingPlant();
        }
    }
    
    private boolean insertNewPlant() {
        //TODO insert a new plant
        return false;
    }
    
    private boolean editExistingPlant() {
        //TODO edit an existing plant
        return false;
    }

}
