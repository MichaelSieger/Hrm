package de.hswt.hrm.contact.ui.part;

import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;

import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.ui.swt.constants.SearchFieldConstants;
import de.hswt.hrm.common.ui.swt.layouts.LayoutUtil;
import de.hswt.hrm.common.ui.swt.table.ColumnComparator;
import de.hswt.hrm.common.ui.swt.table.ColumnDescription;
import de.hswt.hrm.common.ui.swt.table.TableViewerController;
import de.hswt.hrm.contact.model.Contact;
import de.hswt.hrm.contact.service.ContactService;
import de.hswt.hrm.contact.ui.filter.ContactFilter;

public class ContactComposite extends Composite {

    public static final int TABLE_SELCTION_EVENT = SWT.Selection + 12341234;

    private final static Logger LOG = LoggerFactory.getLogger(ContactComposite.class);

    private Text searchText;

    private TableViewer tableViewer;

    private Table table;

    private boolean allowEditing = true;

    @Inject
    private ContactService placeService;

    @Inject
    private IEclipseContext context;

    @Inject
    private IShellProvider shellProvider;

    /**
     * Create the composite.
     * 
     * @param parent
     * @param style
     */
    public ContactComposite(Composite parent, int style) {
        super(parent, style);
        this.setLayout(new FillLayout());
    }

    @PostConstruct
    public void createControls() {
        Composite composite = new Composite(this, SWT.NONE);
        composite.setLayout(new GridLayout(1, false));

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
                editPlace();
            }
        });

        table = tableViewer.getTable();
        table.setLinesVisible(true);
        table.setHeaderVisible(true);
        table.setLayoutData(LayoutUtil.createFillData());

        initializeTable();
        refreshTable();
    }

    private void refreshTable() {
        try {
            tableViewer.setInput(placeService.findAll());
        }
        catch (DatabaseException e) {
            LOG.error("Unable to retrieve list of places.", e);
            showDBConnectionError();
        }
    }

    private void showDBConnectionError() {
        // TODO translate
        MessageDialog.openError(shellProvider.getShell(), "Connection Error",
                "Could not load places from Database.");
    }

    private void updateTableFilter(String filterString) {
        ContactFilter filter = (ContactFilter) tableViewer.getFilters()[0];
        filter.setSearchString(filterString);
        tableViewer.refresh();
    }

    private void initializeTable() {
        List<ColumnDescription<Contact>> columns = ContactPartUtil.getCompositeColumns();

        // Create columns in tableviewer
        TableViewerController<Contact> filler = new TableViewerController<>(tableViewer);
        filler.createColumns(columns);

        // Enable column selection
        filler.createColumnSelectionMenu();

        // Enable sorting
        ColumnComparator<Contact> comparator = new ColumnComparator<>(columns);
        filler.enableSorting(comparator);

        // Add dataprovider that handles our collection
        tableViewer.setContentProvider(ArrayContentProvider.getInstance());

        // Enable filtering
        tableViewer.addFilter(new ContactFilter());
    }

    @SuppressWarnings("unchecked")
    public void addPlace() {
        Optional<Contact> newPlace = ContactPartUtil.showWizard(context, shellProvider.getShell(),
                Optional.<Contact> absent());

        if (newPlace.isPresent()) {
            Collection<Contact> places = (Collection<Contact>) tableViewer.getInput();
            places.add(newPlace.get());
            tableViewer.refresh();
        }
    }

    public void editPlace() {
        if (!allowEditing) {
            return;
        }
        // obtain the place in the column where the doubleClick happend
        Contact selectedPlace = (Contact) tableViewer.getElementAt(tableViewer.getTable()
                .getSelectionIndex());
        if (selectedPlace == null) {
            return;
        }

        // Refresh the selected place with values from the database
        try {
            placeService.refresh(selectedPlace);
            Optional<Contact> updatedPlace = ContactPartUtil.showWizard(context,
                    shellProvider.getShell(), Optional.of(selectedPlace));

            if (updatedPlace.isPresent()) {
                tableViewer.refresh();
            }
        }
        catch (DatabaseException e) {
            LOG.error("Could not retrieve the place from database.", e);
            showDBConnectionError();
        }
    }

    @Override
    protected void checkSubclass() {
    }

    public void setSelection(Contact place) {
        tableViewer.setSelection(new StructuredSelection(place), true);
    }

    public Contact getSelectedPlace() {
        return (Contact) ((IStructuredSelection) tableViewer.getSelection()).getFirstElement();
    }

    public void setAllowEditing(boolean allowEditing) {
        this.allowEditing = allowEditing;
    }

    public boolean isAllowEditing() {
        return allowEditing;
    }

    public void addSelectionChangedListener(ISelectionChangedListener listener) {
        tableViewer.addSelectionChangedListener(listener);
    }

    public void removeSelectionChangedListener(ISelectionChangedListener listener) {
        tableViewer.removeSelectionChangedListener(listener);
    }
}
