package de.hswt.hrm.inspection.ui.part;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

import de.hswt.hrm.common.ui.swt.forms.FormUtil;
import de.hswt.hrm.inspection.model.Inspection;

public class ReportComposite extends Composite {
	// TODO use this if it is implemented
//	@Inject
//	private InspectionService inspectionService;

	@Inject
	private IEclipseContext context;
	
	@Inject
	private IShellProvider shellProvider;
	
	private Inspection currentInspection;
	
	private FormToolkit toolkit = new FormToolkit(Display.getDefault());

	private TabFolder reportTabFolder;

	private TabItem generalTab;
	private TabItem biolocicalRatingTab;
	private TabItem physicalRatingTab;
	private TabItem performanceTab;

	private List componentsList;

	private ReportGeneralComposite reportGeneralComposite;
	private ReportBiologicalComposite reportBiologicalComposite;
	private ReportPhysicalComposite reportPhysicalComposite;
	private ReportPerformanceComposite reportPerformanceComposite;


	/**
	 * Create the composite. 
	 * 
	 * Do not use this constructor when instantiate this composite!
	 * It is only included to make the WindowsBuilder working.
	 * 
	 * @param parent
	 * @param style
	 */
	private ReportComposite(Composite parent, int style) {
		super(parent, SWT.NONE);
		createControls();
	}

	/**
	 * Create the composite.
	 * @param parent
	 */
	public ReportComposite(Composite parent) {
		super(parent, SWT.NONE);
	}

	@PostConstruct
	public void createControls() {
		this.setBackgroundMode(SWT.INHERIT_DEFAULT);
		FillLayout fillLayout = new FillLayout(SWT.HORIZONTAL);
		fillLayout.marginWidth = 5;
		fillLayout.marginHeight = 5;
		this.setLayout(fillLayout);

		SashForm verticalSash = new SashForm(this, SWT.VERTICAL);
		verticalSash.setBackgroundMode(SWT.INHERIT_FORCE);
		toolkit.adapt(verticalSash);
		toolkit.paintBordersFor(verticalSash);

		SashForm horizontalSash = new SashForm(verticalSash, SWT.NONE);
		horizontalSash.setBackgroundMode(SWT.INHERIT_DEFAULT);
		toolkit.adapt(horizontalSash);
		toolkit.paintBordersFor(horizontalSash);
		
		Section listSection = toolkit.createSection(horizontalSash, Section.TITLE_BAR);
		toolkit.paintBordersFor(listSection);
		listSection.setText("Components");
		FormUtil.initSectionColors(listSection);
		
		// TODO use this list to show and select components 
		componentsList = new List(listSection, SWT.BORDER);
		listSection.setClient(componentsList);
		toolkit.adapt(componentsList, true, true);
		
		Section schemeSection = toolkit.createSection(horizontalSash, Section.TITLE_BAR);
		toolkit.paintBordersFor(schemeSection);
		schemeSection.setText("Scheme");
		schemeSection.setExpanded(true);
		FormUtil.initSectionColors(schemeSection);
		
		Composite schemeComposite = new Composite(schemeSection, SWT.NONE);
		schemeSection.setClient(schemeComposite);
		toolkit.adapt(schemeComposite);
		toolkit.paintBordersFor(schemeComposite);
		FillLayout fl_schemeComposite = new FillLayout(SWT.HORIZONTAL);
		fl_schemeComposite.marginHeight = 5;
		schemeComposite.setLayout(fl_schemeComposite);

		
		reportTabFolder = new TabFolder(verticalSash, SWT.NONE);
		reportTabFolder.setBackgroundMode(SWT.INHERIT_FORCE);
		toolkit.adapt(reportTabFolder);
		toolkit.paintBordersFor(reportTabFolder);
		
		generalTab = new TabItem(reportTabFolder, SWT.NONE);
		generalTab.setText("General");
		
		reportGeneralComposite = new ReportGeneralComposite(reportTabFolder);
		ContextInjectionFactory.inject(reportGeneralComposite, context);
		generalTab.setControl(reportGeneralComposite);
		
		biolocicalRatingTab = new TabItem(reportTabFolder, SWT.NONE);
		biolocicalRatingTab.setText("Biological rating");

		reportBiologicalComposite = new ReportBiologicalComposite(reportTabFolder);
		ContextInjectionFactory.inject(reportBiologicalComposite, context);
		biolocicalRatingTab.setControl(reportBiologicalComposite);

		physicalRatingTab = new TabItem(reportTabFolder, SWT.NONE);
		physicalRatingTab.setText("Physical rating");

		reportPhysicalComposite = new ReportPhysicalComposite(reportTabFolder);
		ContextInjectionFactory.inject(reportPhysicalComposite, context);
		physicalRatingTab.setControl(reportPhysicalComposite);

		performanceTab = new TabItem(reportTabFolder, SWT.NONE);
		performanceTab.setText("Performance");

		reportPerformanceComposite = new ReportPerformanceComposite(reportTabFolder);
		ContextInjectionFactory.inject(reportPerformanceComposite, context);
		performanceTab.setControl(reportPerformanceComposite);

		horizontalSash.setWeights(new int[] {1, 5});		
		verticalSash.setWeights(new int[] {2, 5});
		
		// TODO add scheme composite here

	}
	
	public void setCurrentInspection(Inspection inspection) {
		currentInspection = inspection;
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}
