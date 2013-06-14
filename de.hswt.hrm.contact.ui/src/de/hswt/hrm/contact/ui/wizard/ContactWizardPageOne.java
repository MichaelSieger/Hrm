package de.hswt.hrm.contact.ui.wizard;

import java.net.URL;
import java.util.HashMap;

import org.apache.commons.validator.routines.RegexValidator;
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

public class ContactWizardPageOne extends WizardPage {
    private static final Logger LOG = LoggerFactory.getLogger(ContactWizardPageOne.class);
    private static final I18n I18N = I18nFactory.getI18n(ContactWizardPageOne.class);

    private Composite container;
    private Optional<Contact> contact;

    private RegexValidator plzVal = new RegexValidator("[0-9]{5}");
    private RegexValidator textOnlyVal = new RegexValidator("([A-ZÄÖÜ]{1}[a-zäöü]+[\\s]?[\\-]?)*");
    private RegexValidator streetNoVal = new RegexValidator("[0-9]+[a-z]?");

    public ContactWizardPageOne(String pageName, Optional<Contact> contact) {
        super(pageName);
        this.contact = contact;
        setDescription(createDiscription());
        setTitle("Contact Wizard");
    }

    private String createDiscription() {
        if (contact.isPresent()) {
            return I18N.tr("Edit a contact.");
        }

        return I18N.tr("Add a new contact.");
    }

    @Override
    public void createControl(Composite parent) {
        parent.setLayout(new PageContainerFillLayout());
        URL url = ContactWizardPageOne.class.getClassLoader().getResource(
                "de/hswt/hrm/contact/ui/xwt/ContactWizardWindowPageOne"
                        + IConstants.XWT_EXTENSION_SUFFIX);
        try {
            container = (Composite) XWTForms.load(parent, url);
        }
        catch (Exception e) {
            LOG.error("An error occured", e);
        }

        translate(container);

        if (this.contact.isPresent()) {
            updateFields(container);
        }
        
        FormUtil.initSectionColors((Section) XWT.findElementByName(container, "Mandatory"));
        setKeyListener();
        setControl(container);
        setPageComplete(false);
        setToolTips();
    }
    
    private void setToolTips() {
        HashMap<String, Text> widgets = getMandatoryWidgets();
        for (Text text : widgets.values()) {
            text.setToolTipText(XWT.getElementName((Object) text));
        }
    }

    private void setLabelText(final Composite container, final String labelName, final String text) {

        Label l = (Label) XWT.findElementByName(container, labelName);
        if (l == null) {
            LOG.error("Label '" + labelName + "' not found.");
            return;
        }

        l.setText(text);
    }

    private void translate(final Composite container) {
        setLabelText(container, "lblName", I18N.tr("Name"));
        setLabelText(container, "lblStreet", I18N.tr("Street"));
        setLabelText(container, "lblStreetNumber", I18N.tr("Streetnumber"));
        setLabelText(container, "lblCity", I18N.tr("City"));
        setLabelText(container, "lblZipCode", I18N.tr("Zipcode"));
        // setLabelText(container, "lblPhone", "Phone");
        // setLabelText(container, "lblFax", "Fax");
        // setLabelText(container, "lblMobilePhone", "Mobile");
        // setLabelText(container, "lblEmail", "Email");
        // setLabelText(container, "lblShortcut", "Shortcut");
    }

    private void updateFields(final Composite container) {
        Contact c = contact.get();

        Text t = (Text) XWT.findElementByName(container, "name");
        t.setText(c.getName());
        t = (Text) XWT.findElementByName(container, "street");
        t.setText(c.getStreet());
        t = (Text) XWT.findElementByName(container, "streetNumber");
        t.setText(c.getStreetNo());
        t = (Text) XWT.findElementByName(container, "city");
        t.setText(c.getCity());
        t = (Text) XWT.findElementByName(container, "zipCode");
        t.setText(c.getPostCode());
    }

    public HashMap<String, Text> getMandatoryWidgets() {
        HashMap<String, Text> widgets = new HashMap<String, Text>();
        widgets.put("name", (Text) XWT.findElementByName(container, "name"));
        widgets.put("street", (Text) XWT.findElementByName(container, "street"));
        widgets.put("streetNumber", (Text) XWT.findElementByName(container, "streetNumber"));
        widgets.put("city", (Text) XWT.findElementByName(container, "city"));
        widgets.put("zipCode", (Text) XWT.findElementByName(container, "zipCode"));

        return widgets;
    }
    
    @Override
    public boolean isPageComplete() {
        // Mandatory fields
        HashMap<String, Text> mandatory = getMandatoryWidgets();
        // Sorted array
        Text[] manArray = { mandatory.get("name"), mandatory.get("street"),
                mandatory.get("streetNumber"), mandatory.get("zipCode"), mandatory.get("city") };
        boolean isValid;
        for (int i = 0; i < manArray.length; i++) {
            isValid = checkValidity(manArray[i]);
            if (manArray[i].getText().length() == 0) {
                setErrorMessage("Field \"" + XWT.getElementName((Object) manArray[i]) + "\" is mandatory.");
                return false;
            }
            if (!isValid) {
                setErrorMessage("Input for \"" + XWT.getElementName((Object) manArray[i]) + "\" is invalid.");
                return false;
            }
        }
        setErrorMessage(null);
        return true;
    }

    private boolean checkValidity(Text textField) {
        String textFieldName = XWT.getElementName((Object) textField);

        boolean isInvalidText = (textFieldName.equals("name") || textFieldName.equals("city"))
                && (!textOnlyVal.isValid(textField.getText()));
        boolean isInvalidStreetNumber = (textFieldName.equals("streetNumber"))
                && (!streetNoVal.isValid(textField.getText()));
        boolean isInvalidZipCode = (textFieldName.equals("zipCode"))
                && (!plzVal.isValid(textField.getText()));

        if (isInvalidText || isInvalidStreetNumber || isInvalidZipCode) {
            return false;
        }
        return true;
    }

    public void setKeyListener() {
        HashMap<String, Text> widgets = getMandatoryWidgets();
        for (Text text : widgets.values()) {

            text.addKeyListener(new KeyListener() {

                @Override
                public void keyPressed(KeyEvent e) {
                }

                @Override
                public void keyReleased(KeyEvent e) {
                    getWizard().getContainer().updateButtons();
                }
            });
        }
    }

}