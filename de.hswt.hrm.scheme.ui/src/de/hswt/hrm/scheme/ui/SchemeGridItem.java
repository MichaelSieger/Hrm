package de.hswt.hrm.scheme.ui;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;

import com.google.common.base.Optional;

import de.hswt.hrm.component.model.Category;
import de.hswt.hrm.scheme.model.Direction;
import de.hswt.hrm.scheme.model.RenderedComponent;
import de.hswt.hrm.scheme.model.SchemeComponent;
import de.hswt.hrm.scheme.model.ThumbnailImage;

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
	
	public SchemeGridItem(SchemeGridItem item) {
		this.renderedComponent = item.renderedComponent;
		this.direction = item.direction;
		this.x = item.x;
		this.y = item.y;
	}

	public SchemeComponent asSchemeComponent(){
		return new SchemeComponent(x, y, direction, renderedComponent.getComponent());
	}

	public ThumbnailImage getImage(){
		return renderedComponent.getByDirection(direction);
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
    	Optional<Category> op = renderedComponent.getComponent().getCategory();
    	if(!op.isPresent()){
    		throw new IllegalArgumentException("The Category may not be absent here");
    	}
    	Category c = op.get();
        return new Rectangle(x, y, 
        		c.getWidth(),
        		c.getHeight());
    }

}
