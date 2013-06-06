package de.hswt.hrm.contact.ui.wizard;

import java.net.URL;
import java.util.HashMap;

import org.eclipse.e4.xwt.IConstants;
import org.eclipse.e4.xwt.XWT;
import org.eclipse.e4.xwt.forms.XWTForms;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;

import de.hswt.hrm.contact.model.Contact;

public class ContactWizardPageTwo extends WizardPage{
    
    private static final Logger LOG = LoggerFactory.getLogger(ContactWizardPageTwo.class);
    
    private Composite container;
    private Optional<Contact> contact;

    public ContactWizardPageTwo(String pageName, Optional<Contact> contact) {
        super(pageName);
        this.contact = contact;
        setDescription("Provide the contacts communication information.");
    }
    
    @Override
    public void createControl(Composite parent) {
        URL url = ContactWizardPageTwo.class.getClassLoader().getResource(
                "de/hswt/hrm/contact/ui/xwt/ContactWizardWindowPageTwo"+IConstants.XWT_EXTENSION_SUFFIX);
        try {
            container = (Composite) XWTForms.load(parent, url);
        } catch (Exception e) {
            LOG.error("An error occured", e);
        }
        if (this.contact.isPresent()) {
            updateFields(container);
        }
        setControl(container);
    }
    
    private void updateFields(Composite Container) {
        Contact c = contact.get();
        Text t = (Text) XWT.findElementByName(container, "phone");
        t.setText(c.getPhone().or(""));
        t = (Text) XWT.findElementByName(container, "fax");
        t.setText(c.getFax().or(""));
        t = (Text) XWT.findElementByName(container, "mobilePhone");
        t.setText(c.getMobile().or(""));
        t = (Text) XWT.findElementByName(container, "email");
        t.setText(c.getEmail().or(""));
        t = (Text) XWT.findElementByName(container, "shortcut");
        t.setText(c.getShortcut().or(""));
    }
    
    public HashMap<String, Text> getOptionalWidgets() {
        HashMap<String, Text> widgets = new HashMap<String, Text>();
        widgets.put("shortcut", (Text) XWT.findElementByName(container, "shortcut"));
        widgets.put("phone", (Text) XWT.findElementByName(container, "phone"));
        widgets.put("fax", (Text) XWT.findElementByName(container, "fax"));
        widgets.put("mobilePhone", (Text) XWT.findElementByName(container, "mobilePhone"));
        widgets.put("email", (Text) XWT.findElementByName(container, "email"));

        return widgets;
    }

}
