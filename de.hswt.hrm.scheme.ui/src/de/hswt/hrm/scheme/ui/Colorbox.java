package de.hswt.hrm.scheme.ui;

import org.eclipse.swt.graphics.Color;

import com.google.common.base.Preconditions;

public class Colorbox {
	
	private final double x, y, width, height;
	private final Color color;
	private boolean fill;
	
	public Colorbox(double x, double y, double width, double height, Color color, boolean fill) {
		super();
		Preconditions.checkArgument(width > 0);
		Preconditions.checkArgument(height > 0);
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.color = color;
		this.fill = fill;
	}
	
	public Colorbox(double x, double y, double width, double height, Color color) {
		this(x, y, width, height, color, true);
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

	public boolean isFill() {
		return fill;
	}

	
	
}
