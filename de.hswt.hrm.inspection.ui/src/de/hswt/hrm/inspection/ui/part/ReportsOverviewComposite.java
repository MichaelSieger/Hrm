package de.hswt.hrm.inspection.ui.part;

import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;

import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.ui.swt.constants.SearchFieldConstants;
import de.hswt.hrm.common.ui.swt.forms.FormUtil;
import de.hswt.hrm.common.ui.swt.layouts.LayoutUtil;
import de.hswt.hrm.common.ui.swt.table.ColumnComparator;
import de.hswt.hrm.common.ui.swt.table.ColumnDescription;
import de.hswt.hrm.common.ui.swt.table.Direction;
import de.hswt.hrm.common.ui.swt.table.TableViewerController;
import de.hswt.hrm.inspection.model.Inspection;
import de.hswt.hrm.inspection.service.InspectionService;
import de.hswt.hrm.inspection.ui.filter.InspectionFilter;

public class ReportsOverviewComposite extends Composite {

    @Inject
    private InspectionService inspectionService;

    @Inject
    private IEclipseContext context;

    @Inject
    private IShellProvider shellProvider;

    private FormToolkit toolkit = new FormToolkit(Display.getDefault());

    private Text searchText;

    private TableViewer tableViewer;

    private Table table;

    private InspectionFilter searchFilter = new InspectionFilter();

    private Inspection selectedInspection;

    public Inspection getSelectedInspection() {
        return selectedInspection;
    }

    private static final Logger LOG = LoggerFactory.getLogger(ReportsOverviewComposite.class);

    /**
     * Create the composite.
     * 
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

        searchText = new Text(overviewComposite, SWT.BORDER | SWT.SEARCH | SWT.ICON_SEARCH
                | SWT.CANCEL | SWT.ICON_CANCEL);
        searchText.setMessage(SearchFieldConstants.DEFAULT_SEARCH_STRING);
        searchText.setLayoutData(LayoutUtil.createHorzFillData());
        searchText.addModifyListener(new ModifyListener() {
            public void modifyText(ModifyEvent e) {
                updateSearchTableFilter(searchText.getText());
            }
        });
        toolkit.adapt(searchText, true, true);

        tableViewer = new TableViewer(overviewComposite, SWT.BORDER | SWT.HIDE_SELECTION);
        table = tableViewer.getTable();
        table.setLinesVisible(true);
        table.setHeaderVisible(true);
        toolkit.paintBordersFor(table);
        table.setLayoutData(LayoutUtil.createFillData());
        tableViewer.addSelectionChangedListener(new ISelectionChangedListener() {
            @Override
            public void selectionChanged(SelectionChangedEvent event) {
                IStructuredSelection selection = (IStructuredSelection) tableViewer.getSelection();
                Inspection firstElement = (Inspection) selection.getFirstElement();
                selectedInspection = firstElement;

            }

        });

        initializeTable();
        refreshTable();

        // data for table
        // report title
        // inspection date
        // next inspection date
        // only names of customer, requester, controller
        // name of plant

        // to add a new report only request only the name of the report
    }

    private void refreshTable() {

        try {

            tableViewer.setInput(inspectionService.findAll());
        }
        catch (DatabaseException e) {
            LOG.error("Unable to retrieve list of catalog items.", e);
            showDBConnectionError();
        }
    }

    private void showDBConnectionError() {
        // TODO translate
        MessageDialog.openError(shellProvider.getShell(), "Connection Error",
                "Could not load inspections from Database.");
    }

    private void updateSearchTableFilter(String filterString) {
        InspectionFilter filter = (InspectionFilter) tableViewer.getFilters()[0];
        filter.setSearchString(filterString);
        tableViewer.refresh();
    }

    private void initializeTable() {

        List<ColumnDescription<Inspection>> columns = InspectionPartUtil.getColumns();

        // Create columns in tableviewer
        TableViewerController<Inspection> filler = new TableViewerController<>(tableViewer);
        filler.createColumns(columns);

        // Enable column selection
        filler.createColumnSelectionMenu();

        // Enable sorting
        ColumnComparator<Inspection> comparator = new ColumnComparator<>(columns);
        comparator.setCurrentColumnIndex(0);
        comparator.setDirection(Direction.ASCENDING);
        filler.enableSorting(comparator);

        // Add dataprovider that handles our collection
        tableViewer.setContentProvider(ArrayContentProvider.getInstance());

        // Enable filtering
        tableViewer.addFilter(searchFilter);
        
       
    }

    public void addInspection() {
        Optional<Inspection> newInspection = InspectionPartUtil.showInspectionCreateWizard(context,
                shellProvider.getShell());

        if (newInspection.isPresent()) {
            @SuppressWarnings("unchecked")
            Collection<Inspection> items = (Collection<Inspection>) tableViewer.getInput();
            items.add(newInspection.get());
            tableViewer.refresh();
        }
    }

    @Override
    public void dispose() {
        if (toolkit != null) {
            toolkit.dispose();
        }
        super.dispose();
    }
    
    public void setInspection(Inspection inspection){
    	this.selectedInspection = inspection;
    }

    @Override
    protected void checkSubclass() {
    }

}
