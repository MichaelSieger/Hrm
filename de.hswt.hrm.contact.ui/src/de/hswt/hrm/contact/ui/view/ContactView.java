package de.hswt.hrm.contact.ui.view;

import java.net.URL;

import javax.annotation.PostConstruct;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.xwt.IConstants;
import org.eclipse.e4.xwt.XWT;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Composite;

public class ContactView {

    private TableViewer table;

    @PostConstruct
    public void postConstruct(Composite parent) {

        URL url = ContactView.class.getClassLoader().getResource(
                "de/hswt/hrm/contact/ui/xwt/ContactView" + IConstants.XWT_EXTENSION_SUFFIX);
        try {
            Composite comp = (Composite) XWT.load(parent, url);
            table = (TableViewer) XWT.findElementByName(comp, "contactTable");
            System.out.println(table.toString());
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        initalizeTable(parent, table);

    }

    private void initalizeTable(Composite parent, TableViewer table) {

    }

    @Focus
    public void onFocus() {

    }

}