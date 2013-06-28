package de.hswt.hrm.component.ui.part;

import java.util.Collection;
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
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
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
import de.hswt.hrm.i18n.I18n;
import de.hswt.hrm.i18n.I18nFactory;

public class CategoryComposite extends Composite {

    private final static Logger LOG = LoggerFactory.getLogger(CategoryComposite.class);
    private static final I18n I18N = I18nFactory.getI18n(CategoryComposite.class);

    @Inject
    private CategoryService categoryService;

    @Inject
    private IShellProvider shellProvider;

    @Inject
    private IEclipseContext context;

    private Table table;
    private Text searchText;
    private TableViewer tableViewer;

    /**
     * Do not use this constructor when instantiate this composite! It is only included to make the
     * WindowsBuilder working.
     * 
     * @param parent
     * @param style
     */
    private CategoryComposite(Composite parent, int style) {
        super(parent, SWT.NONE);
        createControls();
    }

    /**
     * Create the composite.
     * 
     * @param parent
     */
    public CategoryComposite(Composite parent) {
        super(parent, SWT.NONE);
    }

    @PostConstruct
    private void createControls() {
        setLayout(new FillLayout());
        setBackground(getDisplay().getSystemColor(SWT.COLOR_WHITE));

        Composite composite = new Composite(this, SWT.NONE);
        composite.setLayout(new GridLayout());
        composite.setBackground(getDisplay().getSystemColor(SWT.COLOR_WHITE));

        searchText = new Text(composite, SWT.BORDER | SWT.SEARCH | SWT.ICON_SEARCH | SWT.CANCEL
                | SWT.ICON_CANCEL);
        searchText.setMessage(SearchFieldConstants.DEFAULT_SEARCH_STRING);
        searchText.addModifyListener(new ModifyListener() {
            public void modifyText(ModifyEvent e) {
                updateTableFilter(searchText.getText());
            }
        });
        searchText.setLayoutData(LayoutUtil.createHorzFillData());

        tableViewer = new TableViewer(composite, SWT.BORDER | SWT.FULL_SELECTION);
        tableViewer.addDoubleClickListener(new IDoubleClickListener() {
            public void doubleClick(DoubleClickEvent event) {
                editCategory();
            }
        });
        table = tableViewer.getTable();
        table.setLinesVisible(true);
        table.setHeaderVisible(true);
        table.setLayoutData(LayoutUtil.createFillData());

        initializeTable();
        refreshTable();

        if (categoryService == null) {
            LOG.error("CategoryService not injected to CategoryComposite.");
        }
    }
    
    private void refreshTable() {
        try {
            tableViewer.setInput(categoryService.findAll());
        }
        catch (DatabaseException e) {
            LOG.error("Unable to retrieve list of categories.", e);
            showDBConnectionError();
        }
    }

    private void showDBConnectionError() {
        MessageDialog.openError(shellProvider.getShell(), I18N.tr("Connection Error"),
                I18N.tr("Could not load categories from Database."));
    }

    private void updateTableFilter(String filterString) {
        CategoryFilter filter = (CategoryFilter) tableViewer.getFilters()[0];
        filter.setSearchString(filterString);
        tableViewer.refresh();
    }

    private void initializeTable() {
        List<ColumnDescription<Category>> columns = CategoryCompositeUtil.getColumns();
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
        Optional<Category> newCat = CategoryCompositeUtil.showWizard(context, shellProvider.getShell(),
                Optional.<Category> absent());

        @SuppressWarnings("unchecked")
        Collection<Category> categories = (Collection<Category>) tableViewer.getInput();
        if (newCat.isPresent()) {
            categories.add(newCat.get());
            tableViewer.refresh();
        }
    }

    /**
     * Called whenever a doubleClick into the TableViewer occurs. It obtains the category from the
     * selected line if the TableViewer. The category is passed to the CategoryWizard. When the
     * Wizard has finished, the category will be updated in the database.
     */
    public void editCategory() {

        // Obtain the category in the line where the doubleClick happened
        Category selectedCat = (Category) tableViewer.getElementAt(tableViewer.getTable()
                .getSelectionIndex());
        if (selectedCat == null) {
            return;
        }
        try {
            categoryService.refresh(selectedCat);
            Optional<Category> updatedCat = CategoryCompositeUtil.showWizard(context,
                    shellProvider.getShell(), Optional.of(selectedCat));
            if (updatedCat.isPresent()) {
                tableViewer.refresh();
            }
        }
        catch (DatabaseException e) {
            LOG.error("Could not retrieve the category from database.", e);
            showDBConnectionError();
        }
    }
    
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}
