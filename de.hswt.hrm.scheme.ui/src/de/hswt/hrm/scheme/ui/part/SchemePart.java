package de.hswt.hrm.scheme.ui.part;

import java.net.URL;

import javax.annotation.PostConstruct;

import org.eclipse.e4.xwt.IConstants;
import org.eclipse.e4.xwt.XWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;

import de.hswt.hrm.scheme.ui.GridDNDManager;
import de.hswt.hrm.scheme.ui.ImageTreeModelFactory;
import de.hswt.hrm.scheme.ui.SchemeGrid;
import de.hswt.hrm.scheme.ui.TreeDNDManager;
import de.hswt.hrm.scheme.ui.TreeManager;

public class SchemePart {
	
	private static final int DRAG_OPS = DND.DROP_COPY, DROP_OPS = DND.DROP_COPY;

	private Composite root;
	private SchemeGrid grid;

	@PostConstruct
	public void postConstruct(Composite parent) {

		URL url = SchemePart.class.getClassLoader().getResource(
				"de/hswt/hrm/scheme/ui/SchemeBuilderFrame"
						+ IConstants.XWT_EXTENSION_SUFFIX);

		try {

			root = (Composite) XWT.load(parent, url);
			Tree tree = getTree();
			 new
			 TreeManager(ImageTreeModelFactory.create(parent.getDisplay()),
			 tree);
			grid = new SchemeGrid(getSchemeComposite(), SWT.NONE, 40, 20);
	        DropTarget dt = new DropTarget(grid, DROP_OPS);
	        dt.setTransfer(new Transfer[] { TextTransfer.getInstance() });
	        DragSource treeDragSource = new DragSource(tree, DRAG_OPS);
	        treeDragSource.setTransfer(new Transfer[] { TextTransfer.getInstance()});
	        DragSource gridDragSource = new DragSource(grid, DRAG_OPS);
	        gridDragSource.setTransfer(new Transfer[]{TextTransfer.getInstance()});
			new TreeDNDManager(getTree(), grid, dt, treeDragSource);
			new GridDNDManager(grid, dt, gridDragSource);
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

}