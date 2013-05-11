package de.hswt.hrm.scheme.ui.tree;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

/**
 * Filters the tree by Component names
 * 
 * @author Michael Sieger
 *
 */
public class SchemeTreeViewerFilter extends ViewerFilter{
	
	private final String name;
	
	public SchemeTreeViewerFilter(String name){
		this.name = name;
	}

	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if(element.getClass() == ComponentTreeItem.class){
			ComponentTreeItem c = (ComponentTreeItem) element;
			return c.getRenderedComponent().getComponent().getName().startsWith(name);
		}
		return true;
	}

}
