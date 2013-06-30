package de.hswt.hrm.scheme.ui;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;

import com.google.common.base.Optional;
import static com.google.common.base.Preconditions.*;

import de.hswt.hrm.component.model.Category;
import de.hswt.hrm.scheme.model.Direction;
import de.hswt.hrm.scheme.model.RenderedComponent;
import de.hswt.hrm.scheme.model.Scheme;
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
	private final SchemeComponent schemeComponent;
	
	public SchemeGridItem(RenderedComponent renderedComponent, SchemeComponent schemeComponent) {
		super();
		checkNotNull(renderedComponent);
		checkNotNull(schemeComponent);
		this.renderedComponent = renderedComponent;
		this.schemeComponent = schemeComponent;
	}
	
	public SchemeGridItem(SchemeGridItem item) {
		this.renderedComponent = item.renderedComponent;
		this.schemeComponent = item.schemeComponent;
	}

	public SchemeComponent getSchemeComponent() {
		return schemeComponent;
	}

	public ThumbnailImage getImage(){
		return renderedComponent.getByDirection(schemeComponent.getDirection());
	}

	public Direction getDirection() {
		return schemeComponent.getDirection();
	}

	public RenderedComponent getRenderedComponent() {
		return renderedComponent;
	}

	public void setDirection(Direction direction) {
		schemeComponent.setDirection(direction);
	}

	public int getX() {
		return schemeComponent.getX();
	}

	public void setX(int x) {
		schemeComponent.setX(x);
	}

	public int getY() {
		return schemeComponent.getY();
	}

	public void setY(int y) {
		schemeComponent.setY(y);
	}
	
	public int getWidth(){
		return schemeComponent.getWidth();
	}
	
	public int getHeight(){
		return schemeComponent.getHeight();
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
        return new Rectangle(getX(), getY(), 
        		getWidth(),
        		getHeight());
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((getDirection() == null) ? 0 : getDirection().hashCode());
		result = prime
				* result
				+ ((renderedComponent == null) ? 0 : renderedComponent
						.hashCode());
		result = prime * result + getX();
		result = prime * result + getY();
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
		SchemeGridItem other = (SchemeGridItem) obj;
		if (getDirection() != other.getDirection())
			return false;
		if (renderedComponent == null) {
			if (other.renderedComponent != null)
				return false;
		} else if (!renderedComponent.equals(other.renderedComponent))
			return false;
		if (getX() != other.getX())
			return false;
		if (getY() != other.getY())
			return false;
		return true;
	}

    
}
