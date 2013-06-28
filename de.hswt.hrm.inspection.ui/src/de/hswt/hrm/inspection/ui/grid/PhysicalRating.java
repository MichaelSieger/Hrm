package de.hswt.hrm.inspection.ui.grid;

import java.util.Collection;

import org.eclipse.swt.graphics.Color;

import com.google.common.base.Preconditions;

import de.hswt.hrm.inspection.model.BiologicalRating;
import de.hswt.hrm.scheme.model.SchemeComponent;


public class PhysicalRating extends RatingDisplay{

	public PhysicalRating(InspectionSchemeGrid grid) {
		super(grid);
	}
	
	public void update(Collection<BiologicalRating> ratings){
		InspectionSchemeGrid grid = getSchemeGrid();
		Color[] colors = getColors();
		for(BiologicalRating r : ratings){
			int grade = r.getRating();
			Preconditions.checkArgument(grade >= 0 && grade < colors.length);
			grid.setColor(r.getComponent(), colors[grade]);
		}
	}

}
