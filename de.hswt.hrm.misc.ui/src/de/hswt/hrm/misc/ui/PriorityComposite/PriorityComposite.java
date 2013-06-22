package de.hswt.hrm.misc.ui.PriorityComposite;

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
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
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
import de.hswt.hrm.misc.model.priorities.model.Priority;
import de.hswt.hrm.summary.model.Summary;
import de.hswt.hrm.summary.service.SummaryService;

import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.layout.GridData;

public class PriorityComposite extends Composite {

	private final static Logger LOG = LoggerFactory.getLogger(PriorityComposite.class);

//    @Inject
//    private PriorityService prioService;

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
	 * Do not use this constructor when instantiate this composite! It is only
	 * included to make the WindowsBuilder working.
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
                
        Button plusButton = new Button(composite, SWT.NONE);
        plusButton.setText("+");
        plusButton.addSelectionListener(new SelectionListener() {			
			@Override
			public void widgetSelected(SelectionEvent e) {
				movePriorityUp();				
			}			

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
        
        tableViewer = new TableViewer(composite, SWT.BORDER | SWT.FULL_SELECTION);
        tableViewer.addDoubleClickListener(new IDoubleClickListener() {
            public void doubleClick(DoubleClickEvent event) {
                editPriority();
            }
        });
        table = tableViewer.getTable();
        GridData gd_table = new GridData(SWT.CENTER, SWT.CENTER, false, true, 2, 1);
        gd_table.widthHint = 450;
        gd_table.heightHint = 265;
        table.setLayoutData(gd_table);
        table.setLinesVisible(true);
        table.setHeaderVisible(true);
        table.setLayoutData(LayoutUtil.createFillData());
        
        minusButton = new Button(composite, SWT.NONE);
        minusButton.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1));
        minusButton.setText("- ");
        minusButton.addSelectionListener(new SelectionListener() {			
			@Override
			public void widgetSelected(SelectionEvent e) {
				movePriorityDown();		
			}			

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});

        initializeTable();
        refreshTable();

// TODO       if (prioService == null) {
//            LOG.error("EvaluationService not injected to EvaluationPart.");
//        }
    }

    private void refreshTable() {
    	if(prios == null){
    		Priority a = new Priority("eins", "einsTExt", 3);
    	
	    	Priority b = new Priority("zwei", "einsTExt", 2);
	    	Priority c = new Priority("drei", "einsTExt", 1);
	    	Priority d = new Priority("vier", "einsTExt", 4);
	    	prios = new LinkedList<Priority>();
	    	prios.add(a);
	    	prios.add(b);
	    	prios.add(c);
	    	prios.add(d);
    	}
    	
    	tableViewer.setInput(this.prios);
    	
    	
//  TODO      try {
//            this.prios = prioService.findAll();
//            tableViewer.setInput(this.prios);
//        }
//        catch (DatabaseException e) {
//            LOG.error("Unable to retrieve list of Evaluations.", e);
//            showDBConnectionError();
//        }
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
//        ColumnComparator<Priority> comperator = new ColumnComparator<>(columns);
//        filler.enableSorting(comperator);

        // Add dataprovider that handles our collection
        tableViewer.setContentProvider(ArrayContentProvider.getInstance());

    }

    private void showDBConnectionError() {
        // TODO translate
        MessageDialog.openError(shellProvider.getShell(), "Connection Error",
                "Could not load priorites from Database.");
    }

    /**
     * This Event is called whenever the add button is pressed.
     * 
     * @param event
     */

    public void addPriority() {
        Priority prio = null;

        Optional<Priority> newPrio = PriorityPartUtil.showWizard(context,
                shellProvider.getShell(), Optional.fromNullable(prio));

        if (newPrio.isPresent()) {
            prios.add(newPrio.get());
            tableViewer.refresh();
        }
    }

    /**
     * This method is called whenever a doubleClick onto the Tableviewer occurs. It obtains the
     * evaluation from the selected column of the TableViewer. The Contact is passed to the
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
//   TODO     try {
//            prioService.refresh(selectedPrio);
//            Optional<Priority> updatedEval = PriorityPartUtil.showWizard(context,
//                    shellProvider.getShell(), Optional.of(selectedPrio));
//
//            if (updatedEval.isPresent()) {
//                tableViewer.refresh();
//            }
//        }
//        catch (DatabaseException e) {
//            LOG.error("Could not retrieve the evaluations from database.", e);
//            showDBConnectionError();
//        }
    }
    
	private void movePriorityUp() {		
//	TODO    Priority selectedPrio = (Priority) tableViewer.getElementAt(tableViewer.getTable()
//     														 .getSelectionIndex());
//		if (selectedPrio == null) {
//		  return;
//		}
//		Priority tempPrio = null;
//
//		if(selectedPrio.getPriority() > 1){	       
//		  for(Priority prio : prioService.findAll()){
//		  	if(prio.getPriority() == selectedPrio.getPriority()-1){
//		  		tempPrio = prio;
//		  		break;
//		  	}        	
//		  }
//		  selectedPrio.setPriority(selectedPrio.getPriority()-1);
//		  tempPrio.setPriority(tempPrio.getPriority() +1);
//		  
//		  prioService.update(selectedPrio);
//		  prioService.update(tempPrio);
//		}
//		refreshTable();
	}
	
	private void movePriorityDown() {

		//JUST TESTING
//		Priority a = (Priority) prios.toArray()[0];
//		Priority b = null;
//		int temp = a.getPriority();
//		for(Priority prio : prios){
//			if(prio.getPriority() == a.getPriority()+1){
//				b = prio;
//			}		
//		}		
//		a.setPriority(a.getPriority()+1);
//		b.setPriority(b.getPriority() -1);
//		
//	
//		tableViewer.refresh();
		
// TODO    Priority selectedPrio = (Priority) tableViewer.getElementAt(tableViewer.getTable()
//                .getSelectionIndex());
//        if (selectedPrio == null) {
//            return;
//        }
//        Priority tempPrio = null;
//        
//        if(selectedPrio.getPriority() < prioService.findAll().size()){	        
//	        for(Priority prio : prioService.findAll()){
//	        	if(prio.getPriority() == selectedPrio.getPriority()+1){
//	        		tempPrio = prio;
//	        		break;
//	        	}        	
//	        }
//	        selectedPrio.setPriority(selectedPrio.getPriority()+1);
//	        tempPrio.setPriority(tempPrio.getPriority() -1);
//	        
//	        prioService.update(selectedPrio);
//	        prioService.update(tempPrio);
//	     }
//        refreshTable();
	}
}
