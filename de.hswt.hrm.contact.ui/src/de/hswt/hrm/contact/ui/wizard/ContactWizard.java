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
import de.hswt.hrm.contact.ui.part.ContactPartUtil;
import de.hswt.hrm.i18n.I18n;
import de.hswt.hrm.i18n.I18nFactory;

public class ContactWizard extends Wizard {
    private static final Logger LOG = LoggerFactory.getLogger(ContactWizard.class);
    private static final I18n I18N = I18nFactory.getI18n(ContactPartUtil.class);
    private ContactWizardPageOne first;
    private ContactWizardPageTwo second;
    private Optional<Contact> contact;

    @Inject
    private ContactService contactService;

    public ContactWizard(Optional<Contact> contact) {
        this.contact = contact;
        first = new ContactWizardPageOne("First Page", contact);
        second = new ContactWizardPageTwo("Second Page", contact);

        if (contact.isPresent()) {
            setWindowTitle(I18N.tr("Edit Contact"));
        }
        else {
            setWindowTitle(I18N.tr("Add Contact"));
        }
    }

    @Override
    public void addPages() {
        addPage(first);
        addPage(second);
    }

    @Override
    public boolean canFinish() {
        return first.isPageComplete() && second.isPageComplete();
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
        String name = mandatoryWidgets.get("name").getText();
        String street = mandatoryWidgets.get("street").getText();
        String streetNumber = mandatoryWidgets.get("streetNumber").getText();
        String city = mandatoryWidgets.get("city").getText();
        String zipCode = mandatoryWidgets.get("zipCode").getText();

        HashMap<String, Text> optionalWidgets = second.getOptionalWidgets();
        String shortcut = optionalWidgets.get("shortcut").getText();
        String phone = optionalWidgets.get("phone").getText();
        String fax = optionalWidgets.get("fax").getText();
        String mobilePhone = optionalWidgets.get("mobilePhone").getText();
        String email = optionalWidgets.get("email").getText();

        Contact contact;
        if (c.isPresent()) {

            contact = c.get();

            contact.setName(name);
            contact.setStreet(street);
            contact.setStreetNo(streetNumber);
            contact.setCity(city);
            contact.setPostCode(zipCode);
        }

        else {
            contact = new Contact(name, street, streetNumber, zipCode, city);
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
