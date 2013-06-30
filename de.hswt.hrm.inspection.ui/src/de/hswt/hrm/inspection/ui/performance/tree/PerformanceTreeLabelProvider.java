package de.hswt.hrm.inspection.ui.performance.tree;

import org.eclipse.jface.viewers.ITableColorProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Color;

import de.hswt.hrm.catalog.model.Activity;
import de.hswt.hrm.catalog.model.tree.TreeCurrent;
import de.hswt.hrm.catalog.model.tree.TreeTarget;

public class PerformanceTreeLabelProvider extends LabelProvider {
	//implements ITableColorProvider {

    @Override
    public String getText(Object element) {
        if (element instanceof TreeTarget) {
            return ((TreeTarget)element).getName();
        }
        else  if (element instanceof TreeCurrent) {
            return ((TreeCurrent)element).getName();
        }
        else  if (element instanceof Activity) {
            return ((Activity)element).getName();
        }
        return null;
       
    }

//	@Override
//	public Color getForeground(Object element, int columnIndex) {
//		System.out.println(columnIndex);
//		return null;
//	}
//
//	@Override
//	public Color getBackground(Object element, int columnIndex) {
//		// TODO Auto-generated method stub
//		return null;
//	}

}
