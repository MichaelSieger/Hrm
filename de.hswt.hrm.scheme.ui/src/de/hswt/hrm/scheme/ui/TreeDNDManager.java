package de.hswt.hrm.scheme.ui;

import java.util.List;

import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DragSourceListener;

import de.hswt.hrm.scheme.model.RenderedComponent;
import de.hswt.hrm.scheme.ui.tree.SchemeTreeItem;

/**
 * This class manages the drag and drop from the tree to the SchemeGrid
 * 
 * @author Michael Sieger
 * 
 */
public class TreeDNDManager implements DragSourceListener{

    private final TreeViewer tree;
    
    private final List<RenderedComponent> comps;

    public TreeDNDManager(TreeViewer tree, List<RenderedComponent> comps) {
        super();
        this.tree = tree;
        this.comps = comps;
    }

    @Override
    public void dragStart(DragSourceEvent ev) { 
    	
    }

    @Override
    public void dragSetData(DragSourceEvent ev) {
        ITreeSelection sel = (ITreeSelection) tree.getSelection();
        if(!sel.isEmpty()){
            if (sel.size() != 1) {
                throw new RuntimeException("Only one item is accepted for dragging");   
            }
            SchemeTreeItem item = ((SchemeTreeItem) sel.getFirstElement());
            ev.data = new DragData(comps.indexOf(
            		item.getDragItem().getRenderedComponent()), item.getDragItem().getDirection());
        }
    }

    @Override
    public void dragFinished(DragSourceEvent arg0) {
    }

}
