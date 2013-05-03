package de.hswt.hrm.scheme.model;

/**
 * Represents a Component with all images beeing rendered
 * 
 * @author Michael Sieger
 *
 */
public class RenderedComponent {
	
	private final Component component;
	private final ThumbnailImage leftRight;
	private final ThumbnailImage rightLeft;
	private final ThumbnailImage upDown;
	private final ThumbnailImage downUp;
	
	public RenderedComponent(Component component, ThumbnailImage leftRight,
			ThumbnailImage rightLeft, ThumbnailImage topDown,
			ThumbnailImage downUp) {
		this.component = component;
		this.leftRight = leftRight;
		this.rightLeft = rightLeft;
		this.topDown = topDown;
		this.downUp = downUp;
	}

	public Component getComponent() {
		return component;
	}

	public ThumbnailImage getLeftRight() {
		return leftRight;
	}

	public ThumbnailImage getRightLeft() {
		return rightLeft;
	}

	public ThumbnailImage getTopDown() {
		return topDown;
	}

	public ThumbnailImage getDownUp() {
		return downUp;
	}

}
