package de.hswt.hrm.inspection.ui.listener;

import de.hswt.hrm.plant.model.Plant;
import de.hswt.hrm.scheme.model.SchemeComponent;

public interface ComponentSelectionChangedListener {

	public void componentSelectionChanged(SchemeComponent component);

	public void plantChanged(Plant plant);
}
