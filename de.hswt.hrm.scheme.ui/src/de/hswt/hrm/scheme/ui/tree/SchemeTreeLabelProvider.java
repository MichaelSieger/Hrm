package de.hswt.hrm.scheme.ui.tree;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

/**
 * Implements the ILabelProvider interface and just redirects to the SchemeTreeItem interface.
 * 
 * @author Michael Sieger
 *
 */
public class SchemeTreeLabelProvider extends LabelProvider{

	
	
	@Override
	public Image getImage(Object element) {
		return ((SchemeTreeItem)element).getImage();
	}

	@Override
	public String getText(Object element) {
		return ((SchemeTreeItem)element).getText();
	}

	
	
}
