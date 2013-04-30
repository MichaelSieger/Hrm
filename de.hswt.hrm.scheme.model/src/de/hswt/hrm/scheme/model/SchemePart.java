package de.hswt.hrm.scheme.model;

/**
 * Represents a part of a scheme, and the position in the grid.
 * 
 * @author Anton Schreck
 * 
 */

public class SchemePart {

	private int xPos, yPos;
	private GridImage imag;

	// Getters and setters:
	public int getxPos() {
		return xPos;
	}

	public void setxPos(int xPos) {
		this.xPos = xPos;
	}

	public int getyPos() {
		return yPos;
	}

	public void setyPos(int yPos) {
		this.yPos = yPos;
	}

	public GridImage getImag() {
		return imag;
	}

	public void setImag(GridImage imag) {
		this.imag = imag;
	}

}
