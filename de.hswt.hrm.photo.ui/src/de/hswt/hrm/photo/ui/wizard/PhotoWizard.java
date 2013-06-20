package de.hswt.hrm.photo.ui.wizard;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.TableItem;
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
    
    @Inject
    IEclipseContext context;

    
    
    public PhotoWizard(Optional<List<Photo>> photos) {
        this.photos = photos;
        first = new PhotoWizardPageOne(photos);
        ContextInjectionFactory.inject(first, context);
        
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
    	TableItem[] tableItems = first.getListItems();
    	for(TableItem item : tableItems){
    		File f = (File)item.getData();
			try {					
				FileInputStream  in = new FileInputStream(f);
				byte[] data = new byte[in.available()];
				in.read(data);
				Photo photo = new Photo(data, f.getName(), item.getText());
				
				//TODO Photoservice insert
	    		
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}    	
			catch (IOException e) {
				e.printStackTrace();
			}			
    	}  	
    	
    	return true;
    }
    
    private boolean importNewPhotos() {
    	TableItem[] tableItems = first.getListItems();
    	for(TableItem item : tableItems){
    		File f = (File)item.getData();
			try {					
				FileInputStream  in = new FileInputStream(f);
				byte[] data = new byte[in.available()];
				in.read(data);
				Photo photo = new Photo(data, f.getName(), item.getText());
				//TODO Photoservice insert
	    		
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}    	
			catch (IOException e) {
				e.printStackTrace();
			}			
    	}  	
    	return true;
    }
    
    private void fillPhotoValues(Optional<List<Photo>> photos) {
        List<Photo> newPhotos = new ArrayList<Photo>();
        
        currentPhotoList = Optional.fromNullable(newPhotos);
    }
    
    public Optional<List<Photo>> getPhotos() {
        return currentPhotoList;
    }

}
