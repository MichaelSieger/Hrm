package de.hswt.hrm.inspection.ui.wizard;

import java.util.GregorianCalendar;
import java.util.List;

import javax.inject.Inject;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.database.exception.ElementNotFoundException;
import de.hswt.hrm.common.ui.swt.forms.FormUtil;
import de.hswt.hrm.common.ui.swt.layouts.LayoutUtil;
import de.hswt.hrm.common.ui.swt.layouts.PageContainerFillLayout;
import de.hswt.hrm.common.ui.swt.utils.SWTResourceManager;
import de.hswt.hrm.i18n.I18n;
import de.hswt.hrm.i18n.I18nFactory;
import de.hswt.hrm.inspection.model.Inspection;
import de.hswt.hrm.inspection.model.Layout;
import de.hswt.hrm.inspection.service.LayoutService;
import de.hswt.hrm.inspection.ui.dialog.PlantSelectionDialog;
import de.hswt.hrm.plant.model.Plant;
import de.hswt.hrm.scheme.model.Scheme;
import de.hswt.hrm.scheme.service.SchemeService;

public class ReportCreationWizardPageOne extends WizardPage {

	@Inject
	private IEclipseContext context;

	@Inject
	private IShellProvider shellProvider;

	@Inject
	private LayoutService layoutService;

	@Inject
	private SchemeService schemeService;

	private FormToolkit formToolkit;
	private Text titleText;
	private Text plantText;
	private DateTime reportDateTime;
	private DateTime inspectionDateTime;
	private DateTime nextInspectionDateTime;

	private Inspection inspection;

	private boolean titleFirst = true;

	private boolean plantFirst = true;

	private String title;
	private Plant plant;
	private GregorianCalendar reportDate;
	private GregorianCalendar inspectionDate;
	private GregorianCalendar nextInspectionDate;
	private Layout reportStyle;

	private Combo reportStyleCombo;
	
	private static final I18n I18N = I18nFactory.getI18n(ReportCreationWizardPageOne.class);

	/**
	 * Create the wizard.
	 */
	public ReportCreationWizardPageOne() {
		super("wizardPage");
		setTitle(I18N.tr("Create report"));
		setDescription(I18N.tr("Please provide the basic report informations."));
	}

