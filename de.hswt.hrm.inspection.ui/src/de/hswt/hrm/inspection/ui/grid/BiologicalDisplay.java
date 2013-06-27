package de.hswt.hrm.inspection.ui.grid;

import java.util.Collection;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;

import com.google.common.base.Preconditions;

import de.hswt.hrm.inspection.model.BiologicalRating;
import de.hswt.hrm.scheme.model.SchemeComponent;

public class BiologicalDisplay {
	
	private final InspectionSchemeGrid schemeGrid;
	private final Color[] colors;
	
	public BiologicalDisplay(InspectionSchemeGrid schemeGrid){
		this.schemeGrid = schemeGrid;
		Device device = schemeGrid.getControl().getDisplay();
		colors = new Color[]
				{
					new Color(device, 0, 0, 0),
					new Color(device, 255, 0, 0),
					new Color(device, 255, 0, 0),
					new Color(device, 255, 255, 0),
					new Color(device, 255, 255, 0),
					new Color(device, 0, 255, 0)
				};
	}

	public void update(Collection<BiologicalRating> ratings){
		schemeGrid.clearColors();
		for(BiologicalRating rating : ratings){
			//TODO
			SchemeComponent c = null;
			int r = rating.getRating();
			Preconditions.checkArgument(r >= 0 && r < colors.length);
			schemeGrid.setColor(c, colors[r]);
		}
	}
	
}
