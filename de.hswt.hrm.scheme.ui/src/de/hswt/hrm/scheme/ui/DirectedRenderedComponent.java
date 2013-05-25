package de.hswt.hrm.scheme.ui;

import de.hswt.hrm.component.model.Component;
import de.hswt.hrm.scheme.model.Direction;
import de.hswt.hrm.scheme.model.RenderedComponent;
import de.hswt.hrm.scheme.model.ThumbnailImage;

/**
 * This class represents a RenderedComponent with a Direction
 * 
 * @author Michael Sieger
 *
 */
public class DirectedRenderedComponent extends RenderedComponent{

	private Direction direction;
	
	public DirectedRenderedComponent(Component component, ThumbnailImage leftRight,
			ThumbnailImage rightLeft, ThumbnailImage topDown, ThumbnailImage downUp,
			Direction direction) {
		super(component, leftRight, rightLeft, topDown, downUp);
		this.direction = direction;
	}
	
	public DirectedRenderedComponent(RenderedComponent comp, Direction direction){
		this(comp.getComponent(), comp.getLeftRight(), comp.getRightLeft(),
				comp.getTopDown(), comp.getDownUp(), direction);
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

}
