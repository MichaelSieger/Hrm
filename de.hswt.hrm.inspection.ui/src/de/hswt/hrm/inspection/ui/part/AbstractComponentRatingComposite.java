package de.hswt.hrm.inspection.ui.part;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

import de.hswt.hrm.component.model.Component;

public abstract class AbstractComponentRatingComposite extends Composite {

	public AbstractComponentRatingComposite(Composite parent, int style) {
		super(parent, style);
		setLayout(new FillLayout());
	}

	public abstract void setSelectedComponent(Component component);
	
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
