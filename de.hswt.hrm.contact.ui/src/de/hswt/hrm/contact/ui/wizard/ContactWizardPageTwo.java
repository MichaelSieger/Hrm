package de.hswt.hrm.contact.ui.wizard;

import java.net.URL;
import java.util.HashMap;

import org.apache.commons.validator.routines.EmailValidator;
import org.eclipse.e4.xwt.IConstants;
import org.eclipse.e4.xwt.XWT;
import org.eclipse.e4.xwt.forms.XWTForms;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.Section;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;

import de.hswt.hrm.common.ui.swt.forms.FormUtil;
import de.hswt.hrm.common.ui.swt.layouts.PageContainerFillLayout;
import de.hswt.hrm.contact.model.Contact;
import de.hswt.hrm.i18n.I18n;
import de.hswt.hrm.i18n.I18nFactory;

public class ContactWizardPageTwo extends WizardPage{
    
    private static final Logger LOG = LoggerFactory.getLogger(ContactWizardPageTwo.class);
    private static final I18n I18N = I18nFactory.getI18n(ContactWizardPageTwo.class);
    
    private Composite container;
    private Optional<Contact> contact;
    private HashMap<String, Text> optionalWidgets;
    
    private EmailValidator emailVal = EmailValidator.getInstance();

    public ContactWizardPageTwo(String pageName, Optional<Contact> contact) {
        super(pageName);
        this.contact = contact;
        setDescription(I18N.tr("Provide the contacts communication information"));
        setTitle(I18N.tr("Contact Wizard"));
    }
    
    @Override
    public void createControl(Composite parent) {
        parent.setLayout(new PageContainerFillLayout());
        URL url = ContactWizardPageTwo.class.getClassLoader().getResource(
                "de/hswt/hrm/contact/ui/xwt/ContactWizardWindowPageTwo"+IConstants.XWT_EXTENSION_SUFFIX);
        try {
            container = (Composite) XWTForms.load(parent, url);
        } catch (Exception e) {
            LOG.error("An error occured", e);
        }
        translate (container);
        if (this.contact.isPresent()) {
            updateFields(container);
        }
        FormUtil.initSectionColors((Section) XWT.findElementByName(container, "Optional"));
        setControl(container);
        setKeyListener();
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
        if (optionalWidgets == null) {
            optionalWidgets = new HashMap<String, Text>();
            optionalWidgets.put("shortcut", (Text) XWT.findElementByName(container, "shortcut"));
            optionalWidgets.put("phone", (Text) XWT.findElementByName(container, "phone"));
            optionalWidgets.put("fax", (Text) XWT.findElementByName(container, "fax"));
            optionalWidgets.put("mobilePhone", (Text) XWT.findElementByName(container, "mobilePhone"));
            optionalWidgets.put("email", (Text) XWT.findElementByName(container, "email")); 
        }
        return optionalWidgets;
    }
    
    @Override
    public boolean isPageComplete() {
        Text eMail = (Text) XWT.findElementByName(container, "email");
        if (eMail.getText().length() != 0 && !emailVal.isValid(eMail.getText())) {
            setErrorMessage(I18N.tr("Invalid input for field") + " " + I18N.tr(XWT.getElementName((Object) eMail)));
            return false;
        }
        setErrorMessage(null);
        return true;
    }
    
    private void setKeyListener() {
        Text eMail = (Text) XWT.findElementByName(container, "email");
        eMail.addKeyListener(new KeyListener() {

                @Override
                public void keyPressed(KeyEvent e) {
                }

                @Override
                public void keyReleased(KeyEvent e) {
                    getWizard().getContainer().updateButtons();
                }
            });
    }
    
    private void translate(Composite container) {
        // Section
        setSectionText(container, "Optional", I18N.tr("Communication"));
        // Labels
        setLabelText(container, "lblPhone", I18N.tr("Phone")+":");
        setLabelText(container, "lblMobilePhone", I18N.tr("Mobile")+":");
        setLabelText(container, "lblFax", I18N.tr("Fax")+":");
        setLabelText(container, "lblEmail", I18N.tr("Email")+":");
        setLabelText(container, "lblShortcut", I18N.tr("Shortcut")+":");     
    }
        
    private void setLabelText(Composite container, String labelName, String text) {
        Label l = (Label) XWT.findElementByName(container, labelName);
        if (l == null) {
            LOG.error("Label '" + labelName + "' not found.");
            return;
        }
        l.setText(text);
    }
    
    private void setSectionText(Composite container, String sectionName, String text) {
        Section s = (Section) XWT.findElementByName(container, sectionName);
        if (s == null) {
            LOG.error("Section '" + sectionName + "' not found.");
            return;
        }
        s.setText(text);
    }
}
