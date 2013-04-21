package de.hswt.hrm.contact.ui.part;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Collection;

import javax.annotation.PostConstruct;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.xwt.IConstants;
import org.eclipse.e4.xwt.XWT;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Composite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hswt.hrm.common.Config;
import de.hswt.hrm.common.Hrm;
import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.contact.model.Contact;
import de.hswt.hrm.contact.model.ContactComperator;
import de.hswt.hrm.contact.service.ContactService;
import de.hswt.hrm.contact.ui.filter.ContactFilter;
import de.hswt.hrm.contact.ui.part.util.ContactPartUtils;

public class ContactPart {

    private TableViewer viewer;
    private Collection<Contact> contacts;
    private ContactFilter filter;
    private ContactComperator c;

    private final static Logger LOGGER = LoggerFactory.getLogger(ContactPart.class);

    @PostConstruct
    public void postConstruct(Composite parent) {

        LOGGER.debug("entering method postConstruct");

        /*
         * Getting database connection information via an absolute Path. In addition this method
         * must be placed into the right position when the layout is final. This needs to be
         * improved using eclipse preferences or user.home
         */
        initalizeDbConfig();
        filter = new ContactFilter();
        c = new ContactComperator();
        // URL to the Paths defining XWT file
        URL url = ContactPart.class.getClassLoader().getResource(
                "de/hswt/hrm/contact/ui/xwt/ContactView" + IConstants.XWT_EXTENSION_SUFFIX);

        try {

            // Obtain root element of the XWT file
            final Composite comp = (Composite) XWT.load(parent, url);
            // Obtain TableViwer to fill it with data
            viewer = (TableViewer) XWT.findElementByName(comp, "contactTable");

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        initalizeTable(parent, viewer);

    }

    /*
     * 
     */
    @Focus
    public void onFocus() {

    }

    private void initalizeDbConfig() {
        // Init app
        Hrm.init();
    }

    /**
     * Initializes the TableViewer.\n First it tries to get all Contacts from the Database.
     * Afterwards, the column headers will be created using
     * {@link ContactPartUtils#createColumns(Composite, TableViewer, java.util.Map)}
     * 
     * 
     * @param parent
     * @param viewer
     */
    private void initalizeTable(Composite parent, TableViewer viewer) {

        try {
            contacts = ContactService.findAll();
        }
        catch (DatabaseException e) {
            e.printStackTrace();
        }
        ContactPartUtils.createColumns(parent, viewer, ContactPartUtils.getDefaultColumnHeaders(),
                c);
        viewer.setContentProvider(ArrayContentProvider.getInstance());
        viewer.setInput(contacts);
        viewer.addFilter(filter);
        viewer.setComparator(c);

    }
}