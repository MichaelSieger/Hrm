package de.hswt.hrm.catalog.ui.part;

import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;

import de.hswt.hrm.catalog.model.Activity;
import de.hswt.hrm.catalog.model.Current;
import de.hswt.hrm.catalog.model.ICatalogItem;
import de.hswt.hrm.catalog.model.Target;
import de.hswt.hrm.catalog.service.CatalogService;
import de.hswt.hrm.catalog.ui.filter.CatalogSelectionFilter;
import de.hswt.hrm.catalog.ui.filter.CatalogTextFilter;
import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.ui.swt.constants.SearchFieldConstants;
import de.hswt.hrm.common.ui.swt.forms.FormUtil;
import de.hswt.hrm.common.ui.swt.layouts.LayoutUtil;
import de.hswt.hrm.common.ui.swt.table.ColumnComparator;
import de.hswt.hrm.common.ui.swt.table.ColumnDescription;
import de.hswt.hrm.common.ui.swt.table.TableViewerController;

public class CatalogItemsComposite extends Composite {

	private final static Logger LOG = LoggerFactory
			.getLogger(CatalogItemsComposite.class);

	@Inject
	private CatalogService catalogService;

	@Inject
	private IShellProvider shellProvider;

	@Inject
	private IEclipseContext context;

	private FormToolkit toolkit = new FormToolkit(Display.getDefault());

	private Table table;
	private Text searchText;
	private TableViewer tableViewer;

	private Section catalogsHeaderSection;

	private CatalogTextFilter searchFilter = new CatalogTextFilter();

	private CatalogSelectionFilter selectionFilter = new CatalogSelectionFilter();

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public CatalogItemsComposite(Composite parent, int style) {
		super(parent, style);
		createControls();
	}

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 */
	public CatalogItemsComposite(Composite parent) {
		super(parent, SWT.NONE);
	}

	@PostConstruct
	public void createControls() {
		this.setLayout(new FillLayout());
		
		catalogsHeaderSection = toolkit.createSection(this, Section.TITLE_BAR);
		toolkit.paintBordersFor(catalogsHeaderSection);
		catalogsHeaderSection.setExpanded(true);
		FormUtil.initSectionColors(catalogsHeaderSection);

		Composite composite = toolkit.createComposite(catalogsHeaderSection,
				SWT.NONE);
		toolkit.paintBordersFor(composite);
		catalogsHeaderSection.setClient(composite);
		composite.setLayout(new GridLayout(2, false));

		searchText = new Text(composite, SWT.BORDER | SWT.SEARCH
				| SWT.ICON_SEARCH | SWT.CANCEL | SWT.ICON_CANCEL);
		searchText.setMessage(SearchFieldConstants.DEFAULT_SEARCH_STRING);
		searchText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				updateSearchTableFilter(searchText.getText());
			}
		});
		searchText.setLayoutData(LayoutUtil.createHorzFillData());
		toolkit.adapt(searchText, true, true);

		final Combo filterCombo = new Combo(composite, SWT.SIMPLE | SWT.DROP_DOWN | SWT.READ_ONLY);
		filterCombo.add("Show all");
		filterCombo.add("Targets");
		filterCombo.add("Currents");
		filterCombo.add("Activities");
		filterCombo.select(0);
		filterCombo.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				updateSelectionTableFilter(filterCombo.getSelectionIndex());
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				updateSelectionTableFilter(filterCombo.getSelectionIndex());
			}
		});
		filterCombo.setLayoutData(LayoutUtil.createRightGridData());
		
		tableViewer = new TableViewer(composite, SWT.BORDER
				| SWT.FULL_SELECTION);
		tableViewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				editItem();
			}
		});
		table = tableViewer.getTable();
		table.setSize(214, 221);
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		table.setLayoutData(LayoutUtil.createFillData(2));
		toolkit.paintBordersFor(table);

		initializeTable();
		refreshTable();
		
		if (catalogService == null) {
			LOG.error("CatalogService not injected to CatalogItemsPart.");
		}
	}

	private void refreshTable() {
		try {
			tableViewer.setInput(catalogService.findAllCatalogItem());
		} catch (DatabaseException e) {
			LOG.error("Unable to retrieve list of catalog items.", e);
			showDBConnectionError();
		}
	}

	private void showDBConnectionError() {
		// TODO translate
		MessageDialog.openError(shellProvider.getShell(), "Connection Error",
				"Could not load catalog items from Database.");
	}

	private void updateSearchTableFilter(String filterString) {
		searchFilter.setSearchString(filterString);
		tableViewer.refresh();
	}

	private void updateSelectionTableFilter(int filterId) {
		selectionFilter.setCurrentSelected(false);
        selectionFilter.setAllSelected(false);
        selectionFilter.setTargetSelected(false);
        selectionFilter.setActivitySelected(false);

        switch (filterId) {
        case 0:
        	selectionFilter.setAllSelected((true));
            break;
        case 1:
        	selectionFilter.setTargetSelected(true);
            break;
        case 2:
        	selectionFilter.setCurrentSelected(true);
            break;
        case 3:
        	selectionFilter.setActivitySelected(true);
            break;

        }

		tableViewer.refresh();
	}

	private void initializeTable() {
		List<ColumnDescription<ICatalogItem>> columns = CatalogPartUtil
				.getColumns();

		// Create columns in tableviewer
		TableViewerController<ICatalogItem> filler = new TableViewerController<>(
				tableViewer);
		filler.createColumns(columns);

		// Enable column selection
		filler.createColumnSelectionMenu();

		// Enable sorting
		ColumnComparator<ICatalogItem> comparator = new ColumnComparator<>(
				columns);
		filler.enableSorting(comparator);

		// Add dataprovider that handles our collection
		tableViewer.setContentProvider(ArrayContentProvider.getInstance());

		// Enable filtering
		tableViewer.addFilter(searchFilter);
		tableViewer.addFilter(selectionFilter);
	}

	public void addCatalogItem() {
		Optional<ICatalogItem> newItem = CatalogPartUtil.showWizard(context,
				shellProvider.getShell(), Optional.<ICatalogItem> absent());

		if (newItem.isPresent()) {
			@SuppressWarnings("unchecked")
			Collection<ICatalogItem> items = (Collection<ICatalogItem>) tableViewer
					.getInput();
			items.add(newItem.get());
			tableViewer.refresh();
		}

	}

	public void editItem() {
		// obtain the Item in the column where the doubleClick happend
		ICatalogItem selectedItem = (ICatalogItem) tableViewer
				.getElementAt(tableViewer.getTable().getSelectionIndex());
		if (selectedItem == null) {
			return;
		}

        // Refresh the selected place with values from the database
        try {

            if (selectedItem instanceof Activity) {
                catalogService.refresh((Activity) selectedItem);
            }
            else if (selectedItem instanceof Current) {
                catalogService.refresh((Current) selectedItem);
            }
            else {
                catalogService.refresh((Target) selectedItem);
            }

            Optional<ICatalogItem> updatedItem = CatalogPartUtil.showWizard(context,
                    shellProvider.getShell(), Optional.of(selectedItem));

            if (updatedItem.isPresent()) {
                tableViewer.refresh();
            }
        }
        catch (DatabaseException e) {
            LOG.error("Could not retrieve the CatalogItem from database.", e);
            showDBConnectionError();
        }
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
