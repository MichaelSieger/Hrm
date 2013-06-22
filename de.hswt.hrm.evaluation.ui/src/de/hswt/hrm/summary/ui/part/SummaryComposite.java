package de.hswt.hrm.summary.ui.part;

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
import org.eclipse.swt.widgets.Display;
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
import de.hswt.hrm.summary.model.Summary;
import de.hswt.hrm.summary.service.SummaryService;
import de.hswt.hrm.summary.ui.filter.SummaryFilter;

public class SummaryComposite extends Composite {

	private final static Logger LOG = LoggerFactory.getLogger(SummaryComposite.class);

    @Inject
    private SummaryService evalService;

    @Inject
    private IShellProvider shellProvider;

    @Inject
    private IEclipseContext context;

    private Table table;
    private Text searchText;
    private TableViewer tableViewer;

    private Composite composite;

    private Collection<Summary> evaluations;

	/**
	 * Do not use this constructor when instantiate this composite! It is only
	 * included to make the WindowsBuilder working.
	 * 
	 * @param parent
	 * @param style
	 */
    private SummaryComposite(Composite parent, int style) {
        super(parent, style);
        createControls();
    }

    /**
	 * Create the composite.
	 * 
	 * @param parent
	 */
	public SummaryComposite(Composite parent) {
		super(parent, SWT.NONE);
	}

    /**
     * Create contents of the view part.
     */
    @PostConstruct
    public void createControls() {
        this.setLayout(new FillLayout());
		this.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));

        composite = new Composite(this, SWT.NONE);
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
                editEvaluation();
            }
        });
        table = tableViewer.getTable();
        table.setLinesVisible(true);
        table.setHeaderVisible(true);
        table.setLayoutData(LayoutUtil.createFillData());

        initializeTable();
        refreshTable();

        if (evalService == null) {
            LOG.error("EvaluationService not injected to EvaluationPart.");
        }
    }

    private void refreshTable() {
        try {
            this.evaluations = evalService.findAll();
            tableViewer.setInput(this.evaluations);
        }
        catch (DatabaseException e) {
            LOG.error("Unable to retrieve list of Evaluations.", e);
            showDBConnectionError();
        }
    }

    private void initializeTable() {
        List<ColumnDescription<Summary>> columns = SummaryPartUtil.getColumns();

        // Create columns in tableviewer
        TableViewerController<Summary> filler = new TableViewerController<>(tableViewer);
        filler.createColumns(columns);

        // Enable column selection
        filler.createColumnSelectionMenu();

        // Enable sorting
        ColumnComparator<Summary> comperator = new ColumnComparator<>(columns);
        filler.enableSorting(comperator);

        // Add dataprovider that handles our collection
        tableViewer.setContentProvider(ArrayContentProvider.getInstance());

        // Enable filtering
        tableViewer.addFilter(new SummaryFilter());
    }

    private void showDBConnectionError() {
        // TODO translate
        MessageDialog.openError(shellProvider.getShell(), "Connection Error",
                "Could not load evaluations from Database.");
    }

    private void updateTableFilter(String filterString) {
        SummaryFilter filter = (SummaryFilter) tableViewer.getFilters()[0];
        filter.setSearchString(filterString);
        tableViewer.refresh();
    }

    /**
     * This Event is called whenever the add button is pressed.
     * 
     * @param event
     */

    public void addEvaluation() {
        Summary eval = null;

        Optional<Summary> newEval = SummaryPartUtil.showWizard(context,
                shellProvider.getShell(), Optional.fromNullable(eval));

        if (newEval.isPresent()) {
            evaluations.add(newEval.get());
            tableViewer.refresh();
        }
    }

    /**
     * This method is called whenever a doubleClick onto the Tableviewer occurs. It obtains the
     * summaryfrom the selected column of the TableViewer. The Contact is passed to the
     * EvaluationWizard. When the Wizard has finished, the contact will be updated in the Database
     * 
     * @param event
     *            Event which occured within SWT
     */
    public void editEvaluation() {
        // obtain the contact in the column where the doubleClick happend
        Summary selectedEval = (Summary) tableViewer.getElementAt(tableViewer.getTable()
                .getSelectionIndex());
        if (selectedEval == null) {
            return;
        }
        try {
            evalService.refresh(selectedEval);
            Optional<Summary> updatedEval = SummaryPartUtil.showWizard(context,
                    shellProvider.getShell(), Optional.of(selectedEval));

            if (updatedEval.isPresent()) {
                tableViewer.refresh();
            }
        }
        catch (DatabaseException e) {
            LOG.error("Could not retrieve the evaluations from database.", e);
            showDBConnectionError();
        }
    }
}
