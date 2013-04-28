package de.hswt.hrm.plant.model;

import org.eclipse.swt.graphics.Image;

/**
 * This class is the rendered version of a GridImage
 * 
 * @author Michael Sieger
 *
 */
public class RenderedGridImage{
	
	private final Image thumbnail;
	private final Image image;
	private final GridImage gridImage;
	
	public RenderedGridImage(Image thumbnail, Image image, GridImage gridImage) {
		super();
		this.thumbnail = thumbnail;
		this.image = image;
		this.gridImage = gridImage;
	}

	public Image getThumbnail() {
		return thumbnail;
	}

	public Image getImage() {
		return image;
	}

	public GridImage getGridImage() {
		return gridImage;
	}
	
	
}
