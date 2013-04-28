package de.hswt.hrm.plant.ui.schemeeditor;

import java.net.URL;

import org.eclipse.e4.xwt.IConstants;
import org.eclipse.e4.xwt.XWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;

/**
 * This is the frame where new schemes can be created
 * 
 * @author Michael Sieger
 * 
 */
public class SchemeBuilderFrame extends Composite {
	
	private static final int SCHEME_WIDTH = 20,
							 SCHEME_HEIGHT = 10;
    
    private final Composite root;
    
    private final SchemeGrid grid;

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
            new TreeManager(ImageTreeModelFactory.create(
            						getDisplay()), tree);
            grid = new SchemeGrid(getSchemeComposite(), 
            		SWT.NONE, SCHEME_WIDTH, SCHEME_HEIGHT);
            new DNDManager(this);
        }
        catch (Throwable e) {
            throw new Error("Unable to load " + name, e);
        }

    }

    protected Tree getTree() {
        return (Tree) XWT.findElementByName(root, "tree");
    }
    
    private Composite getSchemeComposite(){
    	return (Composite) XWT.findElementByName(root, "schemeComposite");
    }
    
    protected SchemeGrid getGrid(){
        return (SchemeGrid) getSchemeComposite().getChildren()[0];
    }

}
