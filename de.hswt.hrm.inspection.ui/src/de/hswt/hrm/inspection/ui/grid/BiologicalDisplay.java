package de.hswt.hrm.inspection.ui.grid;

import java.util.Collection;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;

import com.google.common.base.Preconditions;

import de.hswt.hrm.inspection.model.BiologicalRating;
import de.hswt.hrm.scheme.model.SchemeComponent;

public class BiologicalDisplay extends RatingDisplay{
	
	public BiologicalDisplay(InspectionSchemeGrid grid){
		super(grid);
	}

	public void update(Collection<BiologicalRating> ratings){
		InspectionSchemeGrid schemeGrid = getSchemeGrid();
		schemeGrid.clearColors();
		Color[] colors = getColors();
		for(BiologicalRating rating : ratings){
			int r = rating.getRating();
			Preconditions.checkArgument(r >= 0 && r < colors.length);
			schemeGrid.setColor(rating.getComponent(), colors[r]);
		}
	}
	
}
