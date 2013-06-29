package de.hswt.hrm.inspection.ui.grid;

import java.util.Collection;

import org.eclipse.swt.graphics.Rectangle;

import com.google.common.base.Preconditions;

import de.hswt.hrm.inspection.model.SamplingPointType;
import de.hswt.hrm.scheme.model.Direction;
import de.hswt.hrm.scheme.model.RenderedComponent;
import de.hswt.hrm.scheme.model.Scheme;
import de.hswt.hrm.scheme.model.SchemeComponent;
import de.hswt.hrm.scheme.ui.SchemeGridItem;

public class SamplingPointPosition {
	
	public static class Placement{
		int x;
		int y;
		int width;
		int height;
		Direction direction;
		public Placement(int x, int y, int width, int height,
				Direction direction) {
			super();
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
			this.direction = direction;
		}
		
		public Rectangle getRectangle(){
			return new Rectangle(x, y, width, height);
		}
		
	}
	
	public static SchemeGridItem getSamplingPoint(
				SamplingPoints points, 
				SamplingPointType type,
				Scheme scheme, SchemeComponent orig, Placement pos){

		RenderedComponent rc = points.getRenderedComponent(type, isEven(orig, pos.direction));
		SchemeComponent comp = new SchemeComponent(scheme, pos.x, pos.y, pos.direction, rc.getComponent());
		return new SchemeGridItem(rc, comp);
	}
	
	public static Placement getPlacement(Collection<SchemeGridItem> items, SchemeComponent orig){
		Placement pos = null;
		for(Placement r : getPlacements(orig)){
			if(canPlace(items, r)){
				pos = r;
				break;
			}
		}
		return pos;
	}
	
	private static boolean isEven(SchemeComponent c, Direction dir){
		int edge;
		switch(dir){
		case upDown:
		case downUp:
			edge = c.getWidth();
			break;
		case leftRight:
		case rightLeft:
			edge = c.getHeight();
			break;
		default:
			throw new RuntimeException("Internal Error");
		}
		return edge % 2 == 0;
	}
	
	private static boolean canPlace(Collection<SchemeGridItem> items, Placement r){
		for(SchemeGridItem c : items){
			Rectangle r2 = new Rectangle(c.getX(), c.getY(), c.getWidth(), c.getHeight());
			if(r.getRectangle().intersects(r2)){
				return false;
			}
		}
		return true;
	}
	
	private static Placement[] getPlacements(SchemeComponent c){
		int xm = c.getX() + c.getWidth() / 2;
		int ym = c.getY() + c.getHeight() / 2;
		Placement top = new Placement(xm, c.getY() + c.getHeight(), 2, 1, Direction.downUp);
		Placement bot = new Placement(xm, c.getY() - 1, 2, 1, Direction.upDown);
		Placement left = new Placement(c.getX() - 1, ym, 1, 2, Direction.leftRight);
		Placement right = new Placement(c.getX() + c.getWidth(), ym, 1, 2, Direction.rightLeft);
		if(c.getWidth() % 2 == 0){
			top.x--;
			bot.x--;
		}
		if(c.getHeight() % 2 == 0){
			left.y--;
			right.y--;
		}
		return new Placement[]
				{top, bot, left, right};
	}

}
