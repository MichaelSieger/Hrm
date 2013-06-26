package de.hswt.hrm.photo.ui.wizard;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
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

import de.hswt.hrm.common.database.exception.ElementNotFoundException;
import de.hswt.hrm.common.database.exception.SaveException;
import de.hswt.hrm.photo.model.Photo;
import de.hswt.hrm.photo.service.PhotoService;

public class PhotoWizard extends Wizard {
    private static final Logger LOG = LoggerFactory.getLogger(PhotoWizard.class);
    @Inject
    private PhotoService photoService;
    
    private PhotoWizardPageOne first;
    
//    private Optional<List<Photo>> photos;

    private Optional<List<Photo>> currentPhotoList;
    
    private TableItem[] tableItems;
    
    private List<Photo> photos;
    
    @Inject
    IEclipseContext context;

    
    
    public PhotoWizard(List<Photo> photos) {
        this.photos = photos;
        first = new PhotoWizardPageOne(photos);
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

    	int i = 0;
    	for(TableItem item : first.getTableItems()){
    		item.getText();
    		this.photos.get(i).setLabel(item.getText());
    		i++;
    	}
//FIXME enable PHOTOservice
    	int x = 0;
    	for(Photo photo : photos){
    		if(photo.getId() == -1){
    			try {
					photos.set(x,photoService.insert(photo));
				} catch (SaveException e) {
					e.printStackTrace();
				} 
    		} else {
    			try {
					photoService.update(photo);
				} catch (ElementNotFoundException | SaveException e) {
					e.printStackTrace();
				}	
    		}
    		x++;
    		
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
    public TableItem[] getTableItems(){
		return tableItems;    	
    }

}
