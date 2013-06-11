package de.hswt.hrm.component.ui.part;

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
import de.hswt.hrm.component.model.Category;
import de.hswt.hrm.component.service.CategoryService;
import de.hswt.hrm.component.ui.filter.CategoryFilter;


public class CategoryPart {

	private final static Logger LOG = LoggerFactory.getLogger(CategoryPart.class);

	@Inject
	private CategoryService categoryService;

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

	public CategoryPart() {
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
		form.setText("Categories");
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
				editCategory();
			}
		});
		table = tableViewer.getTable();
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		table.setLayoutData(LayoutUtil.createFillData());
		toolkit.paintBordersFor(table);
		form.getToolBarManager().update(true);

		initializeTable();
		refreshTable();

		if (categoryService == null) {
			LOG.error("CategoryService not injected to CategoryPart.");
		}
	}

	private void createActions() {
		{
			editAction = new Action("Edit") {
				@Override
				public void run() {
					super.run();
					editCategory();
				}
			};
			editAction.setDescription("Edit an exisitng category.");
		}
		{
			addAction = new Action("Add") {
				@Override
				public void run() {
					super.run();
					addCategory();
				}
			};
			addAction.setDescription("Add's a new category.");
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

	private void refreshTable() {
		try {
			tableViewer.setInput(categoryService.findAll());
		} catch (DatabaseException e) {
			LOG.error("Unable to retrieve list of categories.", e);
			showDBConnectionError();
		}
	}

	private void showDBConnectionError() {
		// TODO translate
		MessageDialog.openError(shellProvider.getShell(), "Connection Error",
				"Could not load categories from Database.");
//        MessageDialog.openError(event.display.getActiveShell(), "Connection Error",
//                "Gewählte Kategorie kann nicht aus der Datenbank aktualisiert werden.");
	}

	private void updateTableFilter(String filterString) {
		CategoryFilter filter = (CategoryFilter) tableViewer.getFilters()[0];
		filter.setSearchString(filterString);
		tableViewer.refresh();
	}

	private void initializeTable() {
	    List<ColumnDescription<Category>> columns = CategoryPartUtil.getColumns();
	    // Create columns in TableViewer
	    TableViewerController<Category> filler = new TableViewerController<>(tableViewer);
	    filler.createColumns(columns);
	    
	    // Enable columns selection
	    filler.createColumnSelectionMenu();
	    
	    // Enable sorting
	    ColumnComparator<Category> comparator = new ColumnComparator<>(columns);
	    filler.enableSorting(comparator);
	    
	    // Add data provider which handles our collection
	    tableViewer.setContentProvider(ArrayContentProvider.getInstance());
	    
	    // Enable filtering
	    tableViewer.addFilter(new CategoryFilter());
	}
	
    /**
     * Called whenever the add button is pressed.
     */
    public void addCategory() {
        Optional<Category> newCat = CategoryPartUtil.showWizard(context,
                shellProvider.getShell(), Optional.<Category> absent());

        @SuppressWarnings("unchecked")
        Collection<Category> categories = (Collection<Category>) tableViewer.getInput();
        if (newCat.isPresent()) {
            categories.add(newCat.get());
            tableViewer.refresh();
        }
    }
    
    /**
     * Called whenever a doubleClick into the TableViewer occurs. It obtains
     * the category from the selected line if the TableViewer. The category
     * is passed to the CategoryWizard. When the Wizard has finished, the
     * category will be updated in the database.
     */
    public void editCategory() {
     
        // Obtain the category in the line where the doubleClick happened
        Category selectedCat = (Category) tableViewer.getElementAt(tableViewer.getTable().getSelectionIndex());
        if (selectedCat == null) {
            return;
        }
        try {
            categoryService.refresh(selectedCat);
            Optional<Category> updatedCat = CategoryPartUtil.showWizard(context,
                   shellProvider.getShell(), Optional.of(selectedCat));
            if (updatedCat.isPresent()) {
                tableViewer.refresh();
            }
        } catch (DatabaseException e) {
            LOG.error("Could not retrieve the category from database.", e);
            showDBConnectionError();
        }
    }
}
