package de.hswt.hrm.inspection.ui.grid;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

public class RatingDisplay {

	private final Color[] colors;
	private final InspectionSchemeGrid grid;
	
	public RatingDisplay(InspectionSchemeGrid grid){
		this.grid = grid;
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
	
}
