package de.hswt.hrm.scheme.ui;

import java.util.List;

import de.hswt.hrm.scheme.model.RenderedComponent;

/**
 * This Interface describes an class that can return RenderedComponents.
 * 
 * @author Michael Sieger
 *
 */
public interface IImageTreeModel {
    
    public List<RenderedComponent> getImages();

}
