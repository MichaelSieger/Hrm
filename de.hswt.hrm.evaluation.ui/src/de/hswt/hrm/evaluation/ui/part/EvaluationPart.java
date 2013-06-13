package de.hswt.hrm.evaluation.ui.part;

import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;

import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.ui.swt.constants.SearchFieldConstants;
import de.hswt.hrm.common.ui.swt.forms.FormUtil;
import de.hswt.hrm.common.ui.swt.layouts.LayoutUtil;
import de.hswt.hrm.common.ui.swt.table.ColumnComparator;
import de.hswt.hrm.common.ui.swt.table.ColumnDescription;
import de.hswt.hrm.common.ui.swt.table.TableViewerController;
import de.hswt.hrm.evaluation.model.Evaluation;
import de.hswt.hrm.evaluation.service.EvaluationService;
import de.hswt.hrm.evaluation.ui.filter.EvaluationFilter;

public class EvaluationPart {

	private final static Logger LOG = LoggerFactory
			.getLogger(EvaluationPart.class);

	@Inject
	private EvaluationService evalService;

	@Inject
	private IShellProvider shellProvider;

	@Inject
	private IEclipseContext context;

	private FormToolkit toolkit = new FormToolkit(Display.getDefault());
	private Table table;
	private Text searchText;
	private TableViewer tableViewer;

	private Action editAction;
	private Action addAction;
	private Section headerSection;
	private Composite composite;

	private Collection<Evaluation> evaluations;

	public EvaluationPart() {
		// toolkit can be created in PostConstruct, but then then
		// WindowBuilder is unable to parse the code
		toolkit.dispose();
		toolkit = FormUtil.createToolkit();
	}

	/**
	 * Create contents of the view part.
	 */
	@PostConstruct
	public void createControls(Composite parent) {

		createActions();
		toolkit.setBorderStyle(SWT.BORDER);
		toolkit.adapt(parent);
		toolkit.paintBordersFor(parent);
		parent.setLayout(new FillLayout(SWT.HORIZONTAL));

		Form form = toolkit.createForm(parent);
		form.getHead().setOrientation(SWT.RIGHT_TO_LEFT);
		form.setSeparatorVisible(true);
		toolkit.paintBordersFor(form);
		form.setText("Evaluation");
		toolkit.decorateFormHeading(form);
		form.getToolBarManager().add(editAction);

		form.getToolBarManager().add(addAction);
		FillLayout fillLayout = new FillLayout(SWT.HORIZONTAL);
		fillLayout.marginHeight = 1;
		form.getBody().setLayout(fillLayout);

		headerSection = toolkit
				.createSection(form.getBody(), Section.TITLE_BAR);
		toolkit.paintBordersFor(headerSection);
		headerSection.setExpanded(true);
		FormUtil.initSectionColors(headerSection);

		composite = toolkit.createComposite(headerSection, SWT.NONE);
		toolkit.paintBordersFor(composite);
		headerSection.setClient(composite);
		composite.setLayout(new GridLayout(1, false));

		searchText = new Text(composite, SWT.BORDER | SWT.SEARCH
				| SWT.ICON_SEARCH | SWT.CANCEL | SWT.ICON_CANCEL);
		searchText.setMessage(SearchFieldConstants.DEFAULT_SEARCH_STRING);
		searchText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				updateTableFilter(searchText.getText());
			}
		});
		searchText.setLayoutData(LayoutUtil.createHorzFillData());
		toolkit.adapt(searchText, true, true);

		tableViewer = new TableViewer(composite, SWT.BORDER
				| SWT.FULL_SELECTION);
		tableViewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				editEvaluation();
			}
		});
		table = tableViewer.getTable();
		table.setSize(214, 221);
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		table.setLayoutData(LayoutUtil.createFillData());
		toolkit.paintBordersFor(table);
		form.getToolBarManager().update(true);

		initializeTable();
		refreshTable();

		if (evalService == null) {
			LOG.error("EvaluationService not injected to EvaluationPart.");
		}
	}

	private void createActions() {
		{
			editAction = new Action("Edit") {
				@Override
				public void run() {
					super.run();
					editEvaluation();
				}
			};
			editAction.setDescription("Edit an exisitng Evaluation.");
		}
		{
			addAction = new Action("Add") {
				@Override
				public void run() {
					super.run();
					addEvaluation();
				}
			};
			addAction.setDescription("Add's a new Evaluation.");
		}
	}

	@PreDestroy
	public void dispose() {
		if (toolkit != null) {
			toolkit.dispose();
		}
	}

	private void refreshTable() {
		try {
			this.evaluations = evalService.findAll();
			tableViewer.setInput(this.evaluations);
		} catch (DatabaseException e) {
			LOG.error("Unable to retrieve list of Evaluations.", e);
			showDBConnectionError();
		}
	}

	private void initializeTable() {
		List<ColumnDescription<Evaluation>> columns = EvaluationPartUtil
				.getColumns();

		// Create columns in tableviewer
		TableViewerController<Evaluation> filler = new TableViewerController<>(
				tableViewer);
		filler.createColumns(columns);

		// Enable column selection
		filler.createColumnSelectionMenu();

		// Enable sorting
		ColumnComparator<Evaluation> comperator = new ColumnComparator<>(
				columns);
		filler.enableSorting(comperator);

		// Add dataprovider that handles our collection
		tableViewer.setContentProvider(ArrayContentProvider.getInstance());

		// Enable filtering
		tableViewer.addFilter(new EvaluationFilter());
	}

	private void showDBConnectionError() {
		// TODO translate
		MessageDialog.openError(shellProvider.getShell(), "Connection Error",
				"Could not load evaluations from Database.");
	}

	private void updateTableFilter(String filterString) {
		EvaluationFilter filter = (EvaluationFilter) tableViewer.getFilters()[0];
		filter.setSearchString(filterString);
		tableViewer.refresh();
	}

	/**
	 * This Event is called whenever the add button is pressed.
	 * 
	 * @param event
	 */

	private void addEvaluation() {
		Evaluation eval = null;

		Optional<Evaluation> newEval = EvaluationPartUtil.showWizard(context,
				shellProvider.getShell(), Optional.fromNullable(eval));

		if (newEval.isPresent()) {
			evaluations.add(newEval.get());
			tableViewer.refresh();
		}
	}

	/**
	 * This method is called whenever a doubleClick onto the Tableviewer occurs.
	 * It obtains the contact from the selected column of the TableViewer. The
	 * Contact is passed to the ContactWizard. When the Wizard has finished, the
	 * contact will be updated in the Database
	 * 
	 * @param event
	 *            Event which occured within SWT
	 */
	private void editEvaluation() {
		// obtain the contact in the column where the doubleClick happend
		Evaluation selectedEval = (Evaluation) tableViewer
				.getElementAt(tableViewer.getTable().getSelectionIndex());
		if (selectedEval == null) {
			return;
		}
		try {
			evalService.refresh(selectedEval);
			Optional<Evaluation> updatedPlace = EvaluationPartUtil.showWizard(
					context, shellProvider.getShell(),
					Optional.of(selectedEval));

			if (updatedPlace.isPresent()) {
				tableViewer.refresh();
			}
		} catch (DatabaseException e) {
			LOG.error("Could not retrieve the place from database.", e);
			showDBConnectionError();
		}
	}

}
