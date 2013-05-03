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
		this.upDown = topDown;
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
		return upDown;
	}

	public ThumbnailImage getDownUp() {
		return downUp;
	}

	/**
	 * Returnes the image by the given direction
	 * 
	 * @param d
	 * @return
	 */
	public ThumbnailImage getByDirection(Direction d){
		switch(d){
		case downUp:
			return downUp;
		case upDown:
			return upDown;
		case leftRight:
			return leftRight;
		case rightLeft:
			return rightLeft;
		default:
			throw new RuntimeException("There are more than 4 directions?");
		}
	}

}
