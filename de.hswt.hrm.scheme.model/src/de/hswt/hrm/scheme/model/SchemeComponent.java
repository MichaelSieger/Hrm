package de.hswt.hrm.scheme.model;

import static com.google.common.base.Preconditions.*;
import de.hswt.hrm.component.model.Component;

/**
 * This class represents the position of a component in a scheme grid.
 * 
 * @author Michael Sieger
 *
 */
public class SchemeComponent {
	
    private final int id;
    private int x;
    private int y;
    private Direction direction;
    private Component component;
    
    
    public SchemeComponent(int id, int x, int y, Direction direction, Component component) {
        this.id = id;
        setX(x);
        setY(y);
        setDirection(direction);
        setComponent(component);
    }
    
    public SchemeComponent(int x, int y, Direction direction, Component component){
        this(-1, x, y, direction, component);
    }

    public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public int getId() {
        return id;
    }

    public int getX() {
        checkArgument(x >= 0);
        return x;
    }

    public void setX(int x) {
        checkArgument(y >= 0);
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

	public Component getComponent() {
		return component;
	}

	public void setComponent(Component component) {
		checkNotNull(component);
		this.component = component;
	}
    
    
    
}
