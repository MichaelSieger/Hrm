package de.hswt.hrm.main.ui.sidebarPart;

import java.net.URL;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.EPartService.PartState;
import org.eclipse.e4.xwt.IConstants;
import org.eclipse.e4.xwt.XWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ToolBar;


public class SidebarPart {
    
	@Inject
	EPartService service;

    @PostConstruct
    public void postConstruct(Composite parent, IEclipseContext context) {

        URL url = SidebarPart.class.getClassLoader().getResource(
                "de/hswt/hrm/main/ui/sidebarXwt/SideBar" + IConstants.XWT_EXTENSION_SUFFIX);

        try {

            // Obtain root element of the XWT file
            final Composite comp = (Composite) XWT.load(parent, url);
            
            SidebarPartUtil.setListenerForToolbar(service, (ToolBar)XWT.findElementByName(comp, "toolBar"));

        }
        catch (Exception e) {
        }
    }
}