package de.hswt.hrm.scheme.model;

import static com.google.common.base.Preconditions.*;

import java.io.Serializable;

import de.hswt.hrm.component.model.Category;
import de.hswt.hrm.component.model.Component;

/**
 * This class represents the position of a component in a scheme grid.
 * 
 * @author Michael Sieger
 *
 */
public class SchemeComponent{
	
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
        checkArgument(x >= 0);
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
    	checkArgument(y >= 0);
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
    
	private boolean isHorizontal(){
		switch(getDirection()){
		case leftRight:
		case rightLeft:
			return true;
		case upDown:
		case downUp:
			return false;
		default:
			throw new RuntimeException("More than 4 directions?");
		}
	}
	
	public int getWidth(){
		checkCategoryPresent();
		Category c = getComponent().getCategory().get();
		if(isHorizontal()){
			return c.getHeight();
		}else{
			return c.getWidth();
		}
	}
	
	public int getHeight(){
		checkCategoryPresent();
		Category c = getComponent().getCategory().get();
		if(isHorizontal()){
			return c.getWidth();
		}else{
			return c.getHeight();
		}
	}
	
	private void checkCategoryPresent(){
		if(!getComponent().getCategory().isPresent()){
			throw new IllegalArgumentException("Component category must be present for this");
		}
	}
    
}
