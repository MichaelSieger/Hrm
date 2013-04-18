package de.hswt.hrm.contact.ui.view;

import java.net.URL;
import java.util.Collection;

import javax.annotation.PostConstruct;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.xwt.IConstants;
import org.eclipse.e4.xwt.XWT;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Composite;

import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.contact.model.Contact;
import de.hswt.hrm.contact.service.ContactService;

public class ContactView {

    private TableViewer viewer;

    @PostConstruct
    public void postConstruct(Composite parent) {

        URL url = ContactView.class.getClassLoader().getResource(
                "de/hswt/hrm/contact/ui/xwt/ContactView" + IConstants.XWT_EXTENSION_SUFFIX);
        try {
            Composite comp = (Composite) XWT.load(parent, url);
            viewer = (TableViewer) XWT.findElementByName(comp, "contactTable");
            System.out.println(viewer.toString());
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        initalizeTable(parent, viewer);

    }

    private void initalizeTable(Composite parent, TableViewer viewer) {

        try {
            Collection<Contact> contacts = ContactService.findAll();
            viewer.setContentProvider(ArrayContentProvider.getInstance());
            viewer.setInput(contacts);
        }
        catch (DatabaseException e) {
            e.printStackTrace();
        }

    }

    @Focus
    public void onFocus() {

    }

}