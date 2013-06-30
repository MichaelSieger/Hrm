package de.hswt.hrm.inspection.ui.part;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hswt.hrm.inspection.model.BiologicalRating;
import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.observer.Observable;
import de.hswt.hrm.common.observer.Observer;
import de.hswt.hrm.common.ui.swt.forms.FormUtil;
import de.hswt.hrm.common.ui.swt.layouts.LayoutUtil;
import de.hswt.hrm.common.ui.swt.utils.ContentProposalUtil;
import de.hswt.hrm.component.model.Component;
import de.hswt.hrm.i18n.I18n;
import de.hswt.hrm.i18n.I18nFactory;
import de.hswt.hrm.inspection.model.Inspection;
import de.hswt.hrm.inspection.model.PhysicalRating;
import de.hswt.hrm.inspection.model.SamplingPointType;
import de.hswt.hrm.inspection.service.InspectionService;
import de.hswt.hrm.misc.comment.model.Comment;
import de.hswt.hrm.misc.comment.service.CommentService;
import de.hswt.hrm.plant.model.Plant;
import de.hswt.hrm.scheme.model.Scheme;
import de.hswt.hrm.scheme.model.SchemeComponent;

public class ReportBiologicalComposite extends AbstractComponentRatingComposite {

	@Inject
	private InspectionService inspectionService;

	@Inject
	private IEclipseContext context;

	@Inject
	private IShellProvider shellProvider;

	@Inject
	private CommentService commentService;

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

	private Text contactCultureConcentrationText;

	private Text airGermsConcentrationText;
	
	private Collection<BiologicalRating> ratings;
	
	private static final Logger LOG = LoggerFactory
			.getLogger(ReportBiologicalComposite.class);
	
	private static final I18n I18N = I18nFactory.getI18n(ReportBiologicalComposite.class);

	private SchemeComponent currentSchemeComponent;
	
	private Inspection inspection;
	
	private final Observable<Integer> selectedGrade = new Observable<>();
	private final Observable<SamplingPointType> samplePointType = new Observable<>();

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
		contactCultureSection.setText(I18N.tr("Contact cultures"));
		contactCultureSection.setLayoutData(LayoutUtil.createHorzFillData());
		FormUtil.initSectionColors(contactCultureSection);

		Section airSection = formToolkit.createSection(composite,
				Section.TITLE_BAR);
		formToolkit.paintBordersFor(airSection);
		airSection.setText(I18N.tr("Air germs concentration"));
		airSection.setLayoutData(LayoutUtil.createHorzFillData());
		FormUtil.initSectionColors(airSection);

		Section tagSection = formToolkit.createSection(composite,
				Section.TITLE_BAR);
		formToolkit.paintBordersFor(tagSection);
		tagSection.setText(I18N.tr("Scheme tags"));
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
		gl.marginWidth = 1;
		contactCultureComposite.setLayout(gl);
		contactCultureSection.setClient(contactCultureComposite);

		Label contactGradeLabel = new Label(contactCultureComposite, SWT.NONE);
		contactGradeLabel.setLayoutData(LayoutUtil.createHorzFillData(2));
		formToolkit.adapt(contactGradeLabel, true, true);
		contactGradeLabel.setText(I18N.tr("Grade"));

		Label contactWeightLabel = new Label(contactCultureComposite, SWT.NONE);
		contactWeightLabel.setLayoutData(LayoutUtil.createHorzFillData(2));
		formToolkit.adapt(contactWeightLabel, true, true);
		contactWeightLabel.setText(I18N.tr("Weight"));

