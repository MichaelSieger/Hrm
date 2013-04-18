package de.hswt.hrm.contact.ui.view;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Collection;
import javax.annotation.PostConstruct;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.xwt.IConstants;
import org.eclipse.e4.xwt.XWT;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TableColumn;

import de.hswt.hrm.common.Config;
import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.contact.model.Contact;
import de.hswt.hrm.contact.service.ContactService;

public class ContactView {

    private TableViewer viewer;
    private Collection<Contact> contacts;

    @PostConstruct
    public void postConstruct(Composite parent) {
        /*
         * This needs to be improved
         */
        initalize();

        URL url = ContactView.class.getClassLoader().getResource(
                "de/hswt/hrm/contact/ui/xwt/ContactView" + IConstants.XWT_EXTENSION_SUFFIX);
        try {
            Composite comp = (Composite) XWT.load(parent, url);
            viewer = (TableViewer) XWT.findElementByName(comp, "contactTable");

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        initalizeTable(parent, viewer);

    }

    private void initalize() {

        try {
            Config config = Config.getInstance();
            config.load(Paths.get("/home/knacht/git/hrm/resources/hrm.properties"));
        }
        catch (IOException e) {

            e.printStackTrace();
        }
    }

    private void initalizeTable(Composite parent, TableViewer viewer) {

        try {
            contacts = ContactService.findAll();
        }
        catch (DatabaseException e) {
            e.printStackTrace();
        }
        createColumns(parent, viewer);
        viewer.setContentProvider(ArrayContentProvider.getInstance());
        viewer.setInput(contacts);

    }

    private void createColumns(Composite parent, TableViewer viewer2) {

        String[] titles = { "Last Name", "First Name", "Street", "Street Number", "Post Code",
                "City", "Short Cut", "Phone", "fax", "mobile", "Email" };
        int[] bounds = { 100, 100, 100, 100 };

        TableViewerColumn col = createTableViewerColumn(titles[0], 100, 0);
        col.setLabelProvider(new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
                Contact c = (Contact) element;
                return c.getLastName();
            }
        });

    }

    @Focus
    public void onFocus() {

    }

    private TableViewerColumn createTableViewerColumn(String title, int bound, final int colNumber) {
        final TableViewerColumn viewerColumn = new TableViewerColumn(viewer, SWT.NONE);
        final TableColumn column = viewerColumn.getColumn();
        column.setText(title);
        column.setWidth(bound);
        column.setResizable(true);
        column.setMoveable(true);
        return viewerColumn;
    }

}