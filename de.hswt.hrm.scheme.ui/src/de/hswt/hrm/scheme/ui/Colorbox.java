package de.hswt.hrm.scheme.ui;

import org.eclipse.swt.graphics.Color;

public class Colorbox {
	
	private final int x, y, width, height;
	private final Color color;
	
	public Colorbox(int x, int y, int width, int height, Color color) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.color = color;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Color getColor() {
		return color;
	}

	
}
