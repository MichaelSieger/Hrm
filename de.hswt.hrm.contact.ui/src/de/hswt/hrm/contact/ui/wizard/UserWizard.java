package de.hswt.hrm.contact.ui.wizard;

import java.util.HashMap;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;

import de.hswt.hrm.contact.model.Contact;

public class UserWizard extends Wizard {
    WizardPageOne one;
    
    
    public UserWizard() {
        setWindowTitle("New Wizard");
    }

    @Override
    public void addPages() {
        one = new WizardPageOne("asd");
        addPage(one);
    }
   
    @Override
    public boolean canFinish(){
        HashMap widgets = one.getWidgets();
        for(Object object : widgets.values()){
            Text textField = (Text)object;
            if(textField.getText().length() == 0){
                return false;
            }          
        }
        return true;
    }

    @Override
    public boolean performFinish() { 
    	HashMap widgets = one.getWidgets();
	    String firstName = ((Text)widgets.get("firstName")).getText();
	    String lastName = ((Text) widgets.get("lastName")).getText();
	    String shorcut = ((Text) widgets.get("shortcut")).getText();
	    String street = ((Text) widgets.get("street")).getText();
	    String streetNumber = ((Text) widgets.get("streetNumber")).getText();
	    String city = ((Text) widgets.get("city")).getText();
	    String zipCode = ((Text) widgets.get("zipCode")).getText();
	    String phone = ((Text) widgets.get("phone")).getText();
	    String fax = ((Text) widgets.get("fax")).getText();
	    String mobilePhone = ((Text) widgets.get("mobilePhone")).getText();
	    String email = ((Text) widgets.get("email")).getText();
    	
    	
    	
    	Contact newContact = new Contact(lastName, firstName, street, streetNumber, zipCode, city);    	
        return true;
    }

}
