package de.hswt.hrm.plant.ui.schemeeditor;

import java.net.URL;

import org.eclipse.e4.xwt.IConstants;
import org.eclipse.e4.xwt.XWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import de.hswt.hrm.plant.model.GridImage;

/**
 * This is the frame where new schemes can be created
 * 
 * @author Michael Sieger
 * 
 */
public class SchemeBuilderFrame extends Composite {
    
    private final Composite root;

    public SchemeBuilderFrame(Composite parent, int style) {
        super(parent, style);
        setLayout(new FillLayout());
        // load XWT
        String name = SchemeBuilderFrame.class.getSimpleName() + IConstants.XWT_EXTENSION_SUFFIX;
        try {
            URL url = SchemeBuilderFrame.class.getResource(name);
            root = (Composite) XWT.load(url);
            root.setParent(this);
            Tree tree = getTree();
            new TreeManager(getDisplay(), 
            				ImageTreeModelFactory.create(
            						getDisplay()), tree);
        }
        catch (Throwable e) {
            throw new Error("Unable to load " + name, e);
        }

    }

    private Tree getTree() {
        return (Tree) XWT.findElementByName(root, "tree");
    }

}
