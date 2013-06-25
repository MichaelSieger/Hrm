package de.hswt.hrm.scheme.ui.dnd;

import java.io.Serializable;
import java.util.List;

import de.hswt.hrm.scheme.model.Direction;
import de.hswt.hrm.scheme.model.RenderedComponent;
import de.hswt.hrm.scheme.model.SchemeComponent;
import de.hswt.hrm.scheme.ui.SchemeGridItem;

/**
 * This class represents a object, that is dragged in the SchemePart.
 * It is a Serializable version of SchemeGridItem which is not
 * Serialized by itself for Performance reasons.
 * 
 * @author Michael Sieger
 *
 */
public class DragData 
    implements Serializable
{
    private static final long serialVersionUID = 3635333453619223967L;
    
    /**
     * The index in the RenderedComponent set.
     */
    private final int renderedComponentIndex;
    
    private final SchemeComponent schemeComponent;
    
    public DragData(int renderedComponentIndex, SchemeComponent schemeComponent) {
        super();
        this.renderedComponentIndex = renderedComponentIndex;
        this.schemeComponent = schemeComponent;
    }
    
    public boolean hasPosition(){
    	return !(schemeComponent.getX() == -1 && schemeComponent.getY() == -1);
    }
    
    /**
     * Creates the SchemeGridItem, that is DragData is representing
     * 
     * @param comps0
     * @return
     */
    public SchemeGridItem toSchemeGridItem(List<RenderedComponent> comps){
    	return new SchemeGridItem(comps.get(renderedComponentIndex), schemeComponent);
    }

}
