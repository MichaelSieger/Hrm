package de.hswt.hrm.scheme.ui;

import org.eclipse.swt.graphics.Color;

public class Colorbox {
	
	private final double x, y, width, height;
	private final Color color;
	
	public Colorbox(double x, double y, double width, double height, Color color) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.color = color;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getWidth() {
		return width;
	}

	public double getHeight() {
		return height;
	}

	public Color getColor() {
		return color;
	}

	
}