		contactGgradeList = new List(contactCultureComposite, SWT.BORDER);
		contactGgradeList.setLayoutData(LayoutUtil.createHorzFillData(2));
		formToolkit.adapt(contactGgradeList, true, true);
		// 0 = not rated
		for (int i = 0; i < 6; i++) {
			contactGgradeList.add(Integer.toString(i));
		}
		// TODO set 0 selected or grade from db
		contactGgradeList.select(0);
		contactGgradeList.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				int sel = contactGgradeList.getSelectionIndex();
				if (sel != -1) {
					selectedGrade.set(sel);
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

		contactWeightList = new List(contactCultureComposite, SWT.BORDER);
		contactWeightList.setLayoutData(LayoutUtil.createHorzFillData(2));
		formToolkit.adapt(contactWeightList, true, true);
		for (int i = 1; i <= 6; i++) {
			contactWeightList.add(Integer.toString(i));
		}
		// TODO set category weight or weight from db
		contactWeightList.select(0);

		Label contactConcentrationLabel = new Label(contactCultureComposite,
				SWT.NONE);
		contactConcentrationLabel
				.setLayoutData(LayoutUtil.createLeftGridData());
		formToolkit.adapt(contactConcentrationLabel, true, true);
		contactConcentrationLabel.setText(I18N.tr("Concentration"));

		contactCultureConcentrationText = formToolkit.createText(
				contactCultureComposite, "", SWT.NONE);
		contactCultureConcentrationText.setLayoutData(LayoutUtil
				.createHorzCenteredFillData(3));
		// TODO init with value

		Label contactCommentLabel = new Label(contactCultureComposite, SWT.NONE);
		contactCommentLabel.setLayoutData(LayoutUtil.createLeftGridData());
		formToolkit.adapt(contactCommentLabel, true, true);
		contactCommentLabel.setText(I18N.tr("Comment"));

		contactCommentCombo = new Combo(contactCultureComposite, SWT.NONE);
		contactCommentCombo.setLayoutData(LayoutUtil.createHorzFillData(3));
		formToolkit.adapt(contactCommentCombo);
		formToolkit.paintBordersFor(contactCommentCombo);

		initCommentAutoCompletion(contactCommentCombo);
		

		/*************************************
		 * air germs concentration components
		 ************************************/
		Composite airGermsComposite = new Composite(airSection, SWT.NONE);
		airGermsComposite.setBackgroundMode(SWT.INHERIT_DEFAULT);
		formToolkit.adapt(airGermsComposite);
		formToolkit.paintBordersFor(airGermsComposite);
		gl = new GridLayout(4, false);
		gl.marginWidth = 1;
		airGermsComposite.setLayout(gl);
		airSection.setClient(airGermsComposite);

		Label airGradeLabel = new Label(airGermsComposite, SWT.NONE);
		airGradeLabel.setLayoutData(LayoutUtil.createHorzFillData(2));
		formToolkit.adapt(contactGradeLabel, true, true);
		airGradeLabel.setText(I18N.tr("Grade"));

		Label airWeightLabel = new Label(airGermsComposite, SWT.NONE);
		airWeightLabel.setLayoutData(LayoutUtil.createHorzFillData(2));
		formToolkit.adapt(contactWeightLabel, true, true);
		airWeightLabel.setText(I18N.tr("Weight"));

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

		Label airGermsConcentrationLabel = new Label(airGermsComposite,
				SWT.NONE);
		airGermsConcentrationLabel.setLayoutData(LayoutUtil
				.createLeftGridData());
		formToolkit.adapt(airGermsConcentrationLabel, true, true);
		airGermsConcentrationLabel.setText(I18N.tr("Concentration"));

		airGermsConcentrationText = formToolkit.createText(airGermsComposite,
				"", SWT.NONE);
		airGermsConcentrationText.setLayoutData(LayoutUtil
				.createHorzCenteredFillData(3));
		// TODO init with value

		Label airCommentLabel = new Label(airGermsComposite, SWT.NONE);
		airCommentLabel.setLayoutData(LayoutUtil.createLeftGridData());
		formToolkit.adapt(airCommentLabel, true, true);
		airCommentLabel.setText(I18N.tr("Comment"));

		airCommentCombo = new Combo(airGermsComposite, SWT.NONE);
		airCommentCombo.setLayoutData(LayoutUtil.createHorzFillData(3));
		formToolkit.adapt(airCommentCombo);
		formToolkit.paintBordersFor(airCommentCombo);
		initCommentAutoCompletion(airCommentCombo);
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
		tagsComposite.setBackground(getDisplay()
				.getSystemColor(SWT.COLOR_WHITE));
		tagSection.setClient(tagsComposite);

		nothingRadioButton = new Button(tagsComposite, SWT.RADIO);
		formToolkit.adapt(nothingRadioButton, true, true);
		nothingRadioButton.setLayoutData(LayoutUtil.createHorzFillData());
		nothingRadioButton.setText(I18N.tr("Nothing"));
		nothingRadioButton.setSelection(true);
		nothingRadioButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				samplePointType.set(SamplingPointType.none);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

		airGermsRadioButton = new Button(tagsComposite, SWT.RADIO);
		formToolkit.adapt(airGermsRadioButton, true, true);
		airGermsRadioButton.setLayoutData(LayoutUtil.createHorzFillData());
		airGermsRadioButton.setText(I18N.tr("Air germs meassurement"));
		airGermsRadioButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				samplePointType.set(SamplingPointType.airMeasurement);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

		waterAnalysisRadioButton = new Button(tagsComposite, SWT.RADIO);
		formToolkit.adapt(waterAnalysisRadioButton, true, true);
		waterAnalysisRadioButton.setLayoutData(LayoutUtil.createHorzFillData());
		waterAnalysisRadioButton.setText(I18N.tr("Water analysis")+" / "+I18N.tr("Legionella"));
		waterAnalysisRadioButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				samplePointType.set(SamplingPointType.waterAnalysis);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

		contactCultureRadioButton = new Button(tagsComposite, SWT.RADIO);
		formToolkit.adapt(contactCultureRadioButton, true, true);
		contactCultureRadioButton
				.setLayoutData(LayoutUtil.createHorzFillData());
		contactCultureRadioButton.setText(I18N.tr("Contact cultures"));
		contactCultureRadioButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				samplePointType.set(SamplingPointType.contactCulture);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

		sc.setMinSize(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
	}

	public void addSamplePointObserver(Observer<SamplingPointType> o) {
		samplePointType.addObserver(o);
	}

	public void addGradeSelectionObserver(Observer<Integer> o) {
		selectedGrade.addObserver(o);
	}

	private void initCommentAutoCompletion(Combo combo) {

		Collection<Comment> comments;
		try {
			comments = commentService.findAll();
			String[] s = new String[comments.size()];
			int i = 0;

			for (Comment c : comments) {
				s[i] = c.getText();
				i++;
			}
			combo.setItems(s);
			ContentProposalUtil.enableContentProposal(combo);
		} catch (DatabaseException e) {
			LOG.debug("An error occured", e);
		}

	}

	@Override
	public void inspectionChanged(Inspection inspection) {
		updateInspectionData(inspection);
	}

	

    @Override
	public void inspectionComponentSelectionChanged(SchemeComponent component) {
        if (component == null) {
            return;
        }
        
        currentSchemeComponent = component;

        BiologicalRating rating = getRatingForComponent(component);
        
        if (rating != null){
            updateRatingValues(rating);
        }
        else {
            //TODO set default values
        }
	}

	private void updateRatingValues(BiologicalRating rating) {
        // TODO Auto-generated method stub
        
    }

    private BiologicalRating getRatingForComponent(SchemeComponent component) {
        for (BiologicalRating rating : ratings){
            if (rating.getComponent().equals(component));
                return rating;
        }
        
        BiologicalRating rating = new BiologicalRating(inspection, currentSchemeComponent);
        ratings.add(rating);
        return rating;
    }

    @Override
	public void plantChanged(Plant plant) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
	
	private void updateInspectionData(Inspection inspection) {

        try {
            ratings = inspectionService.findBiologicalRating(inspection);

        }

        catch (DatabaseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	    
    }


}
