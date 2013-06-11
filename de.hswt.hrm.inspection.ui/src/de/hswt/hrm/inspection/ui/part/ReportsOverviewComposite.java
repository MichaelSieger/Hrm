package de.hswt.hrm.inspection.ui.part;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.contexts.IEclipseContext;
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
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

import de.hswt.hrm.common.ui.swt.constants.SearchFieldConstants;
import de.hswt.hrm.common.ui.swt.forms.FormUtil;
import de.hswt.hrm.common.ui.swt.layouts.LayoutUtil;
import de.hswt.hrm.common.ui.swt.table.ColumnComparator;
import de.hswt.hrm.common.ui.swt.table.ColumnDescription;
import de.hswt.hrm.common.ui.swt.table.TableViewerController;

public class ReportsOverviewComposite extends Composite {

	// TODO use this if it is implemented
//	@Inject
//	private InspectionService inspectionService;

	@Inject
	private IEclipseContext context;
	
	@Inject
	private IShellProvider shellProvider;
	
	private FormToolkit toolkit = new FormToolkit(Display.getDefault());

	private Text searchText;

	private TableViewer tableViewer;

	private Table table;

	// TODO use this if implemented
//	private InspectionFilter searchFilter = new InspectionFilter();
	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public ReportsOverviewComposite(Composite parent) {
		super(parent, SWT.NONE);
		toolkit.dispose();
		toolkit = FormUtil.createToolkit();
	}

	@PostConstruct
	public void createControlls() {
		this.setLayout(new FillLayout());
		
		Section overviewSection = toolkit.createSection(this, Section.TITLE_BAR);
		toolkit.paintBordersFor(overviewSection);
		overviewSection.setText("Reports overview");
		FormUtil.initSectionColors(overviewSection);
		
		Composite overviewComposite = new Composite(overviewSection, SWT.NONE);
		toolkit.adapt(overviewComposite);
		toolkit.paintBordersFor(overviewComposite);
		overviewSection.setClient(overviewComposite);
		overviewComposite.setLayout(new GridLayout(1, false));
		
		searchText = new Text(overviewComposite, SWT.BORDER | 
				SWT.SEARCH | SWT.ICON_SEARCH | SWT.CANCEL | SWT.ICON_CANCEL);
		searchText.setMessage(SearchFieldConstants.DEFAULT_SEARCH_STRING);
		searchText.setLayoutData(LayoutUtil.createHorzFillData());
		searchText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				updateSearchTableFilter(searchText.getText());
			}
		});
		toolkit.adapt(searchText, true, true);
		
		tableViewer = new TableViewer(overviewComposite, SWT.BORDER | SWT.FULL_SELECTION);
		tableViewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				editInspection();
			}
		});
		table = tableViewer.getTable();
		toolkit.paintBordersFor(table);
		table.setLayoutData(LayoutUtil.createFillData());
	}
	
	private void refreshTable() {
		// TODO adapt to the inspectionService
//		try {
//			tableViewer.setInput(catalogService.findAllCatalogItem());
//		} catch (DatabaseException e) {
//			LOG.error("Unable to retrieve list of catalog items.", e);
//			showDBConnectionError();
//		}
	}

	private void showDBConnectionError() {
		// TODO translate
		MessageDialog.openError(shellProvider.getShell(), "Connection Error",
				"Could not load inspections from Database.");
	}

	private void updateSearchTableFilter(String filterString) {
		// TODO adapt to the inspectionService and Inspection POJO
//		InspectionFilter filter = (InspectionFilter) tableViewer.getFilters()[0];
//		filter.setSearchString(filterString);
//		tableViewer.refresh();
	}


	private void initializeTable() {
		// TODO adapt to the inspectionService and Inspection POJO
//		List<ColumnDescription<ICatalogItem>> columns = CatalogPartUtil
//				.getColumns();
//
//		// Create columns in tableviewer
//		TableViewerController<ICatalogItem> filler = new TableViewerController<>(
//				tableViewer);
//		filler.createColumns(columns);
//
//		// Enable column selection
//		filler.createColumnSelectionMenu();
//
//		// Enable sorting
//		ColumnComparator<ICatalogItem> comparator = new ColumnComparator<>(
//				columns);
//		filler.enableSorting(comparator);
//
//		// Add dataprovider that handles our collection
//		tableViewer.setContentProvider(ArrayContentProvider.getInstance());
//
//		// Enable filtering
//		tableViewer.addFilter(searchFilter);
	}

	public void addInspection() {
		// TODO adapt to the inspectionService and Inspection POJO
//		Optional<ICatalogItem> newItem = CatalogPartUtil.showWizard(context,
//				shellProvider.getShell(), Optional.<ICatalogItem> absent());
//
//		if (newItem.isPresent()) {
//			@SuppressWarnings("unchecked")
//			Collection<ICatalogItem> items = (Collection<ICatalogItem>) tableViewer
//					.getInput();
//			items.add(newItem.get());
//			tableViewer.refresh();
//		}

	}

	public void editInspection() {
		// TODO adapt to the inspectionService and Inspection POJO
		// e.g. see ContactsPart
//		// obtain the place in the column where the doubleClick happend
//		ICatalogItem selectedItem = (ICatalogItem) tableViewer
//				.getElementAt(tableViewer.getTable().getSelectionIndex());
//		if (selectedItem == null) {
//			return;
//		}
//
//        // Refresh the selected place with values from the database
//        try {
//
//            if (selectedItem instanceof Activity) {
//                catalogService.refresh((Activity) selectedItem);
//            }
//            else if (selectedItem instanceof Current) {
//                catalogService.refresh((Current) selectedItem);
//            }
//            else {
//                catalogService.refresh((Target) selectedItem);
//            }
//
//            Optional<ICatalogItem> updatedItem = CatalogPartUtil.showWizard(context,
//                    shellProvider.getShell(), Optional.of(selectedItem));
//
//            if (updatedItem.isPresent()) {
//                tableViewer.refresh();
//            }
//        }
//        catch (DatabaseException e) {
//            LOG.error("Could not retrieve the CatalogItem from database.", e);
//            showDBConnectionError();
//        }
	}

	@Override
	public void dispose() {
		if (toolkit != null) {
			toolkit.dispose();
		}
		super.dispose();
	}

	@Override
	protected void checkSubclass() {
	}
}
