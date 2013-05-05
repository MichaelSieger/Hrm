package de.hswt.hrm.contact.ui.wizard;

import java.util.HashMap;

import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;

import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.database.exception.ElementNotFoundException;
import de.hswt.hrm.common.database.exception.SaveException;
import de.hswt.hrm.contact.model.Contact;
import de.hswt.hrm.contact.service.ContactService;

public class ContactWizard extends Wizard {

    private static final Logger LOG = LoggerFactory.getLogger(ContactWizard.class);
    private ContactWizardPageOne first;
    private Optional<Contact> contact;

    public ContactWizard(Optional<Contact> contact) {

        this.contact = contact;
        first = new ContactWizardPageOne("First Page", contact);
        setWindowTitle("Neuen Kontakt hinzufügen");

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

        if (!contact.isPresent()) {
            return insertNewContact();
        }

        return editExistingCustomer();

    }

    private boolean editExistingCustomer() {

        Contact c = this.contact.get();
        try {

            c = ContactService.findById(c.getId());

        }
        catch (DatabaseException e1) {

            LOG.error("Could not find Contact with ID: " + c.getId(), e1);
        }

        HashMap<String, Text> mandatoryWidgets = first.getMandatoryWidgets();
        String firstName = mandatoryWidgets.get("firstName").getText();
        String lastName = mandatoryWidgets.get("lastName").getText();
        String street = mandatoryWidgets.get("street").getText();
        String streetNumber = mandatoryWidgets.get("streetNumber").getText();
        String city = mandatoryWidgets.get("city").getText();
        String zipCode = mandatoryWidgets.get("zipCode").getText();

        c.setFirstName(firstName);
        c.setLastName(lastName);
        c.setStreet(street);
        c.setStreetNo(streetNumber);
        c.setCity(city);
        c.setPostCode(zipCode);

        HashMap<String, Text> optionalWidgets = first.getOptionalWidgets();
        String shortcut = optionalWidgets.get("shortcut").getText();
        String phone = optionalWidgets.get("phone").getText();
        String fax = optionalWidgets.get("fax").getText();
        String mobilePhone = optionalWidgets.get("mobilePhone").getText();
        String email = optionalWidgets.get("email").getText();

        c.setShortcut(shortcut);
        c.setPhone(phone);
        c.setMobile(mobilePhone);
        c.setFax(fax);
        c.setEmail(email);

        try {
            ContactService.update(c);
        }
        catch (ElementNotFoundException e) {
            LOG.error("Element " + contact + " not found in Database", e);
        }
        catch (SaveException e) {
            LOG.error("Could not save Element: " + contact + "into Database", e);
        }

        return true;

    }

    private boolean insertNewContact() {
        HashMap<String, Text> mandatoryWidgets = first.getMandatoryWidgets();
        String firstName = mandatoryWidgets.get("firstName").getText();
        String lastName = mandatoryWidgets.get("lastName").getText();
        String street = mandatoryWidgets.get("street").getText();
        String streetNumber = mandatoryWidgets.get("streetNumber").getText();
        String city = mandatoryWidgets.get("city").getText();
        String zipCode = mandatoryWidgets.get("zipCode").getText();

        Contact c = new Contact(lastName, firstName, street, streetNumber, zipCode, city);

        HashMap<String, Text> optionalWidgets = first.getOptionalWidgets();
        String shortcut = optionalWidgets.get("shortcut").getText();
        String phone = optionalWidgets.get("phone").getText();
        String fax = optionalWidgets.get("fax").getText();
        String mobilePhone = optionalWidgets.get("mobilePhone").getText();
        String email = optionalWidgets.get("email").getText();

        c.setShortcut(shortcut);
        c.setPhone(phone);
        c.setMobile(mobilePhone);
        c.setFax(fax);
        c.setEmail(email);

        try {
            ContactService.insert(c);
            contact = Optional.fromNullable(c);
        }
        catch (SaveException e) {
            LOG.error("Could not save Element: " + contact + "into Database", e);
        }

        return true;
    }

    public Optional<Contact> getContact() {
        return contact;
    }

}
