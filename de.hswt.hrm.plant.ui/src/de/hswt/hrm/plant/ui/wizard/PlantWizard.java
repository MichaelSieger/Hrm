package de.hswt.hrm.plant.ui.wizard;

import org.eclipse.jface.wizard.Wizard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;

import de.hswt.hrm.plant.model.Plant;

public class PlantWizard extends Wizard {
    
    private static final Logger LOG = LoggerFactory.getLogger(PlantWizard.class);
    private PlantWizardPageOne first;
    private Optional<Plant> plant;

    public PlantWizard(Optional<Plant> plant) {
        this.plant = plant;
        first = new PlantWizardPageOne("Erste Seite", plant);
        
        if (plant.isPresent()) {
            setWindowTitle("Anlage bearbeiten");
        } else {
            setWindowTitle("Neue Anlage erstellen");
        } 
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
        if (plant.isPresent()) {
            return editExistingPlant();
        } else {
            return insertNewPlant();
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
    
    public Optional<Plant> getPlant() {
        return plant;
    }

}
