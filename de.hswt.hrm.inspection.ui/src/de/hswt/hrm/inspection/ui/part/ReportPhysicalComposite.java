package de.hswt.hrm.inspection.ui.part;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import de.hswt.hrm.common.ui.swt.forms.FormUtil;
import de.hswt.hrm.common.ui.swt.layouts.LayoutUtil;
import de.hswt.hrm.common.ui.swt.utils.ContentProposalUtil;
import de.hswt.hrm.component.model.Component;

import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.layout.GridData;

public class ReportPhysicalComposite extends AbstractComponentRatingComposite {

	// TODO remove unused injections, add others as needed
	// TODO use this if it is implemented
	// @Inject
	// private InspectionService inspectionService;

	@Inject
	private IEclipseContext context;

	@Inject
	private IShellProvider shellProvider;

	private FormToolkit formToolkit = new FormToolkit(Display.getDefault());

	private Button nothingRadioButton;
	private Button climateParameterRadioButton;
	private Button photoRadioButton;
	private Button dustRadioButton;

	private Combo commentCombo;

	private List gradeList;
	private List weightList;
	private List list;

	// @TODO remove when example is no longer needed
	private static final String[] items = new String[] { "Alpha", "Beta",
			"gaama", "pie", "alge", "bata" };

	/**
	 * Create the composite.
	 * 
	 * Do not use this constructor when instantiate this composite! It is only
	 * included to make the WindowsBuilder working.
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
	 * 
	 * @param parent
	 * @param style
	 */
	public ReportPhysicalComposite(Composite parent) {
		super(parent, SWT.NONE);
		formToolkit.dispose();
		formToolkit = FormUtil.createToolkit();
	}

