package de.hswt.hrm.plant.ui.schemeeditor;

import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.e4.xwt.DefaultLoadingContext;
import org.eclipse.e4.xwt.IConstants;
import org.eclipse.e4.xwt.IXWTLoader;
import org.eclipse.e4.xwt.XWT;
import org.eclipse.jface.viewers.LabelProvider;
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
            XWT.load(url);
            XWT.getRealm().asyncExec(new Runnable() {
				
				@Override
				public void run() {
		            getTree().setContentProvider(
		            		GridImageContentProviderFactory.create(getDisplay()));
		            getTree().setLabelProvider(new LabelProvider());
		            getTree().setInput("");
				}
			});

        }
        catch (Throwable e) {
            throw new Error("Unable to load " + name, e);
        }

    }

    private TreeViewer getTree() {
        Object o = XWT.findElementByName(this, "tree");
        if (o == null) {
            throw new RuntimeException("Tree not found");
        }
        return (TreeViewer) o;
    }

}
