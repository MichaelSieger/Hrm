package de.hswt.hrm.contact.ui.event;

import java.util.Collection;

import org.eclipse.e4.xwt.XWT;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Text;

import com.google.common.base.Optional;

import de.hswt.hrm.contact.model.Contact;
import de.hswt.hrm.contact.ui.filter.ContactFilter;
import de.hswt.hrm.contact.ui.part.util.ContactPartUtils;
import de.hswt.hrm.contact.ui.wizard.ContactWizard;

public class ContactEventHandler {

    private static final String DEFAULT_SEARCH_STRING = "Suche";
    private static final String EMPTY = "";

    private Contact contact;

    public void leaveText(Event event) {

        Text text = (Text) event.widget;
        if (text.getText().isEmpty()) {
            text.setText(DEFAULT_SEARCH_STRING);
        }
        TableViewer tf = (TableViewer) XWT.findElementByName(text, "contactTable");
        tf.refresh();

    }

    // TODO check for better solution
    public void buttonSelected(Event event) {
        Button b = (Button) event.widget;
        Optional<Contact> newContact = ContactPartUtils.showWizard(event.display.getActiveShell(),
                Optional.fromNullable(contact));

        TableViewer tf = (TableViewer) XWT.findElementByName(b, "contactTable");
        @SuppressWarnings("unchecked")
        Collection<Contact> c = (Collection<Contact>) tf.getInput();
        if (newContact.isPresent()) {
            System.out.println("is present");
            c.add(newContact.get());
            tf.refresh();
        }
        else {
            System.out.println("is not present");
        }
    }

    public void onKeyUp(Event event) {

        Text searchText = (Text) event.widget;
        TableViewer tv = (TableViewer) XWT.findElementByName(searchText, "contactTable");
        ContactFilter filter = (ContactFilter) tv.getFilters()[0];
        filter.setSearchString(searchText.getText());
        tv.refresh();

    }

    public void enterText(Event event) {
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
    public void tableEntrySelected(Event event) {
        TableViewer tv = (TableViewer) XWT.findElementByName(event.widget, "contactTable");
        // obtain the contact in the column where the doubleClick happend
        Contact c = (Contact) tv.getElementAt(tv.getTable().getSelectionIndex());
        WizardDialog wd = new WizardDialog(tv.getTable().getShell(), new ContactWizard(
                Optional.fromNullable(c)));
        wd.open();

    }
}
