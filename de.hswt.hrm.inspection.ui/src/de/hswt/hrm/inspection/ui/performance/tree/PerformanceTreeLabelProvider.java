package de.hswt.hrm.inspection.ui.performance.tree;

import org.eclipse.jface.viewers.LabelProvider;

import de.hswt.hrm.catalog.model.ICatalogItem;
import de.hswt.hrm.scheme.model.SchemeComponent;

public class PerformanceTreeLabelProvider extends LabelProvider {

	@Override
	public String getText(Object element) {
		
		if (element instanceof SchemeComponent){
			return ((SchemeComponent)element).getComponent().getName();
		}
		else if (element instanceof ICatalogItem){
			return ((ICatalogItem)element).getName();
		}
		return null;
	}

}
