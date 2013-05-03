package de.hswt.hrm.scheme.ui;

import de.hswt.hrm.scheme.model.Direction;
import de.hswt.hrm.scheme.model.RenderedComponent;

/**
 * This is the data that is appended to the TreeNodes
 * 
 * @author Michael Sieger
 *
 */
public class TreeData {
	
	private final RenderedComponent renderedComponent;
	private final Direction direction;
	
	public TreeData(RenderedComponent renderedComponent, Direction direction) {
		super();
		this.renderedComponent = renderedComponent;
		this.direction = direction;
	}

	public RenderedComponent getRenderedComponent() {
		return renderedComponent;
	}

	public Direction getDirection() {
		return direction;
	}

	
}
