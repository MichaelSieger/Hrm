package de.hswt.hrm.inspection.ui.performance.tree;

import org.eclipse.jface.viewers.LabelProvider;

import de.hswt.hrm.inspection.treeviewer.model.TreeActivity;
import de.hswt.hrm.inspection.treeviewer.model.TreeCurrent;
import de.hswt.hrm.inspection.treeviewer.model.TreeTarget;

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