	@PostConstruct
	public void createControls() {
		GridLayout gl = new GridLayout(1, false);
		gl.marginBottom = 5;
		gl.marginLeft = 5;
		gl.marginWidth = 0;
		gl.marginHeight = 0;
		setLayout(gl);
		setBackground(getDisplay().getSystemColor(SWT.COLOR_WHITE));

		Composite c = new Composite(this, SWT.NONE);
		c.setLayoutData(LayoutUtil.createFillData());
		c.setLayout(new FillLayout());

		ScrolledComposite sc = new ScrolledComposite(c, SWT.H_SCROLL
				| SWT.V_SCROLL);
		sc.setExpandVertical(true);
		sc.setExpandHorizontal(true);

		Composite composite = new Composite(sc, SWT.None);
		composite.setBackgroundMode(SWT.INHERIT_FORCE);
		gl = new GridLayout(2, true);
		gl.marginWidth = 0;
		gl.marginHeight = 0;
		composite.setLayout(gl);
		sc.setContent(composite);

		Section physicalSection = formToolkit.createSection(composite,
				Section.TITLE_BAR);
		formToolkit.paintBordersFor(physicalSection);
		physicalSection.setText("Physical rating");
		physicalSection.setLayoutData(LayoutUtil.createHorzFillData());
		FormUtil.initSectionColors(physicalSection);

		Section imageSection = formToolkit.createSection(composite,
				Section.TITLE_BAR);
		formToolkit.paintBordersFor(imageSection);
		imageSection.setText("Photos");
		imageSection.setLayoutData(LayoutUtil.createHorzFillData());
		FormUtil.initSectionColors(imageSection);

		Section tagSection = formToolkit.createSection(composite, Section.TITLE_BAR);
		formToolkit.paintBordersFor(tagSection);
		tagSection.setText("Scheme tags");
		tagSection.setLayoutData(LayoutUtil.createHorzFillData(2));
		FormUtil.initSectionColors(tagSection);

		/******************************
		 * physical rating components
		 *****************************/
		Composite physicalComposite = new Composite(physicalSection, SWT.NONE);
		physicalComposite.setBackgroundMode(SWT.INHERIT_DEFAULT);
		formToolkit.adapt(physicalComposite);
		formToolkit.paintBordersFor(physicalComposite);
		gl = new GridLayout(4, false);
		gl.marginWidth = 0;
		physicalComposite.setLayout(gl);
		physicalSection.setClient(physicalComposite);

		Label gradeLabel = new Label(physicalComposite, SWT.NONE);
		gradeLabel.setLayoutData(LayoutUtil.createHorzFillData(2));
		formToolkit.adapt(gradeLabel, true, true);
		gradeLabel.setText("Grade");

		Label weightLabel = new Label(physicalComposite, SWT.NONE);
		weightLabel.setLayoutData(LayoutUtil.createHorzFillData(2));
		formToolkit.adapt(weightLabel, true, true);
		weightLabel.setText("Weight");

		gradeList = new List(physicalComposite, SWT.BORDER);
		gradeList.setLayoutData(LayoutUtil.createHorzFillData(2));
		formToolkit.adapt(gradeList, true, true);
		// 0 = not rated
		for (int i = 0; i < 6; i++) {
			gradeList.add(Integer.toString(i));
		}
		// TODO set 0 selected or grade from db
		gradeList.select(0);

		weightList = new List(physicalComposite, SWT.BORDER);
		weightList.setLayoutData(LayoutUtil.createHorzFillData(2));
		formToolkit.adapt(weightList, true, true);
		for (int i = 1; i <= 6; i++) {
			weightList.add(Integer.toString(i));
		}
		// TODO set category weight or weight from db
		weightList.select(0);

		Label commentLabel = new Label(physicalComposite, SWT.NONE);
		commentLabel.setLayoutData(LayoutUtil.createLeftGridData());
		formToolkit.adapt(commentLabel, true, true);
		commentLabel.setText("Comment");

		commentCombo = new Combo(physicalComposite, SWT.MULTI);
		commentCombo.setLayoutData(LayoutUtil.createHorzFillData(3));
		formToolkit.adapt(commentCombo);
		formToolkit.paintBordersFor(commentCombo);

		// TODO fill with comments from db
		// allow custom inputs
		// use content proposals, like google search, see below

		// example of content proposal
		// TODO replace this with the content of comments
		// set items first!
		commentCombo.setItems(items);
		ContentProposalUtil.enableContentProposal(commentCombo);

		/***************************************
		 * Photo/image section
		 ***************************************/
		Composite imageComposite = new Composite(imageSection, SWT.NONE);
		imageComposite.setBackgroundMode(SWT.INHERIT_DEFAULT);
		formToolkit.adapt(imageComposite);
		formToolkit.paintBordersFor(imageComposite);
		gl = new GridLayout(2, false);
		gl.marginWidth = 0;
		imageComposite.setLayout(gl);
		imageSection.setClient(imageComposite);
		
		list = new List(imageComposite, SWT.BORDER);
		list.setLayoutData(LayoutUtil.createFillData(1, 6));
		formToolkit.adapt(list, true, true);

		Button addPhotoButton = new Button(imageComposite, SWT.NONE);
		formToolkit.adapt(addPhotoButton, true, true);
		addPhotoButton.setText("Add");
		addPhotoButton.setLayoutData(LayoutUtil.createRightGridData());
		addPhotoButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				addPhoto();
			}
		});

		Button removeImageButton = new Button(imageComposite, SWT.NONE);
		formToolkit.adapt(removeImageButton, true, true);
		removeImageButton.setText("Remove");
		removeImageButton.setLayoutData(LayoutUtil.createRightGridData());
		new Label(imageComposite, SWT.NONE);
		new Label(imageComposite, SWT.NONE);
		new Label(imageComposite, SWT.NONE);

		/********************************
		 * tags composite
		 *******************************/
		Composite tagsComposite = new Composite(tagSection, SWT.NONE);
		gl = new GridLayout(2, true);
		gl.marginHeight = 0;
		gl.marginWidth = 0;
		tagsComposite.setLayout(gl);
		tagsComposite.setLayoutData(LayoutUtil.createHorzFillData(2));
		tagsComposite.setBackground(getDisplay().getSystemColor(SWT.COLOR_WHITE));
		tagSection.setClient(tagsComposite);

		nothingRadioButton = new Button(tagsComposite, SWT.RADIO);
		formToolkit.adapt(nothingRadioButton, true, true);
		nothingRadioButton.setLayoutData(LayoutUtil.createHorzFillData());
		nothingRadioButton.setText("Nothing");
		nothingRadioButton.setSelection(true);

		climateParameterRadioButton = new Button(tagsComposite, SWT.RADIO);
		formToolkit.adapt(climateParameterRadioButton, true, true);
		climateParameterRadioButton.setLayoutData(LayoutUtil
				.createHorzFillData());
		climateParameterRadioButton.setText("Climate parameter");

		photoRadioButton = new Button(tagsComposite, SWT.RADIO);
		formToolkit.adapt(photoRadioButton, true, true);
		photoRadioButton.setLayoutData(LayoutUtil.createHorzFillData());
		photoRadioButton.setText("Photo");

		dustRadioButton = new Button(tagsComposite, SWT.RADIO);
		formToolkit.adapt(dustRadioButton, true, true);
		dustRadioButton.setLayoutData(LayoutUtil.createHorzFillData());
		dustRadioButton.setText("Dust concentration determination");

		// DateTime dateFrom = new DateTime(composite, SWT.BORDER | SWT.DATE |
		// SWT.DROP_DOWN);
		// dateFrom.setDate(2007, 0, 1);

		sc.setMinSize(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
	}

	@Override
	public void setSelectedComponent(Component component) {
		// TODO Auto-generated method stub

		
		// GENERAL
		// report title, given at creation time (ReportsOverviewComposite)
		// Button select plant => opens wizard with contacts to select contact
		// Button or dropdown to select overall comment
		// field with date picker => default current
		// field for next inspection with date picker 
		// => radio buttons for 2 or 3 years 
		// => see pictures in Redmine
		
		// PERSONS
		// Button select customer => opens wizard with contacts to select contact
		// Button select requester => opens wizard with contacts to select contact
		// Button select controller => opens wizard with contacts to select contact
		
		// VISUAL
		// import photos
		// combo to select report style
		// selection of plant image
		
		// rating of physical parameter (Temperatur, relative Luftfeuchtigkeit)
		// rating of wasserkeimzahl???
		
		
	}

	private void addPhoto() {
		// TODO request for one or more photos (wizard, dialog?)
	}

	@Override
	public void dispose() {
		formToolkit.dispose();
		super.dispose();
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
