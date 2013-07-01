package de.hswt.hrm.inspection.ui.part;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

import de.hswt.hrm.component.model.Component;
import de.hswt.hrm.inspection.model.Inspection;
import de.hswt.hrm.plant.model.Plant;
import de.hswt.hrm.scheme.model.SchemeComponent;

public abstract class AbstractComponentRatingComposite extends Composite {

	public AbstractComponentRatingComposite(Composite parent, int style) {
		super(parent, style);
		setLayout(new FillLayout());
	}

//	public abstract void setSelectedComponent(Component component);

	protected abstract void inspectionChanged(Inspection inspection);

	protected abstract void inspectionComponentSelectionChanged(SchemeComponent component);

	protected abstract void plantChanged(Plant plant);

	protected abstract void saveValues();
	
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
	
}
