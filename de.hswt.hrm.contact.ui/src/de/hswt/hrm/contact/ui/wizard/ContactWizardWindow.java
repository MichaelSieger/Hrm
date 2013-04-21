package de.hswt.hrm.contact.ui.wizard;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.e4.xwt.DefaultLoadingContext;
import org.eclipse.e4.xwt.IConstants;
import org.eclipse.e4.xwt.IXWTLoader;
import org.eclipse.e4.xwt.XWT;
import org.eclipse.e4.xwt.annotation.UI;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;

public class ContactWizardWindow extends Composite {
    @UI
    Group mandatory;   
    @UI
    Group optional;

    public ContactWizardWindow(Composite parent, int style) {
        super(parent, style);
        setLayout(new FillLayout());
        // load XWT

        try {
            URL url = ContactWizardWindow.class.getClassLoader().getResource(
                    "de/hswt/hrm/contact/ui/xwt/ContactWizardWindow"
                            + IConstants.XWT_EXTENSION_SUFFIX);
            Map<String, Object> options = new HashMap<String, Object>();
            options.put(IXWTLoader.CLASS_PROPERTY, this);
            options.put(IXWTLoader.CONTAINER_PROPERTY, this);
            XWT.setLoadingContext(new DefaultLoadingContext(this.getClass().getClassLoader()));
            XWT.loadWithOptions(url, options);

        }
        catch (Throwable e) {
            throw new Error("Unable to load " + "name", e);
        }
    }

    public HashMap getMandatoryWidgets() {    	
        HashMap<String, Text> widgets = new HashMap<String, Text>();
        widgets.put("firstName", (Text) XWT.findElementByName(mandatory, "firstName"));
        widgets.put("lastName", (Text) XWT.findElementByName(mandatory, "lastName"));
        widgets.put("street", (Text) XWT.findElementByName(mandatory, "street"));
        widgets.put("streetNumber", (Text) XWT.findElementByName(mandatory, "streetNumber"));
        widgets.put("city", (Text) XWT.findElementByName(mandatory, "city"));
        widgets.put("zipCode", (Text) XWT.findElementByName(mandatory, "zipCode"));
        return widgets;
    }
    public HashMap getOptionalWidgets() {    	
        HashMap<String, Text> widgets = new HashMap<String, Text>();
        widgets.put("shortcut", (Text) XWT.findElementByName(mandatory, "shortcut"));
        widgets.put("phone", (Text) XWT.findElementByName(mandatory, "phone"));
        widgets.put("fax", (Text) XWT.findElementByName(mandatory, "fax"));
        widgets.put("mobilePhone", (Text) XWT.findElementByName(mandatory, "mobilePhone"));
        widgets.put("email", (Text) XWT.findElementByName(mandatory, "email"));
        return widgets;
    }
}
