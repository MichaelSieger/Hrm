package de.hswt.hrm.inspection.ui.performance.tree;

import org.eclipse.jface.viewers.ITableColorProvider;
import org.eclipse.jface.viewers.ITableFontProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;

import de.hswt.hrm.catalog.model.Activity;
import de.hswt.hrm.catalog.model.tree.TreeCurrent;
import de.hswt.hrm.catalog.model.tree.TreeTarget;
import de.hswt.hrm.common.ui.swt.utils.SWTResourceManager;

public class PerformanceTreeLabelProvider extends LabelProvider implements ITableLabelProvider, ITableFontProvider, ITableColorProvider {

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

	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		return null;
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
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

	@Override
	public Font getFont(Object element, int columnIndex) {
		return null;
	}

	@Override
	public Color getForeground(Object element, int columnIndex) {
        if (element instanceof TreeTarget) {
            return SWTResourceManager.getColor(SWT.COLOR_DARK_GREEN);
        }
        else  if (element instanceof TreeCurrent) {
            return SWTResourceManager.getColor(SWT.COLOR_DARK_RED);
        }
		return null;
	}

	@Override
	public Color getBackground(Object element, int columnIndex) {
        return null;
	}

}
