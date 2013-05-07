package de.hswt.hrm.place.ui.wizard;

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

    public PlaceWizard(Optional<Place> place) {
        this.place = place;
        
        if (place.isPresent()) {
            setWindowTitle("Standort bearbeiten");
        }
        else {
            setWindowTitle("Neuen Standort hinzufügen");
        }
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
        try {
            Place p = first.getPlace();
            PlaceService.update(p);
            place = Optional.of(p);
        }
        catch (DatabaseException e) {
            LOG.error("Could not update place.", e);
            return false;
        }

        return true;
    }

    private boolean insertNewPlace() {
        try {
            Place p = first.getPlace();
            place = Optional.of(PlaceService.insert(p));
        }
        catch (SaveException e) {
            LOG.error("Could not insert place into database", e);
            return false;
        }

        return true;
    }

}
