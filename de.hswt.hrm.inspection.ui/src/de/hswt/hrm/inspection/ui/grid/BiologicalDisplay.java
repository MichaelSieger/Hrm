package de.hswt.hrm.inspection.ui.grid;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.swt.graphics.Color;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;

import de.hswt.hrm.inspection.model.BiologicalRating;
import de.hswt.hrm.inspection.model.SamplingPointType;
import de.hswt.hrm.scheme.ui.SchemeGridItem;

public class BiologicalDisplay extends RatingDisplay{
	
	private List<SchemeGridItem> samplePoints = new ArrayList<>();
	
	public BiologicalDisplay(InspectionSchemeGrid grid, SamplingPoints samplingPoints){
		super(grid, samplingPoints);
	}

	public void update(Collection<BiologicalRating> ratings){
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
			if(samplingPoint.isPresent()){
				samplePoints.add(SamplingPointPosition.getSamplingPoint(
									getSamplingPoints(), samplingPoint.get(), null, rating.getComponent()));
			}
		}
	}
	
}
