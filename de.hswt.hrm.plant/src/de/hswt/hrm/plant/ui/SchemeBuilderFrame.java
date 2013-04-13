package de.hswt.hrm.plant.ui;

import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.e4.xwt.DefaultLoadingContext;
import org.eclipse.e4.xwt.IConstants;
import org.eclipse.e4.xwt.IXWTLoader;
import org.eclipse.e4.xwt.XWT;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * This is the frame where new schemes can be created
 * 
 * @author Michael Sieger
 * 
 */
public class SchemeBuilderFrame extends Composite {

    public SchemeBuilderFrame(Composite parent, int style) {
        super(parent, style);
        setLayout(new FillLayout());
        // load XWT
        String name = SchemeBuilderFrame.class.getSimpleName() + IConstants.XWT_EXTENSION_SUFFIX;
        try {
            URL url = SchemeBuilderFrame.class.getResource(name);
            Map<String, Object> options = new HashMap<String, Object>();
            options.put(IXWTLoader.CLASS_PROPERTY, this);
            options.put(IXWTLoader.CONTAINER_PROPERTY, this);
            XWT.setLoadingContext(new DefaultLoadingContext(this.getClass().getClassLoader()));
            XWT.loadWithOptions(url, options);
        }
        catch (Throwable e) {
            throw new Error("Unable to load " + name, e);
        }
        XWT.getRealm().exec(new Runnable() {

            @Override
            public void run() {
                getTree().setContentProvider(GridImageContentProviderFactory.create(getDisplay()));
            }
        });

    }

    private TreeViewer getTree() {
        Object o = XWT.findElementByName(this, "tree");
        if (o == null) {
            throw new RuntimeException("Tree not found");
        }
        return (TreeViewer) o;
    }

}
