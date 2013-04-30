package de.hswt.hrm.scheme.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import de.hswt.hrm.scheme.model.RenderedGridImage;

/**
 * This class manages the TreeItems of a Tree that displays the GridImages.
 * The GridImages are organized by their category.
 * 
 * @author Michael Sieger
 *
 */
public class TreeManager {

    private final IImageTreeModel model;
    private final Tree tree;

    public TreeManager(IImageTreeModel model, Tree tree) {
        super();
        this.model = model;
        this.tree = tree;
        generateTreeItems();
    }

    private void generateTreeItems(){
		tree.clearAll(true);
		for(RenderedGridImage img : model.getImages()){
			TreeItem item = new TreeItem(tree, SWT.NONE);
			item.setImage(img.getThumbnail());
			item.setData(img);
		}
	}
}
