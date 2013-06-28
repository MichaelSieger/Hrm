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
					new Color(device, 0, 0, 0),
					new Color(device, 255, 0, 0),
					new Color(device, 255, 0, 0),
					new Color(device, 255, 255, 0),
					new Color(device, 255, 255, 0),
					new Color(device, 0, 255, 0)
				};
	}
	
	public InspectionSchemeGrid getSchemeGrid(){
		return grid;
	}
	
	protected Color[] getColors(){
		return colors;
	}
	
}
