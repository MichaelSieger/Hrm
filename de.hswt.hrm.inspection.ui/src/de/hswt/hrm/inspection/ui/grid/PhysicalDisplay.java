package de.hswt.hrm.inspection.ui.grid;

import java.util.Collection;

import org.eclipse.swt.graphics.Color;

import com.google.common.base.Preconditions;

import de.hswt.hrm.inspection.model.BiologicalRating;
import de.hswt.hrm.inspection.model.PhysicalRating;
import de.hswt.hrm.scheme.model.Scheme;


public class PhysicalDisplay extends RatingDisplay{

	public PhysicalDisplay(InspectionSchemeGrid grid, SamplingPoints samplingPoints) {
		super(grid, samplingPoints);
	}
	
	public void update(Collection<PhysicalRating> ratings, Scheme scheme){
		updateSamplingPoints(ratings, scheme);
		InspectionSchemeGrid grid = getSchemeGrid();
		Color[] colors = getColors();
		for(PhysicalRating r : ratings){
			int grade = r.getRating();
			Preconditions.checkArgument(grade >= 0 && grade < colors.length);
			grid.setColor(r.getComponent(), colors[grade]);
		}
	}

}
