package de.hswt.hrm.scheme.ui.part;

import java.net.URL;

import javax.annotation.PostConstruct;

import org.eclipse.e4.xwt.IConstants;
import org.eclipse.e4.xwt.XWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;

import de.hswt.hrm.scheme.ui.GridDNDManager;
import de.hswt.hrm.scheme.ui.ImageTreeModelFactory;
import de.hswt.hrm.scheme.ui.SchemeGrid;
import de.hswt.hrm.scheme.ui.TreeDNDManager;
import de.hswt.hrm.scheme.ui.TreeManager;

public class SchemePart {

	private Composite root;
	private SchemeGrid grid;

	@PostConstruct
	public void postConstruct(Composite parent) {

		System.out.println(parent.getDisplay());

		URL url = SchemePart.class.getClassLoader().getResource(
				"de/hswt/hrm/scheme/ui/SchemeBuilderFrame"
						+ IConstants.XWT_EXTENSION_SUFFIX);

		try {

			root = (Composite) XWT.load(parent, url);
			Tree tree = getTree();
			 new
			 TreeManager(ImageTreeModelFactory.create(parent.getDisplay()),
			 tree);
			grid = new SchemeGrid(getSchemeComposite(), SWT.NONE, 10, 10);
			new TreeDNDManager(getTree(), grid);
			new GridDNDManager(grid);
		} catch (Throwable e) {
			throw new Error("Unable to load ", e);
		}

	}

	protected Tree getTree() {
		return (Tree) XWT.findElementByName(root, "tree");
	}

	private Composite getSchemeComposite() {
		return (Composite) XWT.findElementByName(root, "schemeComposite");
	}

	protected SchemeGrid getGrid() {
		return (SchemeGrid) getSchemeComposite().getChildren()[0];
	}

}