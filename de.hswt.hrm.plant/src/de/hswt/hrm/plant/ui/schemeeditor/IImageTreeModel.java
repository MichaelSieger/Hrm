package de.hswt.hrm.plant.ui.schemeeditor;

import de.hswt.hrm.plant.model.RenderedGridImage;

/**
 * This Interface describes an class that can return GridImages.
 * 
 * @author Michael Sieger
 *
 */
public interface IImageTreeModel {
    
    public RenderedGridImage[] getImages();

}
