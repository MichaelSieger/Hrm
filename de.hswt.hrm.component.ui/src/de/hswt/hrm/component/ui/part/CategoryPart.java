package de.hswt.hrm.component.ui.part;

import java.net.URL;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.EPartService.PartState;
import org.eclipse.e4.xwt.IConstants;
import org.eclipse.e4.xwt.XWT;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.ui.swt.table.ColumnComparator;
import de.hswt.hrm.common.ui.swt.table.ColumnDescription;
import de.hswt.hrm.common.ui.swt.table.TableViewerController;
import de.hswt.hrm.common.ui.xwt.XwtHelper;
import de.hswt.hrm.component.model.Category;
import de.hswt.hrm.component.ui.event.CategoryEventHandler;
import de.hswt.hrm.component.ui.filter.CategoryFilter;
import de.hswt.hrm.component.service.CategoryService;

public class CategoryPart {
    
    private final static Logger LOG = LoggerFactory.getLogger(CategoryPart.class);
    
    @Inject
    private CategoryService categoryService;
    
    @Inject
    EPartService service;
    
    private TableViewer viewer;
    private Collection<Category> categories;

	@PostConstruct
	public void postConstruct(Composite parent, IEclipseContext context) {
		URL url = CategoryPart.class.getClassLoader().getResource(
		        "de/hswt/hrm/component/ui/xwt/CategoryView"+IConstants.XWT_EXTENSION_SUFFIX);
		try {
		    
		    CategoryEventHandler eventHandler = ContextInjectionFactory.make(
		            CategoryEventHandler.class, context);
		    
		    //Obtain root element of the XWT file
		    final Composite comp = XwtHelper.loadWithEventHandler(parent, url, eventHandler);
		    LOG.debug("XWT loaded successfully");
		    
		    //Obtain TableViewer to fill it with data
		    viewer = (TableViewer) XWT.findElementByName(comp, "categoryTable");
		    initializeTable(parent, viewer);
		    refreshTable(parent);
		    
            ((Button) XWT.findElementByName(comp, "back2Main")).addListener(SWT.Selection, new Listener() {
 				@Override
 				public void handleEvent(Event event) {
 					service.findPart("Clients").setVisible(false);
 					service.findPart("Places").setVisible(false);
 					service.findPart("Plants").setVisible(false);
 					service.findPart("Scheme").setVisible(false);
 					service.findPart("Catalog").setVisible(false);
 					service.findPart("Category").setVisible(false);
 					service.findPart("Main").setVisible(true);
 					service.showPart("Main", PartState.VISIBLE);
 				}
 			});
		} catch (Exception e) {
		    LOG.error("Could not load XWT file from resource",e);
		}
		if (categoryService == null) {
		    LOG.error("CategoryService not injected into CategoryPart");
		}
	}
	
	private void initializeTable(Composite parent, TableViewer viewer) {
	    List<ColumnDescription<Category>> columns = CategoryPartUtil.getColumns();
	    // Create columns in TableViewer
	    TableViewerController<Category> filler = new TableViewerController<>(viewer);
	    filler.createColumns(columns);
	    
	    // Enable columns selection
	    filler.createColumnSelectionMenu();
	    
	    // Enable sorting
	    ColumnComparator<Category> comparator = new ColumnComparator<>(columns);
	    filler.enableSorting(comparator);
	    
	    // Add data provider which handles our collection
	    viewer.setContentProvider(ArrayContentProvider.getInstance());
	    
	    // Enable filtering
	    viewer.addFilter(new CategoryFilter());
	}
	
	private void refreshTable(Composite parent) {
	    try {
	        categories = categoryService.findAll();
	        viewer.setInput(categories);
	    } catch (DatabaseException e) {
	        LOG.error("Unable to retrieve list of categories.", e);
	        MessageDialog.openError(parent.getShell(), "Connection Error",
	                "Could not load categories from Database.");
	    }
	}

}