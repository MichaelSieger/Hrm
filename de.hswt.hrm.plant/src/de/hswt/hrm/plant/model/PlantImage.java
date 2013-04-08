package de.hswt.hrm.plant.model;

/**
 * A plant part of which a plant consists.
 * It contains an Image (PostScript), and its size in the grid
 * that is used to construct a plant.
 * 
 * The size ratio of the grid box can be different, that
 * the one in the image.
 * 
 * @author Michael Sieger
 *
 */
public class PlantImage {
	
	private String postScript;
	private int width, height;	//Size in grid units
	private int primaryKey;
	
	
	
	public String getPostScript() {
        return postScript;
    }
    public void setPostScript(String postScript) {
        this.postScript = postScript;
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
