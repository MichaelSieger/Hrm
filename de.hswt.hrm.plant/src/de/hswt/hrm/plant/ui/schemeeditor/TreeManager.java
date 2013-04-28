package de.hswt.hrm.plant.ui.schemeeditor;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import de.hswt.hrm.plant.model.GridImage;
import de.hswt.hrm.plant.model.RenderedGridImage;

/**
 * This class manages the TreeItems of a Tree that displays the GridImages.
 * The GridImages are organized by their category.
 * 
 * @author Michael Sieger
 *
 */
public class TreeManager {
    
    private static final int THUMBNAIL_GRID_SIZE = 10;

    private final Display display;
    private final IImageTreeModel model;
    private final Tree tree;

    public TreeManager(Display display, IImageTreeModel model, Tree tree) {
        super();
        this.model = model;
        this.tree = tree;
        this.display = display;
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
