package de.hswt.hrm.place.ui.part;

import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.xwt.XWT;
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
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
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
import de.hswt.hrm.place.model.Place;
import de.hswt.hrm.place.service.PlaceService;
import de.hswt.hrm.place.ui.filter.PlaceFilter;

public class PlacePart {

	private final static Logger LOG = LoggerFactory.getLogger(PlacePart.class);

	@Inject
	private PlaceService placeService;

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

	public PlacePart() {
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
		form.setText("Places");
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
				editPlace();
			}
		});
		table = tableViewer.getTable();
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		table.setLayoutData(LayoutUtil.createFillData());
		toolkit.paintBordersFor(table);
		form.getToolBarManager().update(true);

		initializeTable();
		refreshTable(parent);

		if (placeService == null) {
			LOG.error("PlaceService not injected to PlacePart.");
		}
	}

	private void createActions() {
		{
			editAction = new Action("Edit") {
				@Override
				public void run() {
					super.run();
					editPlace();
				}
			};
			editAction.setDescription("Edit an exisitng place.");
		}
		{
			addAction = new Action("Add") {
				@Override
				public void run() {
					super.run();
					addPlace();
				}
			};
			addAction.setDescription("Add's a new place.");
		}
	}

	@PreDestroy
	public void dispose() {
		if (toolkit != null) {
			toolkit.dispose();
		}
	}

	@Focus
	public void setFocus() {
	}

	private void refreshTable(Composite parent) {
		try {
			tableViewer.setInput(placeService.findAll());
		} catch (DatabaseException e) {
			LOG.error("Unable to retrieve list of contacts.", e);
			showDBConnectionError();
		}
	}

	private void showDBConnectionError() {
		// TODO translate
		MessageDialog.openError(shellProvider.getShell(), "Connection Error",
				"Could not load contacts from Database.");
	}

	private void updateTableFilter(String filterString) {
		PlaceFilter filter = (PlaceFilter) tableViewer.getFilters()[0];
		filter.setSearchString(filterString);
		tableViewer.refresh();
	}

	private void initializeTable() {
		List<ColumnDescription<Place>> columns = PlacePartUtil.getColumns();

		// Create columns in tableviewer
		TableViewerController<Place> filler = new TableViewerController<>(
				tableViewer);
		filler.createColumns(columns);

		// Enable column selection
		filler.createColumnSelectionMenu();

		// Enable sorting
		ColumnComparator<Place> comparator = new ColumnComparator<>(columns);
		filler.enableSorting(comparator);

		// Add dataprovider that handles our collection
		tableViewer.setContentProvider(ArrayContentProvider.getInstance());

		// Enable filtering
		tableViewer.addFilter(new PlaceFilter());
	}

	@SuppressWarnings("unchecked")
	public void addPlace() {
		Optional<Place> newPlace = PlacePartUtil.showWizard(context,
				shellProvider.getShell(), Optional.<Place> absent());

		if (newPlace.isPresent()) {
			Collection<Place> places = (Collection<Place>) tableViewer
					.getInput();
			places.add(newPlace.get());
			tableViewer.refresh();
		}
	}

	public void editPlace() {
		// obtain the place in the column where the doubleClick happend
		Place selectedPlace = (Place) tableViewer.getElementAt(tableViewer
				.getTable().getSelectionIndex());
		if (selectedPlace == null) {
			return;
		}

		// Refresh the selected place with values from the database
		try {
			placeService.refresh(selectedPlace);
			Optional<Place> updatedPlace = PlacePartUtil.showWizard(context,
					shellProvider.getShell(), Optional.of(selectedPlace));

			if (updatedPlace.isPresent()) {
				tableViewer.refresh();
			}
		} catch (DatabaseException e) {
			LOG.error("Could not retrieve the place from database.", e);
			showDBConnectionError();
		}
	}
}
