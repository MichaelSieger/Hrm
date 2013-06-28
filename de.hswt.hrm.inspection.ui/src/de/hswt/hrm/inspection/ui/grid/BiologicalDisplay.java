package de.hswt.hrm.inspection.ui.grid;

import java.util.Collection;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;

import com.google.common.base.Preconditions;

import de.hswt.hrm.inspection.model.BiologicalRating;
import de.hswt.hrm.scheme.model.SchemeComponent;

public class BiologicalDisplay extends RatingDisplay{
	
	private final InspectionSchemeGrid schemeGrid;
	
	
	public BiologicalDisplay(InspectionSchemeGrid schemeGrid){
		super(schemeGrid.getControl().getDisplay());
		this.schemeGrid = schemeGrid;
	}

	public void update(Collection<BiologicalRating> ratings){
		schemeGrid.clearColors();
		Color[] colors = getColors();
		for(BiologicalRating rating : ratings){
			//TODO
			SchemeComponent c = null;
			int r = rating.getRating();
			Preconditions.checkArgument(r >= 0 && r < colors.length);
			schemeGrid.setColor(c, colors[r]);
		}
	}
	
}
