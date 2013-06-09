package de.hswt.hrm.contact.ui.event;

import java.util.Collection;

import javax.inject.Inject;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.xwt.XWT;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;

import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.contact.model.Contact;
import de.hswt.hrm.contact.service.ContactService;
import de.hswt.hrm.contact.ui.filter.ContactFilter;
import de.hswt.hrm.contact.ui.part.ContactPartUtil;

public class ContactEventHandler {
    private final static Logger LOG = LoggerFactory.getLogger(ContactEventHandler.class);
    private static final String DEFAULT_SEARCH_STRING = "Suche";
    private static final String EMPTY = "";

    private final IEclipseContext context;
    private final ContactService contactService;

    @Inject
    public ContactEventHandler(IEclipseContext context, ContactService contactService) {

        if (context == null) {
            LOG.error("EclipseContext was not injected to PlaceEventHandler.");
        }

        if (contactService == null) {
            LOG.error("PlaceService was not injected to PlaceEventHandler.");
        }

        this.context = context;
        this.contactService = contactService;

    }

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

        Button b = (Button) event.widget;
        Optional<Contact> newContact = ContactPartUtil.showWizard(context,
                event.display.getActiveShell(), Optional.<Contact> absent());

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
    public void tableEntrySelected(Event event) {

        TableViewer tv = (TableViewer) XWT.findElementByName(event.widget, "contactTable");

        // obtain the contact in the column where the doubleClick happend
        Contact selectedContact = (Contact) tv.getElementAt(tv.getTable().getSelectionIndex());
        if (selectedContact == null) {
            return;
        }
        try {
            contactService.refresh(selectedContact);
            Optional<Contact> updatedPlace = ContactPartUtil.showWizard(context,
                    event.display.getActiveShell(), Optional.of(selectedContact));

            if (updatedPlace.isPresent()) {
                tv.refresh();
            }
        }
        catch (DatabaseException e) {
            LOG.error("Could not retrieve the place from database.", e);

            // TODO: übersetzen
            MessageDialog.openError(event.display.getActiveShell(), "Connection Error",
                    "Could not update selected contact from database.");
        }
    }
}
