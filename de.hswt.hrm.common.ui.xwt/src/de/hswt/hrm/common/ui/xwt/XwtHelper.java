package de.hswt.hrm.common.ui.xwt;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.e4.xwt.IXWTLoader;
import org.eclipse.e4.xwt.XWT;
import org.eclipse.e4.xwt.forms.XWTForms;
import org.eclipse.swt.widgets.Composite;

public final class XwtHelper {
    
    private XwtHelper() { }
    
    public static Composite loadWithEventHandler(Composite parent, URL url, Object eventHandler) 
            throws Exception {
        
        Map<String, Object> options = new HashMap<>();
        options.put(IXWTLoader.CONTAINER_PROPERTY, parent);
        options.put(IXWTLoader.CLASS_PROPERTY, eventHandler);
        
        final Composite composite = (Composite) XWT.loadWithOptions(url, options);
        return composite;
    }

    public static Composite loadFormWithEventHandler(Composite parent, URL url, Object eventHandler) 
            throws Exception {
        
        Map<String, Object> options = new HashMap<>();
        options.put(IXWTLoader.CONTAINER_PROPERTY, parent);
        options.put(IXWTLoader.CLASS_PROPERTY, eventHandler);
        final Composite composite = (Composite) XWTForms.loadWithOptions(url, options);
        return composite;
    }
}
