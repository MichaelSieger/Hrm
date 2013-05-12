package de.hswt.hrm.place.ui.wizard;

import javax.inject.Inject;

import org.eclipse.jface.wizard.Wizard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;

import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.database.exception.SaveException;
import de.hswt.hrm.place.model.Place;
import de.hswt.hrm.place.service.PlaceService;

public class PlaceWizard extends Wizard {
    private static final Logger LOG = LoggerFactory.getLogger(PlaceWizard.class);
    private PlaceWizardPageOne first;
    private Optional<Place> place;
    
    @Inject
    private PlaceService placeService;

    public PlaceWizard(Optional<Place> place) {
        this.place = place;
        
        if (place.isPresent()) {
            setWindowTitle("Standort bearbeiten");
        }
        else {
            setWindowTitle("Neuen Standort hinzufügen");
        }
    }
    
    public Optional<Place> getPlace() {
        return place;
    }

    @Override
    public void addPages() {
        first = new PlaceWizardPageOne("First Page", place);
        addPage(first);
    }

    @Override
    public boolean canFinish() {
        return first.isPageComplete();
    }

    @Override
    public boolean performFinish() {
    	if (place.isPresent()) {
    	    return editExistingPlace();
    	}

    	return insertNewPlace(); 
    }
    
    private boolean editExistingPlace() {
        if (placeService == null) {
            LOG.error("PlaceService not injected to PlaceWizard.");
        }
        
        try {
            Place p = first.getPlace();
            placeService.update(p);
            place = Optional.of(p);
        }
        catch (DatabaseException e) {
            LOG.error("Could not update place.", e);
            return false;
        }

        return true;
    }

    private boolean insertNewPlace() {
        if (placeService == null) {
            LOG.error("PlaceService not injected to PlaceWizard.");
        }
        
        try {
            Place p = first.getPlace();
            place = Optional.of(placeService.insert(p));
        }
        catch (SaveException e) {
            LOG.error("Could not insert place into database", e);
            return false;
        }

        return true;
    }

}
