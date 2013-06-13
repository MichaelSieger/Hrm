package de.hswt.hrm.inspection.ui.part;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

import de.hswt.hrm.common.ui.swt.layouts.LayoutUtil;
import de.hswt.hrm.component.model.Component;

public class ReportGeneralComposite extends AbstractComponentRatingComposite {

	// TODO remove unused injections, add others as needed
	// TODO use this if it is implemented
//	@Inject
//	private InspectionService inspectionService;

	@Inject
	private IEclipseContext context;
	
	@Inject
	private IShellProvider shellProvider;
	
	private FormToolkit toolkit = new FormToolkit(Display.getDefault());

	/**
	 * Do not use this constructor when instantiate this composite!
	 * It is only included to make the WindowsBuilder working.
	 * 
	 * @param parent
	 * @param style
	 */
	private ReportGeneralComposite(Composite parent, int style) {
		super(parent, SWT.NONE);
		createControls();
	}

	/**
	 * Create the composite.
	 * @param parent
	 */
	public ReportGeneralComposite(Composite parent) {
		super(parent, SWT.NONE);
	}

	@PostConstruct
	public void createControls() {
		setLayout(new FillLayout());
		
		Composite generalComposite = new Composite(this, SWT.NONE);
		generalComposite.setBackgroundMode(SWT.INHERIT_FORCE);
		toolkit.paintBordersFor(generalComposite);
		generalComposite.setLayout(new GridLayout(1, false));
		
		Section personsSection = toolkit.createSection(generalComposite, Section.TITLE_BAR);
		personsSection.setLayoutData(LayoutUtil.createFillData());
		toolkit.paintBordersFor(personsSection);
		personsSection.setText("Persons");
		
		Composite personsComposite = new Composite(personsSection, SWT.NONE);
		toolkit.adapt(personsComposite);
		toolkit.paintBordersFor(personsComposite);
		personsSection.setClient(personsComposite);
		personsComposite.setLayout(new GridLayout(2, false));
		
		Button selectCustomer = new Button(personsComposite, SWT.NONE);
		selectCustomer.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		toolkit.adapt(selectCustomer, true, true);
		selectCustomer.setText("Select Customer ");
		
		Label lblName = new Label(personsComposite, SWT.NONE);
		toolkit.adapt(lblName, true, true);
		lblName.setText("Name");
		
		// report title, given at creation time (ReportsOverviewComposite)
		
		// Button select customer => opens wizard with contacts to select contact
		// Button select requester => opens wizard with contacts to select contact
		// Button select controller => opens wizard with contacts to select contact
		
		// Button select plant => opens wizard with contacts to select contact
		
		// Button or dropdown to select overall comment

		// field with date picker => default current
		
		// field for next inspection with date picker 
		// => radio buttons for 2 or 3 years 
		// => see pictures in Redmine
		
		// combo to select report style
		
		// rating of physical parameter (Temperatur, relative Luftfeuchtigkeit)
		// rating of wasserkeimzahl???
		
		// selection of plant image
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
