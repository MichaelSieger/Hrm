package de.hswt.hrm.plant.ui.part;

import java.net.URL;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.xwt.IConstants;
import org.eclipse.e4.xwt.XWT;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.SWT;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.widgets.Form;
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
import de.hswt.hrm.plant.model.Plant;
import de.hswt.hrm.plant.service.PlantService;
import de.hswt.hrm.plant.ui.filter.PlantFilter;
import de.hswt.hrm.scheme.ui.part.SchemePart;

import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class PlantPart {

    private final static Logger LOG = LoggerFactory.getLogger(PlantPart.class);

    @Inject
    private PlantService plantService;

    @Inject
    private IShellProvider shellProvider;

    @Inject
    private IEclipseContext context;

    private FormToolkit toolkit = new FormToolkit(Display.getDefault());

    private Table table;
    private Text searchText;
    private TableViewer tableViewer;

    private IContributionItem addContribution;
    private IContributionItem editContribution;
    private IContributionItem editSchemeContribution;

    private Section plantsHeaderSection;
    private Composite composite;
    private TabFolder tabFolder;
    private TabItem plantsTab;
    private TabItem schemeTab;

    private Form form;

    public PlantPart() {
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
        toolkit.setBorderStyle(SWT.BORDER);
        toolkit.adapt(parent);
        toolkit.paintBordersFor(parent);
        parent.setLayout(new FillLayout(SWT.HORIZONTAL));

        form = toolkit.createForm(parent);
        form.getHead().setOrientation(SWT.RIGHT_TO_LEFT);
        form.setSeparatorVisible(true);
        form.getBody().setBackgroundMode(SWT.INHERIT_FORCE);
        toolkit.paintBordersFor(form);
        form.setText("Plants");
        toolkit.decorateFormHeading(form);
        createActions();

        FillLayout fillLayout = new FillLayout(SWT.HORIZONTAL);
        fillLayout.marginHeight = 5;
        fillLayout.marginWidth = 5;
        form.getBody().setLayout(fillLayout);

        tabFolder = new TabFolder(form.getBody(), SWT.NONE);
        tabFolder.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                if (tabFolder.getItem(tabFolder.getSelectionIndex()).equals(plantsTab)) {
                    showPlantActions(true);
                }
                else {
                    showPlantActions(false);
                }
            }
        });
        tabFolder.setBackgroundMode(SWT.INHERIT_FORCE);
        toolkit.adapt(tabFolder);
        toolkit.paintBordersFor(tabFolder);

        plantsTab = new TabItem(tabFolder, SWT.NONE);
        plantsTab.setText("Plants");

        plantsHeaderSection = toolkit.createSection(tabFolder, Section.TITLE_BAR);
        plantsTab.setControl(plantsHeaderSection);
        toolkit.paintBordersFor(plantsHeaderSection);
        plantsHeaderSection.setExpanded(true);
        FormUtil.initSectionColors(plantsHeaderSection);

        composite = toolkit.createComposite(plantsHeaderSection, SWT.NONE);
        toolkit.paintBordersFor(composite);
        plantsHeaderSection.setClient(composite);
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
        toolkit.adapt(searchText, true, true);

        tableViewer = new TableViewer(composite, SWT.BORDER | SWT.FULL_SELECTION);
        tableViewer.addDoubleClickListener(new IDoubleClickListener() {
            public void doubleClick(DoubleClickEvent event) {
                editPlant();
            }
        });
        table = tableViewer.getTable();
        table.setSize(214, 221);
        table.setLinesVisible(true);
        table.setHeaderVisible(true);
        table.setLayoutData(LayoutUtil.createFillData());
        toolkit.paintBordersFor(table);

        schemeTab = new TabItem(tabFolder, SWT.NONE);
        schemeTab.setText("Scheme");

        // TODO add scheme part here

        URL url = SchemePart.class.getClassLoader().getResource(
                "de/hswt/hrm/scheme/ui/part/SchemePart" + IConstants.XWT_EXTENSION_SUFFIX);

        Composite c;
        try {
            c = (Composite) XWT.load(tabFolder, url);
            schemeTab.setControl(c);

        }
        catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        initializeTable();
        refreshTable();

        if (plantService == null) {
            LOG.error("PlantService not injected to PlantPart.");
        }
    }

    private void createActions() {
        // TODO translate
        Action schemeAction = new Action("Edit Scheme") {
            @Override
            public void run() {
                super.run();
                editScheme();
            }
        };
        schemeAction.setDescription("Edit the scheme of the selected plant.");
        editSchemeContribution = new ActionContributionItem(schemeAction);
        form.getToolBarManager().add(editSchemeContribution);

        Action editAction = new Action("Edit") {
            @Override
            public void run() {
                super.run();
                editPlant();
            }
        };
        editAction.setDescription("Edit an exisitng plant.");
        editContribution = new ActionContributionItem(editAction);
        form.getToolBarManager().add(editContribution);

        Action addAction = new Action("Add") {
            @Override
            public void run() {
                super.run();
                addPlant();
            }
        };
        addAction.setDescription("Add's a new plant.");
        addContribution = new ActionContributionItem(addAction);
        form.getToolBarManager().add(addContribution);

        form.getToolBarManager().update(true);
    }

    private void showPlantActions(boolean show) {
        addContribution.setVisible(show);
        editContribution.setVisible(show);
        editSchemeContribution.setVisible(show);
        form.getToolBarManager().update(true);
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
            tableViewer.setInput(plantService.findAll());
        }
        catch (DatabaseException e) {
            LOG.error("Unable to retrieve list of plants.", e);
            showDBConnectionError();
        }
    }

    private void showDBConnectionError() {
        // TODO translate
        MessageDialog.openError(shellProvider.getShell(), "Connection Error",
                "Could not load plants from Database.");
    }

    private void updateTableFilter(String filterString) {
        PlantFilter filter = (PlantFilter) tableViewer.getFilters()[0];
        filter.setSearchString(filterString);
        tableViewer.refresh();
    }

    private void initializeTable() {
        List<ColumnDescription<Plant>> columns = PlantPartUtil.getColumns();

        // Create columns in tableviewer
        TableViewerController<Plant> filler = new TableViewerController<>(tableViewer);
        filler.createColumns(columns);

        // Enable column selection
        filler.createColumnSelectionMenu();

        // Enable sorting
        ColumnComparator<Plant> comparator = new ColumnComparator<>(columns);
        filler.enableSorting(comparator);

        // Add dataprovider that handles our collection
        tableViewer.setContentProvider(ArrayContentProvider.getInstance());

        // Enable filtering
        tableViewer.addFilter(new PlantFilter());
    }

    private void addPlant() {
        Optional<Plant> newPlant = PlantPartUtil.showWizard(context, shellProvider.getShell(),
                Optional.<Plant> absent());

        @SuppressWarnings("unchecked")
        Collection<Plant> contacs = (Collection<Plant>) tableViewer.getInput();
        if (newPlant.isPresent()) {
            contacs.add(newPlant.get());
            tableViewer.refresh();
        }
    }

    /**
     * This method is called whenever a doubleClick onto the TableViewer occurs. It obtains the
     * Plant from the selected column of the TableViewer. The Plant is passed to the PlantWizard.
     * When the Wizard has finished, the Plant will be updated in the Database
     */
    private void editPlant() {

        // obtain the place in the column where the doubleClick happend
        Plant selectedPlant = (Plant) tableViewer.getElementAt(tableViewer.getTable()
                .getSelectionIndex());
        if (selectedPlant == null) {
            return;
        }

        // Refresh the selected place with values from the database
        try {
            plantService.refresh(selectedPlant);
            Optional<Plant> updatedPlant = PlantPartUtil.showWizard(context,
                    shellProvider.getShell(), Optional.of(selectedPlant));

            if (updatedPlant.isPresent()) {
                tableViewer.refresh();
            }
        }
        catch (DatabaseException e) {
            LOG.error("Could not retrieve the plant from database.", e);
            showDBConnectionError();
        }
    }

    private void editScheme() {
        if (table.getSelectionIndex() < 0) {
            return;
        }

        // TODO init scheme with selected plant

        tabFolder.setSelection(schemeTab);
    }

}
