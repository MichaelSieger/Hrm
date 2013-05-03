package de.hswt.hrm.scheme.ui;

import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import de.hswt.hrm.scheme.model.Category;
import de.hswt.hrm.scheme.model.RenderedComponent;
import de.hswt.hrm.scheme.model.ThumbnailImage;

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
		HashMap<Category, TreeItem> categoryItems = new HashMap<>();
		
		for(RenderedComponent comp : model.getImages()){
			Category c = comp.getComponent().getCategory();
			TreeItem categoryItem = categoryItems.get(c);
			if(categoryItem == null){
				categoryItem = new TreeItem(tree, SWT.NONE);
				categoryItems.put(c, categoryItem);
			}
			TreeItem item = new TreeItem(categoryItem, SWT.NONE);
			item.setText(c.getName());
			addImage(comp.getDownUp(), comp, item);
			addImage(comp.getLeftRight(), comp, item);
			addImage(comp.getRightLeft(), comp, item);
			addImage(comp.getTopDown(), comp, item);
		}
	}
    
    private void addImage(ThumbnailImage img, RenderedComponent comp, TreeItem parent){
    	TreeItem item = new TreeItem(parent, SWT.NONE);
    	item.setImage(img.getThumbnail());
    	item.setData(parent);
    }
}
