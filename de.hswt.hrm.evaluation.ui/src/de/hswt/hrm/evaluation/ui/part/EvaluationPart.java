package de.hswt.hrm.evaluation.ui.part;

import java.net.URL;

import javax.annotation.PostConstruct;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.xwt.IConstants;
import org.eclipse.e4.xwt.XWT;
import org.eclipse.e4.xwt.forms.XWTForms;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hswt.hrm.common.ui.xwt.XwtHelper;

public class EvaluationPart {

    private static final Logger LOG = LoggerFactory.getLogger(EvaluationPart.class);

    @PostConstruct
    public void postConstruct(Composite parent, IEclipseContext context) {
        FillLayout layout = new FillLayout();
        layout.marginHeight = 2;
        layout.marginWidth = 2;
        parent.setLayout(layout);
        URL url = EvaluationPart.class.getClassLoader().getResource(
                "de/hswt/hrm/evaluation/ui/xwt/EvaluationView" + IConstants.XWT_EXTENSION_SUFFIX);

        try {

            // final ContactEventHandler eventHandler = ContextInjectionFactory.make(
            // ContactEventHandler.class, context);
            // Obtain root element of the XWT file
            final Composite comp = (Composite) XWTForms.load(parent, url);
            // final Composite comp = XwtHelper.loadFormWithEventHandler(parent, url, eventHandler);
            // final Composite comp = XwtHelper.loadWithEventHandler(parent, url, eventHandler);

            // LOG.debug("XWT load successfully");
            //
            // // Obtain TableViwer to fill it with data
            // viewer = (TableViewer) XWT.findElementByName(comp, "contactTable");
            // initializeTable(parent, viewer);
            // refreshTable(parent);

        }
        catch (Exception e) {
            LOG.error("Could not load XWT file from resource", e);
        }

        // if (contactService == null) {
        // LOG.error("ContactService not injected to ContactPart");
        // }

    }

}
