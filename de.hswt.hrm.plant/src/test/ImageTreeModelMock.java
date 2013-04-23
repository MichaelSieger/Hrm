package test;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import de.hswt.hrm.plant.model.GridImage;
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
    public GridImage[] getImages() {
        GridImage img = new GridImage();
        img.setWidth(4);
        img.setHeight(4);
        img.setRenderedImage(new Image(display, "/home/tamaran/hrmbilder/test.png"));
        return new GridImage[]{img};
    }


}