	/**
	 * Create contents of the wizard.
	 * 
	 * @param parent
	 */
	public void createControl(Composite parent) {
		parent.setLayout(new PageContainerFillLayout());
		parent.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));

		formToolkit = FormUtil.createToolkit();

		Section generalSection = formToolkit.createSection(parent,
				Section.TITLE_BAR);
		formToolkit.paintBordersFor(generalSection);
		generalSection.setText(I18N.tr("Report information"));
		generalSection.setLayoutData(LayoutUtil.createHorzFillData());
		FormUtil.initSectionColors(generalSection);

		Composite generalComposite = new Composite(generalSection, SWT.NONE);
		generalComposite.setBackgroundMode(SWT.INHERIT_DEFAULT);
		formToolkit.adapt(generalComposite);
		formToolkit.paintBordersFor(generalComposite);
		GridLayout gl = new GridLayout(5, false);
		gl.marginWidth = 1;
		generalComposite.setLayout(gl);
		generalSection.setClient(generalComposite);

		Label titleLabel = new Label(generalComposite, SWT.NONE);
		titleLabel.setLayoutData(LayoutUtil.createLeftCenteredGridData());
		formToolkit.adapt(titleLabel, true, true);
		titleLabel.setText(I18N.tr("Title")+":");

		titleText = new Text(generalComposite, SWT.NONE);
		titleText.setTextLimit(50);
		titleText.setLayoutData(LayoutUtil.createHorzCenteredFillData(4, 1));
		titleText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				if (titleFirst) {
					titleFirst = false;
				}
				checkPage();
			}
		});
		formToolkit.adapt(titleText, true, true);

		Label plantLabel = new Label(generalComposite, SWT.NONE);
		plantLabel.setLayoutData(LayoutUtil.createLeftCenteredGridData());
		formToolkit.adapt(plantLabel, true, true);
		plantLabel.setText(I18N.tr("Plant")+":");

		plantText = new Text(generalComposite, SWT.READ_ONLY);
		plantText.setLayoutData(LayoutUtil.createHorzCenteredFillData(2, 1));
		formToolkit.adapt(plantText, true, true);

		Button plantSelectionButton = formToolkit.createButton(
				generalComposite, I18N.tr("Select")+" "+I18N.tr("Plant"), SWT.PUSH);
		plantSelectionButton.setLayoutData(LayoutUtil.createRightGridData(2));
		plantSelectionButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				PlantSelectionDialog psd = new PlantSelectionDialog(
						shellProvider.getShell(), context);
				psd.create();

				if (plantFirst) {
					plantFirst = false;
				}

				if (psd.open() == Window.OK) {
					plant = psd.getPlant();
					plantText.setText(plant.getDescription());
				} else {
					plant = null;
				}
				checkPage();
			}
		});

		Label reportDateLabel = new Label(generalComposite, SWT.NONE);
		reportDateLabel.setLayoutData(LayoutUtil.createLeftCenteredGridData());
		formToolkit.adapt(reportDateLabel, true, true);
		reportDateLabel.setText(I18N.tr("Report Date")+":");

		reportDateTime = new DateTime(generalComposite, SWT.BORDER | SWT.DATE
				| SWT.DROP_DOWN);
		formToolkit.adapt(reportDateTime);
		formToolkit.paintBordersFor(reportDateTime);
		reportDateTime.setLayoutData(LayoutUtil.createHorzFillData(4));
		reportDateTime.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				reportDate = getCalender(reportDateTime);
				checkPage();
			}
		});
		reportDate = getCalender(reportDateTime);

		Label inspectionDateLabel = new Label(generalComposite, SWT.NONE);
		inspectionDateLabel.setLayoutData(LayoutUtil
				.createLeftCenteredGridData());
		formToolkit.adapt(inspectionDateLabel, true, true);
		inspectionDateLabel.setText(I18N.tr("Inspection Date")+":");

		inspectionDateTime = new DateTime(generalComposite, SWT.BORDER
				| SWT.DATE | SWT.DROP_DOWN);
		formToolkit.adapt(inspectionDateTime);
		formToolkit.paintBordersFor(inspectionDateTime);
		inspectionDateTime.setLayoutData(LayoutUtil.createHorzFillData(4));
		inspectionDateTime.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				inspectionDate = getCalender(inspectionDateTime);
				checkPage();
			}
		});
		inspectionDate = getCalender(inspectionDateTime);

		Label nextInspectionLabel = new Label(generalComposite, SWT.NONE);
		nextInspectionLabel.setLayoutData(LayoutUtil
				.createLeftCenteredGridData());
		formToolkit.adapt(nextInspectionLabel, true, true);
		nextInspectionLabel.setText(I18N.tr("Next Inspection")+":");

		nextInspectionDateTime = new DateTime(generalComposite, SWT.BORDER
				| SWT.DATE | SWT.DROP_DOWN);
		formToolkit.adapt(nextInspectionDateTime);
		formToolkit.paintBordersFor(nextInspectionDateTime);
		nextInspectionDateTime.setLayoutData(LayoutUtil.createHorzFillData());
		nextInspectionDateTime.setYear(inspectionDateTime.getYear() + 2);
		nextInspectionDateTime.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				nextInspectionDate = getCalender(nextInspectionDateTime);
				checkPage();
			}
		});

		Button twoYearsButton = formToolkit.createButton(generalComposite,
				"2 "+I18N.tr("years"), SWT.PUSH);
		twoYearsButton.setLayoutData(LayoutUtil.createRightGridData(2));
		twoYearsButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				nextInspectionDateTime.setYear(inspectionDateTime.getYear() + 2);
			}
		});

		Button threeYearsButton = formToolkit.createButton(generalComposite,
				"3 "+I18N.tr("years"), SWT.PUSH);
		threeYearsButton.setLayoutData(LayoutUtil.createRightGridData());
		threeYearsButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				nextInspectionDateTime.setYear(inspectionDateTime.getYear() + 3);
			}
		});

		Label reportStyleLabel = new Label(generalComposite, SWT.NONE);
		reportStyleLabel.setLayoutData(LayoutUtil.createLeftCenteredGridData());
		formToolkit.adapt(reportStyleLabel, true, true);
		reportStyleLabel.setText(I18N.tr("Layout")+":");

		reportStyleCombo = new Combo(generalComposite, SWT.DROP_DOWN
				| SWT.READ_ONLY);
		reportStyleCombo
				.setLayoutData(LayoutUtil.createHorzCenteredFillData(4));
		formToolkit.adapt(reportStyleCombo);
		formToolkit.paintBordersFor(reportStyleCombo);

		initLayouts(reportStyleCombo);
		setControl(generalSection);

		checkPage();
	}

	private void initLayouts(Combo combo) {

		try {
			// Obtain all layouts form the DB
			List<Layout> layouts = (List<Layout>) layoutService.findAll();

			String[] items = new String[layouts.size()];
			int index = 0;
			for (Layout l : layouts) {
				items[index] = l.getName();
				index++;

			}
			combo.setItems(items);
			index = 0;
			for (String s : items) {
				combo.setData(s, layouts.get(index));
				index++;
			}
			combo.select(0);
		}

		catch (DatabaseException e) {
			e.printStackTrace();
		}

	}

	private void checkPage() {
		setPageComplete(false);

		boolean ok = false, firstCheck = true;

		if (!titleFirst) {
			if (titleText.getText().isEmpty()) {
				setErrorMessage(I18N.tr("Please provide a title for the report."));
				return;
			} else {
				title = titleText.getText();
				ok = true;
			}
		} else {
			firstCheck = false;
		}

		if (!plantFirst) {
			if (plant == null) {
				setErrorMessage(I18N.tr("Please select the plant of the report."));
				return;
			} else {
				ok = true;
			}
		} else {
			firstCheck = false;
		}

		setErrorMessage(null);
		if (ok && firstCheck) {
			setPageComplete(true);

			reportStyle = (Layout) reportStyleCombo.getData(reportStyleCombo
					.getItem(reportStyleCombo.getSelectionIndex()));
			if (nextInspectionDate == null) {
				nextInspectionDate = getCalender(nextInspectionDateTime);
			}

			try {
				Scheme currentScheme = schemeService
						.findCurrentSchemeByPlant(plant);
				inspection = new Inspection(reportDate, inspectionDate,
						nextInspectionDate, title, reportStyle, plant,
						currentScheme);
			} catch (ElementNotFoundException e) {
				e.printStackTrace();
			} catch (DatabaseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	@Override
	public void dispose() {
		formToolkit.dispose();
		super.dispose();
	}

	protected Inspection getInspection() {
		return inspection;
	}

	private GregorianCalendar getCalender(DateTime date) {
		return new GregorianCalendar(date.getYear(), date.getMonth(),
				date.getDay());
	}
}
