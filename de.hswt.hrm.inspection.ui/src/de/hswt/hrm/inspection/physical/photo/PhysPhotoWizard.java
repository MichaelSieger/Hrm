package de.hswt.hrm.inspection.physical.photo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.TableItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;

import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.database.exception.ElementNotFoundException;
import de.hswt.hrm.common.database.exception.SaveException;
import de.hswt.hrm.photo.model.Photo;
import de.hswt.hrm.photo.service.PhotoService;

public class PhysPhotoWizard extends Wizard {
    private static final Logger LOG = LoggerFactory.getLogger(PhysPhotoWizard.class);
   
    @Inject
    private PhotoService photoService;
    
    private PhysPhotoWizardPageOne first;
    
//    private Optional<List<Photo>> photos;

    private Optional<List<Photo>> currentPhotoList;
    
    private TableItem[] tableItems;
    
    private List<Photo> photos;
    private List<Photo> selectedPhotos;
    
    @Inject
    IEclipseContext context;

    
    
    public PhysPhotoWizard(List<Photo> photos, List<Photo> selectedPhotos) {
    	this.photos = photos;
    	this.selectedPhotos = selectedPhotos;
        first = new PhysPhotoWizardPageOne(photos,selectedPhotos);
        ContextInjectionFactory.inject(first, context);        
            setWindowTitle("Import photos");
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
    	first.getSelectedPhotos();
		return true;

    
    }

    
    public Optional<List<Photo>> getPhotos() {
        return currentPhotoList;
    }
    public TableItem[] getTableItems(){
		return tableItems;    	
    }

}
