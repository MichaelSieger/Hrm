package de.hswt.hrm.scheme.ui.tree;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

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
