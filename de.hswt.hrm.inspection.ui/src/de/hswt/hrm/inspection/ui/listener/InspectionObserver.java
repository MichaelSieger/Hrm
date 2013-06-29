package de.hswt.hrm.inspection.ui.listener;

import de.hswt.hrm.inspection.model.Inspection;
import de.hswt.hrm.plant.model.Plant;
import de.hswt.hrm.scheme.model.SchemeComponent;

public interface InspectionObserver {

	public void inspectionChanged(Inspection inspection);
	
	public void inspectionComponentSelectionChanged(SchemeComponent component);

	public void plantChanged(Plant plant);
	
}
