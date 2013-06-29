package de.hswt.hrm.inspection.ui.grid;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;

import com.google.common.base.Optional;

import de.hswt.hrm.inspection.model.Rating;
import de.hswt.hrm.inspection.model.SamplingPointType;
import de.hswt.hrm.inspection.ui.grid.SamplingPointPosition.Placement;
import de.hswt.hrm.scheme.model.Scheme;
import de.hswt.hrm.scheme.model.SchemeComponent;
import de.hswt.hrm.scheme.ui.SchemeGridItem;

public class RatingDisplay {

	private final Color[] colors;
	private final InspectionSchemeGrid grid;
	private final SamplingPoints samplingPoints;
	private List<SchemeGridItem> samplePointItems = new ArrayList<>();
	
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
	
	public void updateSamplingPoints(Collection<? extends Rating> ratings, Scheme scheme){
		grid.removeAll(samplePointItems);
		samplePointItems.clear();
		for(Rating rating : ratings){
			Optional<SamplingPointType> samplingPoint = rating.getSamplingPointType();
			if(samplingPoint.isPresent() && samplingPoint.get() != SamplingPointType.none){
				Collection<SchemeGridItem> items = grid.getItems();
				Placement place = SamplingPointPosition.getPlacement(items, findById(items, rating.getComponent()));
				if(place != null){
					if(place.x < 0){
						int moveX = -place.x;
						grid.move(moveX, 0);
						place.x += moveX;
						moveSamplePoints(moveX, 0);
					}
					if(place.y < 0){
						int moveY = -place.y;
						grid.move(0, moveY);
						place.y += moveY;
						moveSamplePoints(0, moveY);
					}
					samplePointItems.add(SamplingPointPosition.getSamplingPoint(
										getSamplingPoints(), samplingPoint.get(), scheme, rating.getComponent(), place));
				}
			}
		}
		grid.addAll(samplePointItems);
	}
	
	protected SchemeComponent findById(Collection<SchemeGridItem> items, SchemeComponent c){
		for(SchemeGridItem i : items){
			if(i.asSchemeComponent().getId() == c.getId()){
				return i.asSchemeComponent();
			}
		}
		throw new RuntimeException("Internal Error");
	}
	
	private void moveSamplePoints(int x, int y){
		for(SchemeGridItem item : samplePointItems){
			item.setX(item.getX() + x);
			item.setY(item.getY() + y);
		}
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
