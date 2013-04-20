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

public class UserWizardWindow extends Composite{
    @UI
    Group common;
    @UI
    Group adress;
    @UI
    Group contactData;

	public UserWizardWindow(Composite parent, int style) {
		super(parent, style);
		setLayout(new FillLayout());
		// load XWT
		String name = UserWizardWindow.class.getSimpleName()
				+ IConstants.XWT_EXTENSION_SUFFIX;
		try {
			URL url = UserWizardWindow.class.getResource(name);
			Map<String, Object> options = new HashMap<String, Object>();
			options.put(IXWTLoader.CLASS_PROPERTY, this);
			options.put(IXWTLoader.CONTAINER_PROPERTY, this);
			XWT.setLoadingContext(new DefaultLoadingContext(this.getClass()
					.getClassLoader()));
			XWT.loadWithOptions(url, options);
		} catch (Throwable e) {
			throw new Error("Unable to load " + name, e);
		}
	}

	public HashMap getWidgets(){
	    HashMap<String, Text> widgets = new HashMap<String, Text>();
	    widgets.put("firstName",(Text)XWT.findElementByName(common,"firstName"));
	    widgets.put("lastName",(Text)XWT.findElementByName(common,"lastName"));
	    widgets.put("shortcut",(Text)XWT.findElementByName(common,"shortcut"));
	    widgets.put("street",(Text)XWT.findElementByName(common,"street"));
	    widgets.put("streetNumber",(Text)XWT.findElementByName(common,"streetNumber"));
        widgets.put("city",(Text)XWT.findElementByName(common,"city"));
        widgets.put("zipCode",(Text)XWT.findElementByName(common,"zipCode"));
        widgets.put("phone",(Text)XWT.findElementByName(common,"phone"));
        widgets.put("fax",(Text)XWT.findElementByName(common,"fax"));
        widgets.put("mobilePhone",(Text)XWT.findElementByName(common,"mobilePhone"));
        widgets.put("email",(Text)XWT.findElementByName(common,"email"));	    	    
	    return widgets;	    
	}
}
