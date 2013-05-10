package de.hswt.hrm.scheme.ui;

import de.hswt.hrm.scheme.model.Direction;
import de.hswt.hrm.scheme.model.RenderedComponent;

/**
 * This class represents a RenderedComponent with a Direction
 * 
 * @author Michael Sieger
 *
 */
public class DirectedRenderedComponent {
	
	private final RenderedComponent renderedComponent;
	private Direction direction;
	
	public DirectedRenderedComponent(RenderedComponent renderedComponent,
			Direction direction) {
		super();
		this.renderedComponent = renderedComponent;
		this.direction = direction;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public RenderedComponent getRenderedComponent() {
		return renderedComponent;
	}
	
	
	
}
