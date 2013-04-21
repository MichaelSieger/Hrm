package de.hswt.hrm.contact.ui.wizard;

import java.util.HashMap;

import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Text;

import de.hswt.hrm.common.database.exception.SaveException;
import de.hswt.hrm.contact.model.Contact;
import de.hswt.hrm.contact.service.ContactService;

public class ContactWizard extends Wizard {
    ContactWizardPageOne one;

    public ContactWizard() {
        setWindowTitle("Add a new Contact");

    }

    @Override
    public void addPages() {
        one = new ContactWizardPageOne("First Page");
        addPage(one);
    }

    @Override
    public boolean canFinish() {
        HashMap mandatoryWidgets = one.getMandatoryWidgets();
        for (Object object : mandatoryWidgets.values()) {
            Text textField = (Text) object;
            if (textField != null) {
                if (textField.getText().length() == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean performFinish() {
        HashMap mandatoryWidgets = one.getMandatoryWidgets();
        String firstName = ((Text) mandatoryWidgets.get("firstName")).getText();
        String lastName = ((Text) mandatoryWidgets.get("lastName")).getText();        
        String street = ((Text) mandatoryWidgets.get("street")).getText();
        String streetNumber = ((Text) mandatoryWidgets.get("streetNumber")).getText();
        String city = ((Text) mandatoryWidgets.get("city")).getText();
        String zipCode = ((Text) mandatoryWidgets.get("zipCode")).getText();
            
        Contact newContact = new Contact(lastName, firstName, street, streetNumber, zipCode, city);
        
        HashMap optionalWidgets = one.getOptionalWidgets();
        String shortcut = ((Text) optionalWidgets.get("shortcut")).getText();
        String phone = ((Text) optionalWidgets.get("phone")).getText();
        String fax = ((Text) optionalWidgets.get("fax")).getText();
        String mobilePhone = ((Text) optionalWidgets.get("mobilePhone")).getText();
        String email = ((Text) optionalWidgets.get("email")).getText(); 
        
        newContact.setShortcut(shortcut);
        newContact.setPhone(phone);
        newContact.setMobile(mobilePhone);
        newContact.setFax(fax);
        newContact.setEmail(email);  
        
        try {
			ContactService.insert(newContact);
		} catch (SaveException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
        return true;
    }

}
