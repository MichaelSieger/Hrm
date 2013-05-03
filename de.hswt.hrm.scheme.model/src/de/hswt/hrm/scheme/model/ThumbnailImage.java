package de.hswt.hrm.scheme.model;

import org.eclipse.swt.graphics.Image;

import static com.google.common.base.Preconditions.*;

public class ThumbnailImage {
	
	private final Image image;
	private final Image thumbnail;
	
	public ThumbnailImage(Image image, Image thumbnail) {
		checkNotNull(image);
		checkNotNull(thumbnail);
		this.image = image;
		this.thumbnail = thumbnail;
	}

	public Image getImage() {
		return image;
	}

	public Image getThumbnail() {
		return thumbnail;
	}
	
	

}
