package de.hswt.hrm.inspection.ui.grid;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Rectangle;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;

import de.hswt.hrm.inspection.model.BiologicalRating;
import de.hswt.hrm.inspection.model.SamplingPointType;
import de.hswt.hrm.inspection.ui.grid.SamplingPointPosition.Placement;
import de.hswt.hrm.scheme.model.Scheme;
import de.hswt.hrm.scheme.ui.SchemeGridItem;

public class BiologicalDisplay extends RatingDisplay{
	
	private List<SchemeGridItem> samplePoints = new ArrayList<>();
	
	public BiologicalDisplay(InspectionSchemeGrid grid, SamplingPoints samplingPoints){
		super(grid, samplingPoints);
	}

	public void update(Collection<BiologicalRating> ratings, Scheme scheme){
		InspectionSchemeGrid schemeGrid = getSchemeGrid();
		schemeGrid.removeAll(samplePoints);
		samplePoints.clear();
		schemeGrid.clearColors();
		Color[] colors = getColors();
		for(BiologicalRating rating : ratings){
			int r = rating.getRating();
			Preconditions.checkArgument(r >= 0 && r < colors.length);
			schemeGrid.setColor(rating.getComponent(), colors[r]);
			Optional<SamplingPointType> samplingPoint = rating.getSamplingPointType();
			if(samplingPoint.isPresent() && samplingPoint.get() != SamplingPointType.none){
				Placement place = SamplingPointPosition.getPlacement(scheme, rating.getComponent());
				if(place != null){
					if(place.x < 0){
						int moveX = -place.x;
						schemeGrid.move(moveX, 0);
						place.x += moveX;
						moveSamplePoints(moveX, 0);
					}
					if(place.y < 0){
						int moveY = -place.y;
						schemeGrid.move(0, moveY);
						place.y += moveY;
						moveSamplePoints(0, moveY);
					}
					samplePoints.add(SamplingPointPosition.getSamplingPoint(
										getSamplingPoints(), samplingPoint.get(), scheme, rating.getComponent(), place));
				}
			}
		}
		schemeGrid.addAll(samplePoints);
	}
	
	private void moveSamplePoints(int x, int y){
		for(SchemeGridItem item : samplePoints){
			item.setX(item.getX() + x);
			item.setY(item.getY() + y);
		}
	}
	
}
