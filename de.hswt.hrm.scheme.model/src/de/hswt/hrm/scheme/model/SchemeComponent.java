package de.hswt.hrm.scheme.model;

import static com.google.common.base.Preconditions.*;

import com.google.common.base.Optional;

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
    private Scheme scheme;
    
    
    public SchemeComponent(int id, Scheme scheme, int x, int y, Direction direction, 
    		Component component) {
    	
        this.id = id;
        setX(x);
        setY(y);
        setDirection(direction);
        setComponent(component);
        setScheme(scheme);
    }
    
    public SchemeComponent(Scheme scheme, int x, int y, Direction direction, Component component) {
        this(-1, scheme, x, y, direction, component);
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
	
	public Scheme getScheme() {
	    return scheme;
	}
	
	public void setScheme(final Scheme scheme) {
	    this.scheme = scheme;
	}
    
    
    
}
