package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.eclipse.swt.widgets.Display;

import de.hswt.hrm.plant.logic.GridImageConverter;
import de.hswt.hrm.plant.model.GridImage;
import de.hswt.hrm.plant.model.RenderedGridImage;
import de.hswt.hrm.plant.ui.schemeeditor.IImageTreeModel;

/**
 * A Mock for the grid image tree data.
 * 
 * @author Michael Sieger
 * 
 */
public class ImageTreeModelMock implements IImageTreeModel {
    
    private final Display display;

    public ImageTreeModelMock(Display display) {
        super();
        this.display = display;
    }

    @Override
    public RenderedGridImage[] getImages() {
    	try{
            GridImage img = new GridImage();
            img.setWidth(5);
            img.setHeight(5);
            img.setImage(loadBytes(new File(this.getClass().getResource("pdftest.pdf").getFile())));
            return new RenderedGridImage[]{GridImageConverter.convert(display, img)};
    	}catch(Exception e){
    		throw new RuntimeException(e);
    	}
    }

    private byte[] loadBytes(File f) throws FileNotFoundException, IOException{
    	try(FileInputStream fin = new FileInputStream(f)){
    		byte[] r = new byte[fin.available()];
    		fin.read(r);
    		return r;
    	}
    }

}
