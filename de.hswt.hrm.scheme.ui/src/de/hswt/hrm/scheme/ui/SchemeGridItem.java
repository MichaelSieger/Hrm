package de.hswt.hrm.scheme.ui;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;

import de.hswt.hrm.scheme.model.Category;
import de.hswt.hrm.scheme.model.Direction;
import de.hswt.hrm.scheme.model.RenderedComponent;

/**
 * Contains the propertys of a image in the SchemeGrid.
 * 
 * @author Michael Sieger
 *
 */
public class SchemeGridItem {
	
	private RenderedComponent renderedComponent;
	private Direction direction;
	private int x;
	private int y;
	
	public SchemeGridItem(RenderedComponent renderedComponent, Direction direction, 
								int x, int y) {
		super();
		this.renderedComponent = renderedComponent;
		this.direction = direction;
		this.x = x;
		this.y = y;
	}
	
	

	public RenderedComponent getRenderedComponent() {
		return renderedComponent;
	}



	public void setRenderedComponent(RenderedComponent renderedComponent) {
		this.renderedComponent = renderedComponent;
	}



	public Direction getDirection() {
		return direction;
	}



	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
    /**
     * Returns true if the image intersects the other image.
     * 
     * @param o
     * @return
     */
    boolean intersects(SchemeGridItem o){
        return getBoundingBox().intersects(o.getBoundingBox());
    }
    
    boolean intersects(Point p){
        return getBoundingBox().contains(p);
    }
    
    Rectangle getBoundingBox(){
    	Category c = renderedComponent.getComponent().getCategory();
        return new Rectangle(x, y, 
        		c.getWidth(),
        		c.getHeight());
    }

}
