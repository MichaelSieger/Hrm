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
import de.hswt.hrm.scheme.model.SchemeComponent;
import de.hswt.hrm.scheme.ui.SchemeGridItem;

public class BiologicalDisplay extends RatingDisplay{
	
	public BiologicalDisplay(InspectionSchemeGrid grid, SamplingPoints samplingPoints){
		super(grid, samplingPoints);
	}

	public void update(Collection<BiologicalRating> ratings, Scheme scheme){
		updateSamplingPoints(ratings, scheme);
		InspectionSchemeGrid schemeGrid = getSchemeGrid();
		schemeGrid.clearColors();
		Color[] colors = getColors();
		Collection<SchemeGridItem> items = schemeGrid.getItems();
		for(BiologicalRating rating : ratings){
			int r = rating.getRating();
			Preconditions.checkArgument(r >= 0 && r < colors.length);
			schemeGrid.setColor(findById(items, rating.getComponent()), colors[r]);
		}
	}
	
}
