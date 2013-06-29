package de.hswt.hrm.inspection.ui.performance.tree;

import org.eclipse.jface.viewers.LabelProvider;

import de.hswt.hrm.inspection.model.tree.TreeActivity;
import de.hswt.hrm.inspection.model.tree.TreeCurrent;
import de.hswt.hrm.inspection.model.tree.TreeTarget;

public class PerformanceTreeLabelProvider extends LabelProvider {

    @Override
    public String getText(Object element) {
        if (element instanceof TreeTarget) {
            return ((TreeTarget)element).getName();
        }
        else  if (element instanceof TreeCurrent) {
            return ((TreeCurrent)element).getName();
        }
        else  if (element instanceof TreeActivity) {
            return ((TreeActivity)element).getName();
        }
        return null;
       
    }

}
