package de.hswt.hrm.scheme.model;

import com.google.common.base.Preconditions;

import de.hswt.hrm.component.model.Component;

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
		ThumbnailImage img;
		switch(d){
		case downUp:
			img = downUp;
			break;
		case upDown:
			img = upDown;
			break;
		case leftRight:
			img = leftRight;
			break;
		case rightLeft:
			img = rightLeft;
			break;
		default:
			throw new RuntimeException("There are more than 4 directions?");
		}
		//Preconditions.checkNotNull(img, "The direction doesnt exist");
		return img;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((component == null) ? 0 : component.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RenderedComponent other = (RenderedComponent) obj;
		if (component == null) {
			if (other.component != null)
				return false;
		} else if (!component.equals(other.component))
			return false;
		return true;
	}
	
	

}
