package de.hswt.hrm.contact.ui.part;

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
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.ui.swt.table.ColumnComparator;
import de.hswt.hrm.common.ui.swt.table.ColumnDescription;
import de.hswt.hrm.common.ui.swt.table.TableViewerController;
import de.hswt.hrm.common.ui.xwt.XwtHelper;
import de.hswt.hrm.contact.model.Contact;
import de.hswt.hrm.contact.service.ContactService;
import de.hswt.hrm.contact.ui.event.ContactEventHandler;
import de.hswt.hrm.contact.ui.filter.ContactFilter;

public class ContactPart {
    private final static Logger LOG = LoggerFactory.getLogger(ContactPart.class);

    @Inject
    private ContactService contactService;
    
	@Inject
	EPartService service;

    private TableViewer viewer;
    private Collection<Contact> contacts;

    @PostConstruct
    public void postConstruct(Composite parent, IEclipseContext context) {

        URL url = ContactPart.class.getClassLoader().getResource(
                "de/hswt/hrm/contact/ui/xwt/ContactView" + IConstants.XWT_EXTENSION_SUFFIX);

        try {

            ContactEventHandler eventHandler = ContextInjectionFactory.make(
                    ContactEventHandler.class, context);
            // Obtain root element of the XWT file
            final Composite comp = XwtHelper.loadWithEventHandler(parent, url, eventHandler);
            LOG.debug("XWT load successfully");

            // Obtain TableViwer to fill it with data
            viewer = (TableViewer) XWT.findElementByName(comp, "contactTable");
            initializeTable(parent, viewer);
            refreshTable(parent);   

        }
        catch (Exception e) {
            LOG.error("Could not load XWT file from resource", e);
        }

        if (contactService == null) {
            LOG.error("ContactService not injected to ContactPart");
        }

    }

    private void refreshTable(Composite parent) {
        try {
            contacts = contactService.findAll();
            viewer.setInput(contacts);
        }
        catch (DatabaseException e) {
            LOG.error("Unable to retrieve list of contacts.", e);

            MessageDialog.openError(parent.getShell(), "Connection Error",
                    "Could not load contacts from Database.");
        }
    }

    private void initializeTable(Composite parent, TableViewer viewer) {
        List<ColumnDescription<Contact>> columns = ContactPartUtil.getColumns();

        // Create columns in tableviewer
        TableViewerController<Contact> filler = new TableViewerController<>(viewer);
        filler.createColumns(columns);

        // Enable column selection
        filler.createColumnSelectionMenu();

        // Enable sorting
        ColumnComparator<Contact> comperator = new ColumnComparator<>(columns);
        filler.enableSorting(comperator);

        // Add dataprovider that handles our collection
        viewer.setContentProvider(ArrayContentProvider.getInstance());

        // Enable filtering
        viewer.addFilter(new ContactFilter());
    }

}