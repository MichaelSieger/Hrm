package de.hswt.hrm.misc.ui.prioritycomposite;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;

import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.database.exception.ElementNotFoundException;
import de.hswt.hrm.common.database.exception.SaveException;
import de.hswt.hrm.common.ui.swt.table.ColumnDescription;
import de.hswt.hrm.common.ui.swt.table.TableViewerController;
import de.hswt.hrm.common.ui.swt.utils.SWTResourceManager;
import de.hswt.hrm.i18n.I18n;
import de.hswt.hrm.i18n.I18nFactory;
import de.hswt.hrm.misc.priority.model.Priority;
import de.hswt.hrm.misc.priority.service.PriorityService;

public class PriorityComposite extends Composite {

    private final static Logger LOG = LoggerFactory.getLogger(PriorityComposite.class);
    private static final I18n I18N = I18nFactory.getI18n(PriorityComposite.class);
     @Inject
     private PriorityService prioService;

    @Inject
    private IShellProvider shellProvider;

    @Inject
    private IEclipseContext context;

    private Table table;
    private TableViewer tableViewer;

    private Composite composite;

    private Collection<Priority> prios;
    private Button minusButton;

    /**
     * Do not use this constructor when instantiate this composite! It is only included to make the
     * WindowsBuilder working.
     * 
     * @param parent
     * @param style
     */
    private PriorityComposite(Composite parent, int style) {
        super(parent, style);
        createControls();
    }

    /**
     * Create the composite.
     * 
     * @param parent
     */
    public PriorityComposite(Composite parent) {
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
        composite.setLayout(new GridLayout(2, false));
        new Label(composite, SWT.NONE);

        tableViewer = new TableViewer(composite, SWT.BORDER | SWT.FULL_SELECTION);
        tableViewer.addDoubleClickListener(new IDoubleClickListener() {
            public void doubleClick(DoubleClickEvent event) {
                editPriority();
            }
        });
        table = tableViewer.getTable();
        GridData gd_table = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 2);
        gd_table.widthHint = 465;
        gd_table.heightHint = 265;
        table.setLayoutData(gd_table);
        table.setLinesVisible(true);
        table.setHeaderVisible(true);


        initializeTable();
        refreshTable();
        if (prioService == null) {
        	 LOG.error("PriorityService not injected to PriorityPart.");
         }
    }

    private void refreshTable() {
 
          try {
        	  this.prios = prioService.findAll();
        	  tableViewer.setInput(this.prios);
         }
         catch (DatabaseException e) {
        	 LOG.error("Unable to retrieve list of Priorities.", e);
        	 showDBConnectionError();
         }
    }

    private void initializeTable() {
        List<ColumnDescription<Priority>> columns = PriorityPartUtil.getColumns();

        // Create columns in tableviewer
        TableViewerController<Priority> filler = new TableViewerController<>(tableViewer);
        filler.createColumns(columns);

        // Enable column selection
        filler.createColumnSelectionMenu();

        tableViewer.setComparator(new ViewerComparator() {
            @Override
            public int compare(Viewer viewer, Object e1, Object e2) {
                if (e1 instanceof Priority && e2 instanceof Priority) {
                    String prio1 = Integer.toString(((Priority) e1).getPriority());
                    String prio2 = Integer.toString(((Priority) e2).getPriority());
                    return prio1.compareToIgnoreCase(prio2);
                }
                throw new IllegalArgumentException("Not comparable: " + e1 + " " + e2);
            }
        });

        // Enable sorting
        // ColumnComparator<Priority> comperator = new ColumnComparator<>(columns);
        // filler.enableSorting(comperator);

        // Add dataprovider that handles our collection
        tableViewer.setContentProvider(ArrayContentProvider.getInstance());

    }

    private void showDBConnectionError() {
        MessageDialog.openError(shellProvider.getShell(), I18N.tr("Connection Error"),
                I18N.tr("Could not load priorites from Database."));
    }

    /**
     * This Event is called whenever the add button is pressed.
     * 
     * @param event
     */

    public void addPriority() {
        Priority prio = null;

        Optional<Priority> newPrio = PriorityPartUtil.showWizard(context, shellProvider.getShell(),
                Optional.fromNullable(prio));

        if (newPrio.isPresent()) {
            prios.add(newPrio.get());
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
    public void editPriority() {
        // obtain the contact in the column where the doubleClick happend
        Priority selectedPrio = (Priority) tableViewer.getElementAt(tableViewer.getTable()
                .getSelectionIndex());
        if (selectedPrio == null) {
            return;
        }
        try {
        	prioService.refresh(selectedPrio);
        	Optional<Priority> updatedEval = PriorityPartUtil.showWizard(context,
        			shellProvider.getShell(), Optional.of(selectedPrio));
        
        	if (updatedEval.isPresent()) {
        		tableViewer.refresh();
        	}
         }
         catch (DatabaseException e) {
        	 LOG.error("Could not retrieve the Priorities from database.", e);
        	 showDBConnectionError();
         }
    }

    public void movePriorityUp() {
    	  Priority selectedPrio = (Priority) tableViewer.getElementAt(tableViewer.getTable()
         .getSelectionIndex());
         if (selectedPrio == null) {
         return;
         }
         Priority tempPrio = null;
        
         if(selectedPrio.getPriority() > 1){
         try {
			for(Priority prio : prioService.findAll()){
			 if(prio.getPriority() == selectedPrio.getPriority()-1){
			 tempPrio = prio;
			 break;
			 }
			 }
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         selectedPrio.setPriority(selectedPrio.getPriority()-1);
         tempPrio.setPriority(tempPrio.getPriority() +1);
        
         try {
			prioService.update(selectedPrio);
		} catch (ElementNotFoundException | SaveException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         try {
			prioService.update(tempPrio);
		} catch (ElementNotFoundException | SaveException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         }
         refreshTable();
    }

    public void movePriorityDown() {

        // JUST TESTING
//         Priority a =
//         (Priority)tableViewer.getElementAt(tableViewer.getTable().getSelectionIndex());
//         Priority b = null;
//         int temp = a.getPriority();
//         for(Priority prio : prios){
//         if(prio.getPriority() == a.getPriority()+1){
//         b = prio;
//         }
//         }
//         a.setPriority(a.getPriority()+1);
//         b.setPriority(b.getPriority() -1);
        
        
//         tableViewer.refresh();
         // FIXME delete upper part and uncomment part under 
        Priority selectedPrio = (Priority) tableViewer.getElementAt(tableViewer.getTable()
         .getSelectionIndex());
         if (selectedPrio == null) {
         return;
         }
         Priority tempPrio = null;
        
         try {
			if(selectedPrio.getPriority() < prioService.findAll().size()){
			 for(Priority prio : prioService.findAll()){
			 if(prio.getPriority() == selectedPrio.getPriority()+1){
			 tempPrio = prio;
			 break;
			 }
			 }
			 selectedPrio.setPriority(selectedPrio.getPriority()+1);
			 tempPrio.setPriority(tempPrio.getPriority() -1);
			
			 prioService.update(selectedPrio);
			 prioService.update(tempPrio);
			 }
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
         refreshTable();
    }
}
