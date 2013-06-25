package de.hswt.hrm.misc.ui.part;

import java.io.IOException;
import java.nio.file.Path;
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
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;

import de.hswt.hrm.common.Config;
import de.hswt.hrm.common.Hrm;
import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.ui.swt.layouts.LayoutUtil;
import de.hswt.hrm.common.ui.swt.table.ColumnComparator;
import de.hswt.hrm.common.ui.swt.table.ColumnDescription;
import de.hswt.hrm.common.ui.swt.table.TableViewerController;
import de.hswt.hrm.inspection.model.Layout;
import de.hswt.hrm.inspection.service.LayoutService;
import de.hswt.hrm.misc.reportPreference.model.ReportPreference;

public class ReportPreferencesComposite extends Composite {

    private final static Logger LOG = LoggerFactory.getLogger(ReportPreferencesComposite.class);

     @Inject
     private LayoutService prefService;

    @Inject
    private IShellProvider shellProvider;

    @Inject
    private IEclipseContext context;

    private Table table;
    private TableViewer tableViewer;

    private Composite composite;

    private Collection<Layout> preferences;
    private Text directoryText;
    private Label lblNewLabel;
    private Button browseButton;
    private Label stylesLabel;

    /**
     * Do not use this constructor when instantiate this composite! It is only included to make the
     * WindowsBuilder working.
     * 
     * @param parent
     * @param style
     */
    private ReportPreferencesComposite(Composite parent, int style) {
        super(parent, style);
        createControls();
    }

    /**
     * Create the composite.
     * 
     * @param parent
     */
    public ReportPreferencesComposite(Composite parent) {
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
        composite.setLayout(new GridLayout(3, false));

        lblNewLabel = new Label(composite, SWT.NONE);
        lblNewLabel.setLayoutData(LayoutUtil.createLeftCenteredGridData());
        lblNewLabel.setText("Standard directory");

        directoryText = new Text(composite, SWT.BORDER | SWT.READ_ONLY);
        directoryText.setToolTipText("A local directory where all the reports are created.");
        directoryText.setLayoutData(LayoutUtil.createHorzCenteredFillData());

        browseButton = new Button(composite, SWT.NONE);
        browseButton
                .setToolTipText("Browse for a local directory where all the reports are created.");
        browseButton.setText("Browse ...");
        browseButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                setStandardReportDirectory();
            }
        });

        stylesLabel = new Label(composite, SWT.NONE);
        stylesLabel.setText("Style");
        stylesLabel.setLayoutData(LayoutUtil.createLeftGridData());
        
        tableViewer = new TableViewer(composite, SWT.BORDER | SWT.FULL_SELECTION);
        tableViewer.addDoubleClickListener(new IDoubleClickListener() {
            public void doubleClick(DoubleClickEvent event) {
                editPreference();
            }
        });
        table = tableViewer.getTable();
        table.setLinesVisible(true);
        table.setHeaderVisible(true);
        table.setLayoutData(LayoutUtil.createFillData(2));

        initializeTable();
        refreshTable();
        
        Config config = Config.getInstance();
        String dir = config.getProperty(Config.Keys.REPORT_STYLE_FOLDER);
        if(dir != null){        
        	directoryText.setText(dir);
        }

        if (prefService == null) {
        	 LOG.error("LayoutService not injected to LayoutPart.");
         }
    }

    private void refreshTable() {
    	try {
	         this.preferences = prefService.findAll();
	         tableViewer.setInput(this.preferences);
	         }
	    catch (DatabaseException e) {
	         LOG.error("Unable to retrieve list of Layouts.", e);
	         showDBConnectionError();
        }
    }

    private void initializeTable() {
        List<ColumnDescription<Layout>> columns = ReportPreferenceUtil.getColumns();

        // Create columns in tableviewer
        TableViewerController<Layout> filler = new TableViewerController<>(tableViewer);
        filler.createColumns(columns);

        // Enable column selection
        filler.createColumnSelectionMenu();

        // Enable sorting
        ColumnComparator<Layout> comperator = new ColumnComparator<>(columns);
        filler.enableSorting(comperator);

        // Add dataprovider that handles our collection
        tableViewer.setContentProvider(ArrayContentProvider.getInstance());

    }

    private void showDBConnectionError() {
        // TODO translate
        MessageDialog.openError(shellProvider.getShell(), "Connection Error",
                "Could not load preferences from Database.");
    }

    /**
     * This Event is called whenever the add button is pressed.
     * 
     * @param event
     */

    public void addPrefernence() {
        Layout preference = null;

        Optional<Layout> newPreference = ReportPreferenceUtil.showWizard(context,
                shellProvider.getShell(), Optional.fromNullable(preference));

        if (newPreference.isPresent()) {
            preferences.add(newPreference.get());
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
    public void editPreference() {
        // obtain the contact in the column where the doubleClick happend
        Layout selectedPreference = (Layout) tableViewer
                .getElementAt(tableViewer.getTable().getSelectionIndex());
        if (selectedPreference == null) {
            return;
        }
        try {
		     prefService.refresh(selectedPreference);
		     Optional<Layout> updatedPreference = ReportPreferenceUtil.showWizard(context,
		     shellProvider.getShell(), Optional.of(selectedPreference));
		    
		     if (updatedPreference.isPresent()) {
		    	 tableViewer.refresh();
		     }
		}
        catch (DatabaseException e) {
         LOG.error("Could not retrieve the Preferences from database.", e);
         showDBConnectionError();
        }
    }

    private void setStandardReportDirectory() {
        DirectoryDialog dialog = new DirectoryDialog(this.getShell());
        dialog.setText("Report standard directory selection");
        dialog.setMessage("Select a directory as root of all created reports.");
        String dir = dialog.open();
        if (dir != null) {
            directoryText.setText(dir);
        
	        Config cfg = Config.getInstance();
	        cfg.setProperty(Config.Keys.REPORT_STYLE_FOLDER, dir);
	
	        Path configPath = Hrm.getConfigPath();
	        try {
	            cfg.store(configPath, true, true);
	        }
	        catch (IOException e) {
	            e.printStackTrace();
	        }
        }
    }
}
