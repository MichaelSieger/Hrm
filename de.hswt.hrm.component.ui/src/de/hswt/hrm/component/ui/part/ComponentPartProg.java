package de.hswt.hrm.component.ui.part;

import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.xwt.IConstants;
import org.eclipse.e4.xwt.XWT;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Image;
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
import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPage;

import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.ui.swt.constants.SearchFieldConstants;
import de.hswt.hrm.common.ui.swt.forms.FormUtil;
import de.hswt.hrm.common.ui.swt.layouts.LayoutUtil;
import de.hswt.hrm.common.ui.swt.table.ColumnComparator;
import de.hswt.hrm.common.ui.swt.table.ColumnDescription;
import de.hswt.hrm.common.ui.swt.table.TableViewerController;
import de.hswt.hrm.common.ui.xwt.XwtHelper;
import de.hswt.hrm.component.model.Category;
import de.hswt.hrm.component.model.Component;
import de.hswt.hrm.component.service.CategoryService;
import de.hswt.hrm.component.service.ComponentService;
import de.hswt.hrm.component.ui.event.ComponentEventHandler;
import de.hswt.hrm.component.ui.filter.CategoryFilter;
import de.hswt.hrm.component.ui.filter.ComponentFilter;
import de.hswt.hrm.scheme.service.ComponentConverter;

import org.eclipse.swt.widgets.Label;

public class ComponentPartProg {
    private final static Logger LOG = LoggerFactory.getLogger(ComponentPartProg.class);

    private FormToolkit toolkit = new FormToolkit(Display.getDefault());
    
    @Inject
    private ComponentService service;
    
    @Inject 
    private CategoryService catService;
    
    @Inject 
    IEclipseContext context;
    
    @Inject
    private IShellProvider shellProvider;

    private TableViewer viewer;
    private Collection<Component> components;
    
    private Table table;
    private Text searchText;
    private TableViewer tableViewer;

    private Action editAction;
    private Action addAction;
    private Section headerSection;
    private Composite composite;
    private Label previewImage;
    
    public ComponentPartProg() {
        // toolkit can be created in PostConstruct, but then then
        // WindowBuilder is unable to parse the code
        toolkit.dispose();
        toolkit = FormUtil.createToolkit();
    }
    
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
        form.setText("Components");
        toolkit.decorateFormHeading(form);
        form.getToolBarManager().add(editAction);

        form.getToolBarManager().add(addAction);
        FillLayout fillLayout = new FillLayout(SWT.HORIZONTAL);
        fillLayout.marginHeight = 1;
        form.getBody().setLayout(fillLayout);

        headerSection = toolkit.createSection(form.getBody(), Section.TITLE_BAR);
        toolkit.paintBordersFor(headerSection);
        headerSection.setExpanded(true);
        FormUtil.initSectionColors(headerSection);

        composite = toolkit.createComposite(headerSection, SWT.NONE);
        toolkit.paintBordersFor(composite);
        headerSection.setClient(composite);
        composite.setLayout(new GridLayout(2, false));

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
        new Label(composite, SWT.NONE);

        tableViewer = new TableViewer(composite, SWT.BORDER | SWT.FULL_SELECTION);
        tableViewer.addDoubleClickListener(new IDoubleClickListener() {
            public void doubleClick(DoubleClickEvent event) {
                editComponent();
            }
        });
        tableViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				setPreview();
				
			}
		});
        table = tableViewer.getTable();
        table.setLinesVisible(true);
        table.setHeaderVisible(true);
        table.setLayoutData(LayoutUtil.createFillData());
        toolkit.paintBordersFor(table);
        form.getToolBarManager().update(true);

        initializeTable(composite, tableViewer);
        refreshTable(composite);
        
        previewImage = new Label(composite, SWT.NONE);
        toolkit.adapt(previewImage, true, true);

        if (service == null) {
            LOG.error("ComponentService not injected to ComponentPart.");
        }
    }
    
    private void createActions() {
        {
            editAction = new Action("Edit") {
                @Override
                public void run() {
                    super.run();
                    editComponent();
                }
            };
            editAction.setDescription("Edit an exisitng component.");
        }
        {
            addAction = new Action("Add") {
                @Override
                public void run() {
                    super.run();
                    addComponent();
                }
            };
            addAction.setDescription("Add's a new component.");
        }
    }

    private void refreshTable(Composite parent) {
        try {
            components = service.findAll();
            tableViewer.setInput(components);
        }
        catch (DatabaseException e) {
            LOG.error("Unable to retrieve list of components.", e);

            MessageDialog.openError(parent.getShell(), "Connection Error",
                    "Could not load components from Database.");
        }
    }

    private void initializeTable(Composite parent, TableViewer viewer) {
        List<ColumnDescription<Component>> columns = ComponentPartUtil.getColumns();

        // Create columns in tableviewer
        TableViewerController<Component> filler = new TableViewerController<>(viewer);
        filler.createColumns(columns);

        // Enable column selection
        filler.createColumnSelectionMenu();

        // Enable sorting
        ColumnComparator<Component> comperator = new ColumnComparator<>(columns);
        filler.enableSorting(comperator);

        // Add dataprovider that handles our collection
        viewer.setContentProvider(ArrayContentProvider.getInstance());

        // Enable filtering
        viewer.addFilter(new ComponentFilter());
    }
    
    public void addComponent() {
        
        Optional<Component> newComponent = ComponentPartUtil.showWizard(service,catService, context,
        		shellProvider.getShell(), Optional.<Component> absent());


        Collection<Component> components = (Collection<Component>) tableViewer.getInput();
        if (newComponent.isPresent()) {
            components.add(newComponent.get());
            tableViewer.refresh();
        }
    }
    
    public void editComponent() {

        Component selectedComp = (Component) tableViewer.getElementAt(tableViewer.getTable()
                .getSelectionIndex());
        if (selectedComp == null) {
            return;
        }
//        try {
//            service.refresh(selectedComp);
            Optional<Component> updatedComp = ComponentPartUtil.showWizard(service,catService, context,
                    shellProvider.getShell(), Optional.of(selectedComp));
//            if (updatedComp.isPresent()) {
//                tableViewer.refresh();
//            }
//        }
//        catch (DatabaseException e) {
//            LOG.error("Could not retrieve the component from database.", e);
//        }
    }
    
    private void updateTableFilter(String filterString) {
        ComponentFilter filter = (ComponentFilter) tableViewer.getFilters()[0];
        filter.setSearchString(filterString);
        tableViewer.refresh();
    }
    
    private void setPreview(){        
    	byte[] bytes;    	
        IStructuredSelection sel =  (IStructuredSelection) tableViewer.getSelection();
        Component selectedComponent = (Component) sel.getFirstElement();
        if(selectedComponent != null){
	        bytes = selectedComponent.getRightLeftImage();
	        if(bytes == null){
	        	bytes = selectedComponent.getDownUpImage();
	        }
	         if(bytes == null){
	        	bytes = selectedComponent.getUpDownImage();
	         }
	         if(bytes == null){
	        	bytes = selectedComponent.getLeftRightImage();
	         }
	        
	
			
			ByteBuffer buf = ByteBuffer.wrap(bytes);
			PDFFile pdffile;
			try {
				pdffile = new PDFFile(buf);
				PDFPage page = pdffile.getPage(0);
				
				Image imge = ComponentConverter.getSWTImage(previewImage.getDisplay(),ComponentConverter.renderImage(page, 100, 100));
		        previewImage.setImage(imge);
		    	previewImage.setSize( previewImage.computeSize( SWT.DEFAULT, SWT.DEFAULT ));
				
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	   	
        }
    }

}