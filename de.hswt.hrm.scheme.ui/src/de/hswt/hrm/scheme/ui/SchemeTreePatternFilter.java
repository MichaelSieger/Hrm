package de.hswt.hrm.scheme.ui;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.dialogs.PatternFilter;

import de.hswt.hrm.scheme.ui.tree.SchemeTreeItem;

/**
 * This class manages which items in the FilteredTree are visible.
 * A item is visible, if its name starts with the search string, 
 * or if a parent or child is visible.
 * 
 * @author Michael Sieger
 *
 */
public class SchemeTreePatternFilter extends PatternFilter{
	
	@Override
	protected boolean isParentMatch(Viewer viewer, Object element) {
		return super.isParentMatch(viewer, element);
	}

	@Override
	protected boolean isLeafMatch(Viewer viewer, Object element) {
		return hasParentMatch((SchemeTreeItem) element);
	}

	/**
	 * Does the item or any of the children match the search word?
	 * 
	 * @param item
	 * @return
	 */
	private boolean hasParentMatch(SchemeTreeItem item){
		while(item != null){
			if(wordMatches(item.getText())){
				return true;
			}
			item = item.getParent();
		}
		return false;
	}
	
}
