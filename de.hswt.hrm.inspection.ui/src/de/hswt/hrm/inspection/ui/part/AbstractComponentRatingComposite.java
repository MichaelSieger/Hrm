package de.hswt.hrm.inspection.ui.part;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

import de.hswt.hrm.component.model.Component;
import de.hswt.hrm.inspection.ui.listener.ComponentSelectionChangedListener;
import de.hswt.hrm.inspection.ui.listener.InspectionObserver;
import de.hswt.hrm.plant.model.Plant;
import de.hswt.hrm.scheme.model.SchemeComponent;

public abstract class AbstractComponentRatingComposite extends Composite implements InspectionObserver {

	private List<ComponentSelectionChangedListener> componentListener;
	
	public AbstractComponentRatingComposite(Composite parent, int style) {
		super(parent, style);
		setLayout(new FillLayout());
		componentListener = new ArrayList<>();
	}

	public abstract void setSelectedComponent(Component component);

	protected void fireComponentSelectionChanged(SchemeComponent component) {
		for (ComponentSelectionChangedListener listener : componentListener)  {
			listener.componentSelectionChanged(component);
		}
	}

	protected void firePlantChanged(Plant plant) {
		for (ComponentSelectionChangedListener listener : componentListener)  {
			listener.plantChanged(plant);
		}
	}

	protected void addComponentSelectionListener(ComponentSelectionChangedListener listener) {
		componentListener.add(listener);
	}

	protected void removeComponentSelectionListener(ComponentSelectionChangedListener listener) {
		componentListener.remove(listener);
	}
	
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
	
}
