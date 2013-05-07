package de.hswt.hrm.contact.ui.part;

import java.net.URL;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;

import org.eclipse.e4.xwt.IConstants;
import org.eclipse.e4.xwt.XWT;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Composite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.ui.swt.table.ColumnComparator;
import de.hswt.hrm.common.ui.swt.table.ColumnDescription;
import de.hswt.hrm.common.ui.swt.table.TableViewerController;
import de.hswt.hrm.contact.model.Contact;
import de.hswt.hrm.contact.service.ContactService;
import de.hswt.hrm.contact.ui.filter.ContactFilter;

public class ContactPart {

    private TableViewer viewer;
    private Collection<Contact> contacts;

    private final static Logger LOG = LoggerFactory.getLogger(ContactPart.class);

    @PostConstruct
    public void postConstruct(Composite parent) {

        URL url = ContactPart.class.getClassLoader().getResource(
                "de/hswt/hrm/contact/ui/xwt/ContactView" + IConstants.XWT_EXTENSION_SUFFIX);

        try {

            // Obtain root element of the XWT file
            final Composite comp = (Composite) XWT.load(parent, url);
            // Obtain TableViwer to fill it with data
            viewer = (TableViewer) XWT.findElementByName(comp, "contactTable");
            initializeTable(parent, viewer);
            refreshTable(parent);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void refreshTable(Composite parent) {
        try {
            contacts = ContactService.findAll();
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