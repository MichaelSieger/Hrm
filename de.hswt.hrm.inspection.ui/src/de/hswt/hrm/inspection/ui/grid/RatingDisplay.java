package de.hswt.hrm.inspection.ui.grid;

import java.util.Collection;
import java.util.Iterator;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;

import de.hswt.hrm.scheme.ui.SchemeGridItem;

public class RatingDisplay {

	private final Color[] colors;
	private final InspectionSchemeGrid grid;
	private final SamplingPoints samplingPoints;
	
	public RatingDisplay(InspectionSchemeGrid grid, SamplingPoints samplingPoints){
		this.grid = grid;
		this.samplingPoints = samplingPoints;
		Display device = grid.getControl().getDisplay();
		colors = new Color[]
				{
					new Color(device, 255, 255, 255),
					new Color(device, 151, 248, 71),
					new Color(device, 206, 255, 166),
					new Color(device, 255, 244, 143),
					new Color(device, 244, 146, 238),
					new Color(device, 248, 84, 57)
				};
	}
	
	public InspectionSchemeGrid getSchemeGrid(){
		return grid;
	}
	
	protected Color[] getColors(){
		return colors;
	}
	
	protected static Rectangle getBounds(Collection<SchemeGridItem> c){
		Iterator<SchemeGridItem> it = c.iterator();
		if(!it.hasNext()){
			return new Rectangle(0, 0, 0, 0);
		}
		SchemeGridItem first = it.next();
		int lx = first.getX();
		int ly = first.getY();
		int hx = first.getX() + first.getWidth();
		int hy = first.getY() + first.getHeight();
		while(it.hasNext()){
			SchemeGridItem item = it.next();
			lx = Math.min(lx, item.getX());
			ly = Math.min(ly, item.getY());
			hx = Math.max(hx, item.getX() + item.getWidth());
			hy = Math.max(hy, item.getY() + item.getHeight());
		}
		return new Rectangle(lx, ly, hx - lx + 1, hy - ly + 1);
	}

	public SamplingPoints getSamplingPoints() {
		return samplingPoints;
	}
	
}
