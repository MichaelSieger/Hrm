package de.hswt.hrm.contact.ui.view;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Map;

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

    private final static int WIDTH = 100;

    private TableViewer viewer;
    private Collection<Contact> contacts;
    private Map<String, String> columnHeader;

    @PostConstruct
    public void postConstruct(Composite parent) {
        /*
         * This needs to be improved using eclipse preferences or user.home
         */
        initalizeDbConfig();
        /*
         * This is only a temporary Solution
         */
        initalizeMap();

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

        TableColumn[] t = viewer.getTable().getColumns();
        for (TableColumn c : t) {
            System.out.println(c);
        }

    }

    private void initalizeMap() {

        columnHeader.put("lastName", "Nachname");
        columnHeader.put("firstName", "Vorname");
        columnHeader.put("street", "Strasse");
        columnHeader.put("streetNr", "Hausnummer");
        columnHeader.put("postCode", "Postleitzahl");
        columnHeader.put("city", "Stadt");
        columnHeader.put("shortcut", "Kürzel");
        columnHeader.put("phone", "Telefonnummer");
        columnHeader.put("fax", "Fax");
        columnHeader.put("mobile", "Mobil");
        columnHeader.put("email", "E-mail");

    }

    private void initalizeDbConfig() {

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

        TableViewerColumn col = createTableViewerColumn(titles[0], WIDTH, 0);
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

    private TableViewerColumn createTableViewerColumn(String title, int bound, int colNumber) {
        TableViewerColumn viewerColumn = new TableViewerColumn(viewer, SWT.NONE);
        TableColumn column = viewerColumn.getColumn();
        column.setText(title);
        column.setWidth(bound);
        column.setResizable(true);
        column.setMoveable(true);
        return viewerColumn;
    }

}