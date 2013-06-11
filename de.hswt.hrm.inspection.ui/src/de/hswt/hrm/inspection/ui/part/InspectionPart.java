package de.hswt.hrm.inspection.ui.part;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.SWT;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Table;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.widgets.List;

import de.hswt.hrm.common.ui.swt.constants.SearchFieldConstants;
import de.hswt.hrm.common.ui.swt.forms.FormUtil;
import de.hswt.hrm.common.ui.swt.layouts.LayoutUtil;

import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.ui.forms.widgets.Section;

import ch.qos.logback.core.Layout;

public class InspectionPart {

	@Inject
	private IEclipseContext context;
	
	private FormToolkit formToolkit = new FormToolkit(Display.getDefault());
	
	private ReportsOverviewComposite reportsOverviewComposite;
	
	private Form form;

	private TabFolder tabFolder;

	private TabItem reportTab;
	private TabItem overviewTab;

	private IContributionItem addContribution;
	private IContributionItem copyContribution;
	private IContributionItem editContribution;
	private IContributionItem evaluateContribution;

	public InspectionPart() {
		// toolkit can be created in PostConstruct, but then then
		// WindowBuilder is unable to parse the code
		formToolkit.dispose();
		formToolkit = FormUtil.createToolkit();
	}

	/**
	 * Create contents of the view part.
	 */
	@PostConstruct
	public void createControls(Composite parent) {
		parent.setBackgroundMode(SWT.INHERIT_DEFAULT);
		
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setBackgroundMode(SWT.INHERIT_DEFAULT);
		composite.setLayout(new FillLayout());
		
		form = formToolkit.createForm(composite);
		form.getHead().setOrientation(SWT.RIGHT_TO_LEFT);
		form.getBody().setBackgroundMode(SWT.INHERIT_FORCE);
		form.setBackgroundMode(SWT.INHERIT_DEFAULT);
		formToolkit.paintBordersFor(form);
		form.setText("Inspection");
		FillLayout fillLayout = new FillLayout(SWT.HORIZONTAL);
		fillLayout.marginHeight = 5;
		fillLayout.marginWidth = 5;
		form.getBody().setLayout(fillLayout);
		formToolkit.decorateFormHeading(form);
		
		tabFolder = new TabFolder(form.getBody(), SWT.NONE);
		tabFolder.setBackgroundMode(SWT.INHERIT_FORCE);
		formToolkit.adapt(tabFolder);
		formToolkit.paintBordersFor(tabFolder);
		tabFolder.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (tabFolder.getItem(tabFolder.getSelectionIndex()).equals(overviewTab)) {
					showOverviewActions(true);
				} else {
					showOverviewActions(false);
				}
			}
		});
		
		overviewTab = new TabItem(tabFolder, SWT.NONE);
		overviewTab.setText("Overview");
		
		// reports overview composite
		reportsOverviewComposite = new ReportsOverviewComposite(tabFolder);
		ContextInjectionFactory.inject(reportsOverviewComposite, context);
		overviewTab.setControl(reportsOverviewComposite);
		
		reportTab = new TabItem(tabFolder, SWT.NONE);
		reportTab.setText("Report");
		
		Composite reportComposite = new Composite(tabFolder, SWT.NONE);
		reportComposite.setBackgroundMode(SWT.INHERIT_DEFAULT);
		reportTab.setControl(reportComposite);
		formToolkit.paintBordersFor(reportComposite);
		FillLayout fl_reportComposite = new FillLayout(SWT.HORIZONTAL);
		fl_reportComposite.marginWidth = 5;
		fl_reportComposite.marginHeight = 5;
		reportComposite.setLayout(fl_reportComposite);
		
		SashForm horizontalSash = new SashForm(reportComposite, SWT.NONE);
		horizontalSash.setBackgroundMode(SWT.INHERIT_DEFAULT);
		formToolkit.adapt(horizontalSash);
		formToolkit.paintBordersFor(horizontalSash);
		
		Section listSection = formToolkit.createSection(horizontalSash, Section.TITLE_BAR);
		formToolkit.paintBordersFor(listSection);
		listSection.setText("Components");
		FormUtil.initSectionColors(listSection);
		
		List list = new List(listSection, SWT.BORDER);
		listSection.setClient(list);
		formToolkit.adapt(list, true, true);
		
		SashForm verticalSash = new SashForm(horizontalSash, SWT.VERTICAL);
		verticalSash.setBackgroundMode(SWT.INHERIT_FORCE);
		formToolkit.adapt(verticalSash);
		formToolkit.paintBordersFor(verticalSash);
		
		TabFolder reportTabFolder = new TabFolder(verticalSash, SWT.NONE);
		reportTabFolder.setBackgroundMode(SWT.INHERIT_FORCE);
		formToolkit.adapt(reportTabFolder);
		formToolkit.paintBordersFor(reportTabFolder);
		
		TabItem generalTab = new TabItem(reportTabFolder, SWT.NONE);
		generalTab.setText("General");
		
		Composite generalComposite = new Composite(reportTabFolder, SWT.NONE);
		generalComposite.setBackgroundMode(SWT.INHERIT_FORCE);
		generalTab.setControl(generalComposite);
		formToolkit.paintBordersFor(generalComposite);
		generalComposite.setLayout(new GridLayout(1, false));
		
		Section personsSection = formToolkit.createSection(generalComposite, Section.TITLE_BAR);
		personsSection.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		formToolkit.paintBordersFor(personsSection);
		personsSection.setText("Persons");
		
		Composite personsComposite = new Composite(personsSection, SWT.NONE);
		formToolkit.adapt(personsComposite);
		formToolkit.paintBordersFor(personsComposite);
		personsSection.setClient(personsComposite);
		personsComposite.setLayout(new GridLayout(2, false));
		
		Button selectCustomer = new Button(personsComposite, SWT.NONE);
		selectCustomer.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		formToolkit.adapt(selectCustomer, true, true);
		selectCustomer.setText("Select Customer ");
		
		Label lblName = new Label(personsComposite, SWT.NONE);
		formToolkit.adapt(lblName, true, true);
		lblName.setText("Name");
		
		TabItem ratingTab = new TabItem(reportTabFolder, SWT.NONE);
		ratingTab.setText("Rating");
		
		Composite ratingComposite = new Composite(reportTabFolder, SWT.NONE);
		ratingComposite.setBackgroundMode(SWT.INHERIT_DEFAULT);
		ratingTab.setControl(ratingComposite);
		formToolkit.paintBordersFor(ratingComposite);
		
		TabItem performanceTab = new TabItem(reportTabFolder, SWT.NONE);
		performanceTab.setText("Performance");
		
		Section schemeSection = formToolkit.createSection(verticalSash, Section.TITLE_BAR);
		formToolkit.paintBordersFor(schemeSection);
		schemeSection.setText("Scheme");
		schemeSection.setExpanded(true);
		FormUtil.initSectionColors(schemeSection);
		
		Composite schemeComposite = new Composite(schemeSection, SWT.NONE);
		schemeSection.setClient(schemeComposite);
		formToolkit.adapt(schemeComposite);
		formToolkit.paintBordersFor(schemeComposite);
		FillLayout fl_schemeComposite = new FillLayout(SWT.HORIZONTAL);
		fl_schemeComposite.marginHeight = 5;
		schemeComposite.setLayout(fl_schemeComposite);
		verticalSash.setWeights(new int[] {4, 2});
		horizontalSash.setWeights(new int[] {183, 898});
		
		createActions();
	}

	protected void showOverviewActions(boolean visible) {
		addContribution.setVisible(visible);
		copyContribution.setVisible(visible);
		editContribution.setVisible(visible);
		evaluateContribution.setVisible(visible);
		form.getToolBarManager().update(true);
	}

	private void createActions() {
		// TODO translate
		
		Action evaluateAction = new Action("Report") {
			@Override
			public void run() {
				super.run();
				// TODO read selected inspection/report and init the
				// evaluation tab with it
				tabFolder.setSelection(reportTab);
			}
		};
		evaluateAction.setDescription("Edit an exisitng report.");
		evaluateContribution = new ActionContributionItem(evaluateAction);
		form.getToolBarManager().add(evaluateContribution);
		
		Action editAction = new Action("Edit") {
			@Override
			public void run() {
				super.run();
				reportsOverviewComposite.editInspection();
			}
		};
		editAction.setDescription("Edit an exisitng report.");
		editContribution = new ActionContributionItem(editAction);
		form.getToolBarManager().add(editContribution);

		Action copyAction = new Action("Copy") {
			@Override
			public void run() {
				super.run();
				// TODO copy a report
			}
		};
		copyAction.setDescription("Add's a new report.");
		copyAction.setEnabled(false);
		copyContribution = new ActionContributionItem(copyAction);
		form.getToolBarManager().add(copyContribution);

		Action addAction = new Action("Add") {
				@Override
				public void run() {
					super.run();
					reportsOverviewComposite.addInspection();
				}
			};
		addAction.setDescription("Add's a new report.");
		addContribution = new ActionContributionItem(addAction);
		form.getToolBarManager().add(addContribution);

		form.getToolBarManager().update(true);
	}
	
	@PreDestroy
	public void dispose() {
		formToolkit.dispose();
	}

	@Focus
	public void setFocus() {
		// TODO	Set the focus to control
	}
	
	private void updateTableFilter(String filterString) {
		// TODO adapt to this class
//		ContactFilter filter = (ContactFilter) tableViewer.getFilters()[0];
//		filter.setSearchString(filterString);
//		tableViewer.refresh();
	}
}
