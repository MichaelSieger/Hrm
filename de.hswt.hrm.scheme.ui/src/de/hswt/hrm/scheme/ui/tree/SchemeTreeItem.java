package de.hswt.hrm.scheme.ui.tree;

import org.eclipse.swt.graphics.Image;

public interface SchemeTreeItem {
	
	public SchemeTreeItem[] getChildren();
	
	public boolean hasChildren();
	
	public SchemeTreeItem getParent();
	
	public String getText();
	
	public Image getImage();
}
