package de.hswt.hrm.contact.ui.event;

import java.util.Collection;

import org.eclipse.e4.xwt.XWT;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Text;

import com.google.common.base.Optional;

import de.hswt.hrm.contact.model.Contact;
import de.hswt.hrm.contact.ui.filter.ContactFilter;
import de.hswt.hrm.contact.ui.part.util.ContactPartUtils;

public class ContactEventHandler {

    private static final String DEFAULT_SEARCH_STRING = "Suche";
    private static final String EMPTY = "";
    private Contact contact;

    /**
     * This event is called whenever the Search Text Field is leaved. If the the field is blank, the
     * value of the Field {@link #DEFAULT_SEARCH_STRING} is inserted.
     * 
     * @param event
     *            Event which occured in SWT
     */
    public void leaveText(Event event) {

        Text text = (Text) event.widget;
        if (text.getText().isEmpty()) {
            text.setText(DEFAULT_SEARCH_STRING);
        }
        TableViewer tf = (TableViewer) XWT.findElementByName(text, "contactTable");
        tf.refresh();

    }

    /**
     * This Event is called whenever the add buttion is pressed.
     * 
     * @param event
     */
    @SuppressWarnings("unchecked")
    public void buttonSelected(Event event) {
        contact = null;
        Button b = (Button) event.widget;
        Optional<Contact> newContact = ContactPartUtils.showWizard(event.display.getActiveShell(),
                Optional.fromNullable(contact));

        TableViewer tv = (TableViewer) XWT.findElementByName(b, "contactTable");

        Collection<Contact> contacs = (Collection<Contact>) tv.getInput();
        if (newContact.isPresent()) {
            contacs.add(newContact.get());
            tv.refresh();
        }
    }

    /**
     * This event is called whenever a Text is entered into the Search textField
     * 
     * @param event
     */
    public void onKeyUp(Event event) {

        Text searchText = (Text) event.widget;
        TableViewer tv = (TableViewer) XWT.findElementByName(searchText, "contactTable");
        ContactFilter filter = (ContactFilter) tv.getFilters()[0];
        filter.setSearchString(searchText.getText());
        tv.refresh();

    }

    /**
     * This event is called whenever the Search text field is entered
     * 
     * @param event
     */
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
    @SuppressWarnings("unchecked")
    public void tableEntrySelected(Event event) {

        TableViewer tv = (TableViewer) XWT.findElementByName(event.widget, "contactTable");

        // obtain the contact in the column where the doubleClick happend
        Contact selectedContact = (Contact) tv.getElementAt(tv.getTable().getSelectionIndex());

        Optional<Contact> updateContact = ContactPartUtils.showWizard(
                event.display.getActiveShell(), Optional.fromNullable(selectedContact));

//        Collection<Contact> contacs = (Collection<Contact>) tv.getInput();

        if (updateContact.isPresent()) {
            // Maybe it is better to programm against List<E> Interface and use get insted of remove
            // and add
//            contacs.remove(selectedContact);
//            contacs.add(updateContact.get());
            tv.refresh();

        }
    }
}
