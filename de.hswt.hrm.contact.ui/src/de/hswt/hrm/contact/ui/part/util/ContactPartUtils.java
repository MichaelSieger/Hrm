package de.hswt.hrm.contact.ui.part.util;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TableColumn;

import de.hswt.hrm.contact.model.Contact;

public final class ContactPartUtils {

    private final static int WIDTH = 120;

    private ContactPartUtils() {

    }

    public static Map<String, String> getDefaultColumnHeaders() {

        // TODO Multilanguage support

        Map<String, String> columnHeaders = new HashMap<String, String>();

        columnHeaders.put("lastName", "Nachname");
        columnHeaders.put("firstName", "Vorname");
        columnHeaders.put("street", "Strasse");
        columnHeaders.put("streetNo", "Hausnummer");
        columnHeaders.put("postCode", "Postleitzahl");
        columnHeaders.put("city", "Stadt");
        columnHeaders.put("shortcut", "Kürzel");
        columnHeaders.put("phone", "Telefonnummer");
        columnHeaders.put("fax", "Fax");
        columnHeaders.put("mobile", "Mobil");
        columnHeaders.put("email", "E-mail");

        return columnHeaders;
    }

    public static void createColumns(Composite parent, TableViewer viewer,
            Map<String, String> columnHeaders) {

        // LastName
        TableViewerColumn col = createTableViewerColumn(columnHeaders.get("lastName"), WIDTH,
                viewer);
        col.setLabelProvider(new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
                Contact c = (Contact) element;
                return c.getLastName();
            }
        });

        // firstName
        col = createTableViewerColumn(columnHeaders.get("firstName"), WIDTH, viewer);
        col.setLabelProvider(new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
                Contact c = (Contact) element;
                return c.getFirstName();
            }
        });

        // street
        col = createTableViewerColumn(columnHeaders.get("street"), WIDTH, viewer);
        col.setLabelProvider(new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
                Contact c = (Contact) element;
                return c.getStreet();
            }
        });

        // streetNo
        col = createTableViewerColumn(columnHeaders.get("streetNo"), WIDTH, viewer);
        col.setLabelProvider(new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
                Contact c = (Contact) element;
                return c.getStreetNo();
            }
        });

        // postCode
        col = createTableViewerColumn(columnHeaders.get("postCode"), WIDTH, viewer);
        col.setLabelProvider(new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
                Contact c = (Contact) element;
                return c.getPostCode();
            }
        });

        // city
        col = createTableViewerColumn(columnHeaders.get("city"), WIDTH, viewer);
        col.setLabelProvider(new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
                Contact c = (Contact) element;
                return c.getCity();
            }
        });

    }

    private static TableViewerColumn createTableViewerColumn(String title, int bound,
            TableViewer viewer) {
        TableViewerColumn viewerColumn = new TableViewerColumn(viewer, SWT.NONE);
        TableColumn column = viewerColumn.getColumn();
        column.setText(title);
        column.setWidth(bound);
        column.setResizable(true);
        column.setMoveable(true);
        return viewerColumn;
    }
}
