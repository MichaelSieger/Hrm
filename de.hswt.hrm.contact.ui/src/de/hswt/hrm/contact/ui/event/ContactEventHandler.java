package de.hswt.hrm.contact.ui.event;

import java.util.Collection;

import org.eclipse.e4.xwt.XWT;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Text;

import de.hswt.hrm.contact.model.Contact;
import de.hswt.hrm.contact.ui.filter.ContactFilter;
import de.hswt.hrm.contact.ui.wizard.ContactWizard;

public class ContactEventHandler {

    private static final String DEFAULT_SEARCH_STRING = "Suche";
    private static final String EMPTY = "";

    public void onFocusOut(Event event) {

        Text text = (Text) event.widget;
        if (text.getText().isEmpty()) {
            text.setText(DEFAULT_SEARCH_STRING);
        }
        TableViewer tf = (TableViewer) XWT.findElementByName(text, "contactTable");
        tf.refresh();

    }

    // TODO check for better solution
    public void onSelection(Event event) {
        Button b = (Button) event.widget;
        ContactWizard cw = new ContactWizard(null);
        WizardDialog dialog = new WizardDialog(b.getShell(), cw);
        dialog.open();
        if (cw.getContact() != null) {
            TableViewer tf = (TableViewer) XWT.findElementByName(b, "contactTable");
            @SuppressWarnings("unchecked")
            Collection<Contact> c = (Collection<Contact>) tf.getInput();
            c.add(cw.getContact());
            tf.refresh();
        }
    }

    public void onKeyUp(Event event) {

        Text searchText = (Text) event.widget;
        TableViewer tv = (TableViewer) XWT.findElementByName(searchText, "contactTable");
        ContactFilter filter = (ContactFilter) tv.getFilters()[0];
        filter.setSearchString(searchText.getText());
        tv.refresh();

    }

    public void onFocusIn(Event event) {
        Text text = (Text) event.widget;
        if (text.getText().equals(DEFAULT_SEARCH_STRING)) {
            text.setText(EMPTY);
        }

    }

    /**
     * This method is called whenever a doubleClick onto the Tableviewer occurs. It obtains the
     * contact from the selected column of the TableViewer. The Contact is passed to the
     * ContactWizard. When the Wizard has finished, the contact will be updated in the Database
     * 
     * @param event
     *            Event which occured within SWT
     */
    public void onMouseDoubleClick(Event event) {
        TableViewer tv = (TableViewer) XWT.findElementByName(event.widget, "contactTable");
        // obtain the contact in the column where the doubleClick happend
        Contact c = (Contact) tv.getElementAt(tv.getTable().getSelectionIndex());
        WizardDialog wd = new WizardDialog(tv.getTable().getShell(), new ContactWizard(c));
        wd.open();

    }
}
