package de.hswt.hrm.inspection.ui.part;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.jface.viewers.CellEditor.LayoutData;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import de.hswt.hrm.common.ui.swt.forms.FormUtil;
import de.hswt.hrm.common.ui.swt.layouts.LayoutUtil;
import de.hswt.hrm.component.model.Component;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.layout.GridData;

public class ReportPhysicalComposite extends AbstractComponentRatingComposite {

	// TODO remove unused injections, add others as needed
	// TODO use this if it is implemented
//	@Inject
//	private InspectionService inspectionService;

	@Inject
	private IEclipseContext context;
	
	@Inject
	private IShellProvider shellProvider;
	private final FormToolkit formToolkit = new FormToolkit(Display.getDefault());

	/**
	 * Create the composite. 
	 * 
	 * Do not use this constructor when instantiate this composite!
	 * It is only included to make the WindowsBuilder working.
	 * 
	 * @param parent
	 * @param style
	 */
	private ReportPhysicalComposite(Composite parent, int style) {
		super(parent, SWT.NONE);
		createControls();
	}

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public ReportPhysicalComposite(Composite parent) {
		super(parent, SWT.NONE);

	}

	@PostConstruct
	public void createControls() {
		GridLayout gridLayout = new GridLayout(1, false);
		gridLayout.marginBottom = 5;
		gridLayout.marginLeft = 5;
		gridLayout.marginWidth = 0;
		gridLayout.marginHeight = 0;
		setLayout(gridLayout);
		setBackground(getDisplay().getSystemColor(SWT.COLOR_WHITE));
		
		Section physicalSection = formToolkit.createSection(this, Section.TITLE_BAR);
		formToolkit.paintBordersFor(physicalSection);
		physicalSection.setText("Physical rating");
		physicalSection.setLayoutData(LayoutUtil.createFillData());
		FormUtil.initSectionColors(physicalSection);
		
		Composite composite = new Composite(physicalSection, SWT.NONE);
		formToolkit.adapt(composite);
		formToolkit.paintBordersFor(composite);
		physicalSection.setClient(composite);
		GridLayout gl_composite = new GridLayout(2, false);
		gl_composite.horizontalSpacing = 0;
		composite.setLayout(gl_composite);
		
		Label gradeLabel = new Label(composite, SWT.NONE);
		gradeLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		formToolkit.adapt(gradeLabel, true, true);
		gradeLabel.setText("Grade");
		
		Combo gradeCombo = new Combo(composite, SWT.SIMPLE | SWT.DROP_DOWN | SWT.READ_ONLY);
		gradeCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		formToolkit.adapt(gradeCombo);
		formToolkit.paintBordersFor(gradeCombo);
		gradeCombo.add("0"); // not rated
		gradeCombo.add("1");
		gradeCombo.add("2");
		gradeCombo.add("3");
		gradeCombo.add("4");
		gradeCombo.add("5");
		// TODO set 0 selected or grade from db
		gradeCombo.select(0);
		
		Label weightLabel = new Label(composite, SWT.NONE);
		weightLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		formToolkit.adapt(weightLabel, true, true);
		weightLabel.setText("Weight");
		
		Combo weightCombo = new Combo(composite, SWT.SIMPLE | SWT.DROP_DOWN | SWT.READ_ONLY);
		weightCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		formToolkit.adapt(weightCombo);
		formToolkit.paintBordersFor(weightCombo);
		for (int i = 1; i <= 10; i++) {
			weightCombo.add(Integer.toString(i));
		}
		// TODO set category weight or weight from db
		gradeCombo.select(0);
		
		Label commentLabel = new Label(composite, SWT.NONE);
		commentLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		formToolkit.adapt(commentLabel, true, true);
		commentLabel.setText("Comment");
		
		Combo commentCombo = new Combo(composite, SWT.NONE);
		commentCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		formToolkit.adapt(commentCombo);
		formToolkit.paintBordersFor(commentCombo);
		// TODO fill with comments from db
		// allow custom inputs
		// try to use autofill, like google search
		
		// TODO for me
		// add images
		// change first combos to lists
		// selection of arrows in the grid
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
