package de.hswt.hrm.inspection.ui.part;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import de.hswt.hrm.component.model.Component;

public class ReportBiologicalComposite extends AbstractComponentRatingComposite {

	// TODO remove unused injections, add others as needed
	// TODO use this if it is implemented
//	@Inject
//	private InspectionService inspectionService;

	@Inject
	private IEclipseContext context;
	
	@Inject
	private IShellProvider shellProvider;

	/**
	 * Do not use this constructor when instantiate this composite!
	 * It is only included to make the WindowsBuilder working.
	 * 
	 * @param parent
	 * @param style
	 */
	private ReportBiologicalComposite(Composite parent, int style) {
		super(parent, SWT.NONE);
		createControls();
	}

	/**
	 * Create the composite.
	 * @param parent
	 */
	public ReportBiologicalComposite(Composite parent) {
		super(parent, SWT.NONE);
	}

	@PostConstruct
	public void createControls() {
		
	}

	@Override
	public void setSelectedComponent(Component component) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
