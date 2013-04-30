package de.hswt.hrm.scheme.ui;

import de.hswt.hrm.scheme.model.RenderedGridImage;

/**
 * This Interface describes an class that can return GridImages.
 * 
 * @author Michael Sieger
 *
 */
public interface IImageTreeModel {
    
    public RenderedGridImage[] getImages();

}
