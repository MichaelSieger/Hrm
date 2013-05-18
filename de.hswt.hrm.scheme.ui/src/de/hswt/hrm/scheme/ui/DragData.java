package de.hswt.hrm.scheme.ui;

import java.io.Serializable;

import de.hswt.hrm.scheme.model.Direction;

public class DragData 
    implements Serializable
{
    private static final long serialVersionUID = 3635333453619223967L;
    
    
    private final int id;
    private final int x;
    private final int y;
    private final Direction direction;
    
    public DragData(int id, int x, int y, Direction direction) {
        super();
        this.id = id;
        this.x = x;
        this.y = y;
        this.direction = direction;
    }
    
    public DragData(int id, Direction direction){
    	this(id, -1, -1, direction);
    }

    public int getId() {
        return id;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    public boolean hasPosition(){
    	return x == -1 && y == -1;
    }

    public Direction getDirection() {
        return direction;
    }
    
    

}
