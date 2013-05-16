package de.hswt.hrm.scheme.ui;

import java.io.Serializable;

import de.hswt.hrm.scheme.model.Direction;
import de.hswt.hrm.scheme.ui.part.SchemePart;

public class DragData 
    implements Serializable
{
    private static final long serialVersionUID = 3635333453619223967L;
    
    
    private final int id;
    private final int x;
    private final int y;
    private final Direction direction;
    
    public DragData(SchemePart part, DirectedRenderedComponent comp){
        id = part.getRenderedComponentId(comp);
    }

}
