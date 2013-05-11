package de.hswt.hrm.scheme.ui.tree;

import java.util.Collection;
import java.util.Iterator;

import org.eclipse.swt.graphics.Image;

import de.hswt.hrm.scheme.model.Category;
import de.hswt.hrm.scheme.model.RenderedComponent;
import de.hswt.hrm.scheme.ui.DirectedRenderedComponent;

/**
 * This class represents a Category in the SchemeTreeViewer
 * 
 * @author Michael Sieger
 *
 */
public class CategoryTreeItem extends SchemeTreeItem{
	
	private final Category c;
	private final Collection<RenderedComponent> renComps;
	
	public CategoryTreeItem(Category c, Collection<RenderedComponent> renComps){
		/*
		 * Category is top level -> no parent
		 */
		super(null);
		this.c = c;
		this.renComps = renComps;
	}

	@Override
	public SchemeTreeItem[] getChildren() {
		ComponentTreeItem[] cTreeItems = new ComponentTreeItem[renComps.size()];
		Iterator<RenderedComponent> it = renComps.iterator();
		for(int i = 0; i < cTreeItems.length; i++){
			RenderedComponent c = it.next();
			cTreeItems[i] = new ComponentTreeItem(this, c);
		}
		return cTreeItems;
	}

	@Override
	public boolean hasChildren() {
		return true;
	}

	@Override
	public SchemeTreeItem getParent() {
		return null;
	}

	@Override
	public String getText() {
		return c.getName();
	}

	@Override
	public Image getImage() {
		return null;
	}

	@Override
	public DirectedRenderedComponent getDragItem() {
		return null;
	}
	
}
