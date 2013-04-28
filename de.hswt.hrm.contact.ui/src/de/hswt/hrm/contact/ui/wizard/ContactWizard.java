package de.hswt.hrm.contact.ui.wizard;

import java.util.HashMap;

import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Text;

import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.database.exception.ElementNotFoundException;
import de.hswt.hrm.common.database.exception.SaveException;
import de.hswt.hrm.contact.model.Contact;
import de.hswt.hrm.contact.service.ContactService;

public class ContactWizard extends Wizard {
    private ContactWizardPageOne one;

    public ContactWizard(Contact c) {
        one = new ContactWizardPageOne("First Page", c);
        setWindowTitle("Neuen Kontakt hinzufügen");

    }

    @Override
    public void addPages() {

        addPage(one);
    }

    @Override
    public boolean canFinish() {
        return one.isPageComplete();
    }

    @Override
    public boolean performFinish() {

        if (one.getContact() == null) {
            return insertNewContact();
        }

        else {
            return editExistingCustomer();
        }

    }

    private boolean editExistingCustomer() {

        Contact c = null;

        try {
            c = ContactService.findById(one.getContact().getId());

        }
        catch (DatabaseException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        HashMap<String, Text> mandatoryWidgets = one.getMandatoryWidgets();
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

        HashMap<String, Text> optionalWidgets = one.getOptionalWidgets();
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
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (SaveException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return true;

    }

    private boolean insertNewContact() {
        HashMap<String, Text> mandatoryWidgets = one.getMandatoryWidgets();
        String firstName = mandatoryWidgets.get("firstName").getText();
        String lastName = mandatoryWidgets.get("lastName").getText();
        String street = mandatoryWidgets.get("street").getText();
        String streetNumber = mandatoryWidgets.get("streetNumber").getText();
        String city = mandatoryWidgets.get("city").getText();
        String zipCode = mandatoryWidgets.get("zipCode").getText();

        Contact newContact = new Contact(lastName, firstName, street, streetNumber, zipCode, city);

        HashMap<String, Text> optionalWidgets = one.getOptionalWidgets();
        String shortcut = optionalWidgets.get("shortcut").getText();
        String phone = optionalWidgets.get("phone").getText();
        String fax = optionalWidgets.get("fax").getText();
        String mobilePhone = optionalWidgets.get("mobilePhone").getText();
        String email = optionalWidgets.get("email").getText();

        newContact.setShortcut(shortcut);
        newContact.setPhone(phone);
        newContact.setMobile(mobilePhone);
        newContact.setFax(fax);
        newContact.setEmail(email);

        try {
            ContactService.insert(newContact);
        }
        catch (SaveException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return true;
    }

}
