package de.hswt.hrm.plant.model;

import java.io.Serializable;

import org.eclipse.swt.graphics.Image;

/**
 * This class contains an Image, and its size in the grid.
 * 
 * The size ratio of the grid box can be different, that the one in the image.
 * 
 * @author Michael Sieger
 * 
 */
public class GridImage 
    implements Serializable
{

    private static final long serialVersionUID = 2906158433227663863L;
    
    private byte[] image;
    private int width, height; // Size in grid units
    private int primaryKey;

    public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(int primaryKey) {
        this.primaryKey = primaryKey;
    }

}
