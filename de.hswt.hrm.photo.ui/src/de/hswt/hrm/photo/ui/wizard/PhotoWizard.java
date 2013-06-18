package de.hswt.hrm.photo.ui.wizard;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.jface.wizard.Wizard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;

import de.hswt.hrm.photo.model.Photo;

public class PhotoWizard extends Wizard {
    private static final Logger LOG = LoggerFactory.getLogger(PhotoWizard.class);

//    @Inject
//    private PhotoService photoService;
    
    private PhotoWizardPageOne first;
    
    private Optional<List<Photo>> photos;

    private Optional<List<Photo>> currentPhotoList;

    
    
    public PhotoWizard(Optional<List<Photo>> photos) {
        this.photos = photos;
        first = new PhotoWizardPageOne(photos);
        
        if (photos.isPresent()) {
        	setWindowTitle("Edit photos");
        } else {
            setWindowTitle("Import photos");
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
        if (photos.isPresent()) {
            return editExistingPhotos();
        } else {
            return importNewPhotos();
        }
    }
    
    private boolean editExistingPhotos() {
    	// TODO update photos
    	return true;
    }
    
    private boolean importNewPhotos() {
    	// TODO add/import photos
    	return true;
    }
    
    private void fillPhotoValues(Optional<List<Photo>> photos) {
        List<Photo> newPhotos = new ArrayList<Photo>();
        
        // TODO 
        
        currentPhotoList = Optional.fromNullable(newPhotos);
    }
    
    public Optional<List<Photo>> getPhotos() {
        return currentPhotoList;
    }

}
