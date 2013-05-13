package de.hswt.hrm.contact.ui.wizard;

import java.util.HashMap;

import javax.inject.Inject;

import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;

import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.database.exception.SaveException;
import de.hswt.hrm.contact.model.Contact;
import de.hswt.hrm.contact.service.ContactService;

public class ContactWizard extends Wizard {

    private static final Logger LOG = LoggerFactory.getLogger(ContactWizard.class);
    private ContactWizardPageOne first;
    private Optional<Contact> contact;
    
    @Inject
    private ContactService contactService;

    public ContactWizard(Optional<Contact> contact) {

        this.contact = contact;
        first = new ContactWizardPageOne("First Page", contact);

        if (contact.isPresent()) {
            setWindowTitle("Kontakt bearbeiten");
        }
        else {
            setWindowTitle("Neuen Kontakt anlegen");
        }
    }

    @Override
    public void addPages() {
        addPage(first);
    }

    @Override
    public boolean canFinish() {
        return first.isPageComplete();
    }

    @Override
    public boolean performFinish() {
        if (contact.isPresent()) {
            return editExistingCustomer();
        }
        else {
            return insertNewContact();
        }

    }

    private boolean editExistingCustomer() {
         // At this point we already know that the contact exists
         Contact c = this.contact.get();
         try {
         // Update contact from the Database
         c = contactService.findById(c.getId());
         // set the values to the fields from the WizardPage
         c = setValues(contact);
         // Update contact in the Database
         contactService.update(c);
         contact = Optional.of(c);
        
         }
         catch (DatabaseException e1) {
         LOG.error("An error occured", e1);
         }

        return true;

    }

    private boolean insertNewContact() {
        Contact c = setValues(Optional.<Contact> absent());

         try {
         contact = Optional.of(contactService.insert(c));
         }
         catch (SaveException e) {
         LOG.error("Could not save Element: " + contact + "into Database", e);
         }

        return true;
    }

    private Contact setValues(Optional<Contact> c) {

        HashMap<String, Text> mandatoryWidgets = first.getMandatoryWidgets();
        String firstName = mandatoryWidgets.get("firstName").getText();
        String lastName = mandatoryWidgets.get("lastName").getText();
        String street = mandatoryWidgets.get("street").getText();
        String streetNumber = mandatoryWidgets.get("streetNumber").getText();
        String city = mandatoryWidgets.get("city").getText();
        String zipCode = mandatoryWidgets.get("zipCode").getText();

        HashMap<String, Text> optionalWidgets = first.getOptionalWidgets();
        String shortcut = optionalWidgets.get("shortcut").getText();
        String phone = optionalWidgets.get("phone").getText();
        String fax = optionalWidgets.get("fax").getText();
        String mobilePhone = optionalWidgets.get("mobilePhone").getText();
        String email = optionalWidgets.get("email").getText();

        Contact contact;
        if (c.isPresent()) {

            contact = c.get();

            contact.setFirstName(firstName);
            contact.setLastName(lastName);
            contact.setStreet(street);
            contact.setStreetNo(streetNumber);
            contact.setCity(city);
            contact.setPostCode(zipCode);
        }

        else {
            contact = new Contact(lastName, firstName, street, streetNumber, zipCode, city);
        }

        contact.setShortcut(shortcut);
        contact.setPhone(phone);
        contact.setMobile(mobilePhone);
        contact.setFax(fax);
        contact.setEmail(email);

        return contact;

    }

    public Optional<Contact> getContact() {
        return contact;
    }

}
