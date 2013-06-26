package de.hswt.hrm.scheme.ui.tree;

import java.util.List;

import org.eclipse.swt.graphics.Image;

import de.hswt.hrm.scheme.model.RenderedComponent;
import de.hswt.hrm.scheme.model.Scheme;
import de.hswt.hrm.scheme.model.SchemeComponent;
import de.hswt.hrm.scheme.ui.dnd.DragData;

/**
 * Represents a item in the SchemeTree.
 * 
 * @author Michael Sieger
 *
 */
public abstract class SchemeTreeItem {
	
	private final SchemeTreeItem parent;
	
	/**
	 * Constructs the item with the given parent.
	 * The parent may be null, which means the item is a root node.
	 * 
	 * @param parent
	 */
	public SchemeTreeItem(SchemeTreeItem parent){
		this.parent = parent;
	}
	
	/**
	 * @return All children of this node.
	 */
	public abstract SchemeTreeItem[] getChildren();
	
	/**
	 * @return Will getChildren() return a non empty array.
	 */
	public abstract boolean hasChildren();
	
	public SchemeTreeItem getParent(){
		return parent;
	}
	
	/**
	 * @return The text to display for this item
	 */
	public abstract String getText();
	
	/**
	 * @return The Image to display for this item
	 */
	public abstract Image getImage();
	
	/**
	 * Returns what to be dragged, if a drag was started from this item.
	 * Can be null, if the item is not draggable.
	 * 
	 * @return
	 */
	public abstract DragData getDragItem(Scheme scheme, List<RenderedComponent> renderedComponents);

	public RenderedComponent getRenderedComponent() {
		return null;
	}

	public SchemeComponent getSchemeComponent() {
		return null;
	}
}
