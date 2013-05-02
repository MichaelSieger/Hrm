package de.hswt.hrm.scheme.ui;

import de.hswt.hrm.scheme.model.RenderedGridImage;

/**
 * Contains the propertys of a image in the SchemeGrid.
 * 
 * @author Michael Sieger
 *
 */
public class SchemeGridItem {
	
	private RenderedGridImage gridImage;
	private int x;
	private int y;
	
	public SchemeGridItem(RenderedGridImage gridImage, int x, int y) {
		super();
		this.gridImage = gridImage;
		this.x = x;
		this.y = y;
	}

	public RenderedGridImage getGridImage() {
		return gridImage;
	}

	public void setGridImage(RenderedGridImage gridImage) {
		this.gridImage = gridImage;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	

}
