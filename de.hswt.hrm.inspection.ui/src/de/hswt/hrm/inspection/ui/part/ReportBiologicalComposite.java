package de.hswt.hrm.inspection.ui.part;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

import de.hswt.hrm.common.ui.swt.forms.FormUtil;
import de.hswt.hrm.common.ui.swt.layouts.LayoutUtil;
import de.hswt.hrm.common.ui.swt.utils.ContentProposalUtil;
import de.hswt.hrm.component.model.Component;

public class ReportBiologicalComposite extends AbstractComponentRatingComposite {

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
	private Button airGermsRadioButton;
	private Button waterAnalysisRadioButton;
	private Button contactCultureRadioButton;

	private List contactGgradeList;
	private List contactWeightList;
	private Combo contactCommentCombo;

	private List airGradeList;
	private List airWeightList;
	private Combo airCommentCombo;

	// @TODO remove when example is no longer needed
	private static final String[] items = new String[] { "Alpha", "Beta",
			"gaama", "pie", "alge", "bata" };

	/**
	 * Do not use this constructor when instantiate this composite! It is only
	 * included to make the WindowsBuilder working.
	 * 
	 * @param parent
	 * @param style
	 */
	private ReportBiologicalComposite(Composite parent, int style) {
		super(parent, SWT.NONE);
		setBackgroundMode(SWT.INHERIT_FORCE);
		createControls();
	}

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 */
	public ReportBiologicalComposite(Composite parent) {
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
		gl = new GridLayout(2, false);
		gl.marginWidth = 0;
		gl.marginHeight = 0;
		composite.setLayout(gl);
		sc.setContent(composite);

		Section contactCultureSection = formToolkit.createSection(composite,
				Section.TITLE_BAR);
		formToolkit.paintBordersFor(contactCultureSection);
		contactCultureSection.setText("Contact cultures");
		contactCultureSection.setLayoutData(LayoutUtil.createHorzFillData());
		FormUtil.initSectionColors(contactCultureSection);

		Section airSection = formToolkit.createSection(composite, Section.TITLE_BAR);
		formToolkit.paintBordersFor(airSection);
		airSection.setText("Air germs concentration");
		airSection.setLayoutData(LayoutUtil.createHorzFillData());
		FormUtil.initSectionColors(airSection);

		Section tagSection = formToolkit.createSection(composite, Section.TITLE_BAR);
		formToolkit.paintBordersFor(tagSection);
		tagSection.setText("Scheme tags");
		tagSection.setLayoutData(LayoutUtil.createHorzFillData(2));
		FormUtil.initSectionColors(tagSection);

		/*****************************
		 * contact culture components
		 ****************************/
		Composite contactCultureComposite = new Composite(
				contactCultureSection, SWT.NONE);
		contactCultureComposite.setBackgroundMode(SWT.INHERIT_DEFAULT);
		formToolkit.adapt(contactCultureComposite);
		formToolkit.paintBordersFor(contactCultureComposite);
		gl = new GridLayout(4, false);
		gl.marginWidth = 0;
		contactCultureComposite.setLayout(gl);
		contactCultureSection.setClient(contactCultureComposite);
		
		Label contactGradeLabel = new Label(contactCultureComposite, SWT.NONE);
		contactGradeLabel.setLayoutData(LayoutUtil.createHorzFillData(2));
		formToolkit.adapt(contactGradeLabel, true, true);
		contactGradeLabel.setText("Grade");

		Label contactWeightLabel = new Label(contactCultureComposite, SWT.NONE);
		contactWeightLabel.setLayoutData(LayoutUtil.createHorzFillData(2));
		formToolkit.adapt(contactWeightLabel, true, true);
		contactWeightLabel.setText("Weight");

		contactGgradeList = new List(contactCultureComposite, SWT.BORDER);
		contactGgradeList.setLayoutData(LayoutUtil.createHorzFillData(2));
		formToolkit.adapt(contactGgradeList, true, true);
		// 0 = not rated
		for (int i = 0; i < 6; i++) {
			contactGgradeList.add(Integer.toString(i));
		}
		// TODO set 0 selected or grade from db
		contactGgradeList.select(0);

		contactWeightList = new List(contactCultureComposite, SWT.BORDER);
		contactWeightList.setLayoutData(LayoutUtil.createHorzFillData(2));
		formToolkit.adapt(contactWeightList, true, true);
		for (int i = 1; i <= 6; i++) {
			contactWeightList.add(Integer.toString(i));
		}
		// TODO set category weight or weight from db
		contactWeightList.select(0);

		Label contactCommentLabel = new Label(contactCultureComposite, SWT.NONE);
		contactCommentLabel.setLayoutData(LayoutUtil.createLeftGridData());
		formToolkit.adapt(contactCommentLabel, true, true);
		contactCommentLabel.setText("Comment");

		contactCommentCombo = new Combo(contactCultureComposite, SWT.NONE);
		contactCommentCombo.setLayoutData(LayoutUtil.createHorzFillData(3));
		formToolkit.adapt(contactCommentCombo);
		formToolkit.paintBordersFor(contactCommentCombo);

		// TODO fill with comments from db
		// allow custom inputs
		// use content proposals, like google search, see below

		// example of content proposal
		// TODO replace this with the content of comments
		// set items first!
		contactCommentCombo.setItems(items);
		ContentProposalUtil.enableContentProposal(contactCommentCombo);

		/*************************************
		 * air germs concentration components
		 ************************************/
		Composite airGermsComposite = new Composite(airSection, SWT.NONE);
		airGermsComposite.setBackgroundMode(SWT.INHERIT_DEFAULT);
		formToolkit.adapt(airGermsComposite);
		formToolkit.paintBordersFor(airGermsComposite);
		gl = new GridLayout(4, false);
		gl.marginWidth = 0;
		airGermsComposite.setLayout(gl);
		airSection.setClient(airGermsComposite);

		Label airGradeLabel = new Label(airGermsComposite, SWT.NONE);
		airGradeLabel.setLayoutData(LayoutUtil.createHorzFillData(2));
		formToolkit.adapt(contactGradeLabel, true, true);
		airGradeLabel.setText("Grade");

		Label airWeightLabel = new Label(airGermsComposite, SWT.NONE);
		airWeightLabel.setLayoutData(LayoutUtil.createHorzFillData(2));
		formToolkit.adapt(contactWeightLabel, true, true);
		airWeightLabel.setText("Weight");

		airGradeList = new List(airGermsComposite, SWT.BORDER);
		airGradeList.setLayoutData(LayoutUtil.createHorzFillData(2));
		formToolkit.adapt(airGradeList, true, true);
		// 0 = not rated
		for (int i = 0; i < 6; i++) {
			airGradeList.add(Integer.toString(i));
		}
		// TODO set 0 selected or grade from db
		airGradeList.select(0);

		airWeightList = new List(airGermsComposite, SWT.BORDER);
		airWeightList.setLayoutData(LayoutUtil.createHorzFillData(2));
		formToolkit.adapt(airWeightList, true, true);
		for (int i = 1; i <= 6; i++) {
			airWeightList.add(Integer.toString(i));
		}
		// TODO set category weight or weight from db
		airWeightList.select(0);

		Label airCommentLabel = new Label(airGermsComposite, SWT.NONE);
		airCommentLabel.setLayoutData(LayoutUtil.createLeftGridData());
		formToolkit.adapt(airCommentLabel, true, true);
		airCommentLabel.setText("Comment");

		airCommentCombo = new Combo(airGermsComposite, SWT.NONE);
		airCommentCombo.setLayoutData(LayoutUtil.createHorzFillData(3));
		formToolkit.adapt(airCommentCombo);
		formToolkit.paintBordersFor(airCommentCombo);

		// TODO fill with comments from db
		// allow custom inputs
		// use content proposals, like google search, see below

		// example of content proposal
		// TODO replace this with the content of comments
		// set items first!
		airCommentCombo.setItems(items);
		ContentProposalUtil.enableContentProposal(airCommentCombo);

		/********************************
		 * tags components
		 *******************************/

		Composite tagsComposite = new Composite(tagSection, SWT.NONE);
		gl = new GridLayout(2, true);
		gl.marginHeight = 0;
		gl.marginWidth = 0;
		tagsComposite.setLayout(gl);
		tagsComposite.setLayoutData(LayoutUtil.createHorzFillData(4));
		tagsComposite.setBackground(getDisplay().getSystemColor(SWT.COLOR_WHITE));
		tagSection.setClient(tagsComposite);

		nothingRadioButton = new Button(tagsComposite, SWT.RADIO);
		formToolkit.adapt(nothingRadioButton, true, true);
		nothingRadioButton.setLayoutData(LayoutUtil.createHorzFillData());
		nothingRadioButton.setText("Nothing");
		nothingRadioButton.setSelection(true);

		airGermsRadioButton = new Button(tagsComposite, SWT.RADIO);
		formToolkit.adapt(airGermsRadioButton, true, true);
		airGermsRadioButton.setLayoutData(LayoutUtil
				.createHorzFillData());
		airGermsRadioButton.setText("Air germs meassurement");

		waterAnalysisRadioButton = new Button(tagsComposite, SWT.RADIO);
		formToolkit.adapt(waterAnalysisRadioButton, true, true);
		waterAnalysisRadioButton.setLayoutData(LayoutUtil.createHorzFillData());
		waterAnalysisRadioButton.setText("Water analysis / Legionella");

		contactCultureRadioButton = new Button(tagsComposite, SWT.RADIO);
		formToolkit.adapt(contactCultureRadioButton, true, true);
		contactCultureRadioButton.setLayoutData(LayoutUtil.createHorzFillData());
		contactCultureRadioButton.setText("Contact culture");

		sc.setMinSize(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
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
