package de.hswt.hrm.inspection.ui.grid;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

public class RatingDisplay {

	private final Color[] colors;
	
	public RatingDisplay(Display device){
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
	
	protected Color[] getColors(){
		return colors;
	}
	
}
