package de.hswt.hrm.scheme.ui.tree;

import org.eclipse.swt.graphics.Image;

import de.hswt.hrm.scheme.ui.DirectedRenderedComponent;

public abstract class SchemeTreeItem {
	
	private final SchemeTreeItem parent;
	
	public SchemeTreeItem(SchemeTreeItem parent){
		this.parent = parent;
	}
	
	public abstract SchemeTreeItem[] getChildren();
	
	public abstract boolean hasChildren();
	
	public SchemeTreeItem getParent(){
		return parent;
	}
	
	public abstract String getText();
	
	public abstract Image getImage();
	
	public abstract DirectedRenderedComponent getDragItem();
}
