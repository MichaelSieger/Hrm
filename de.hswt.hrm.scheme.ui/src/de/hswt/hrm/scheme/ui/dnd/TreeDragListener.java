package de.hswt.hrm.scheme.ui.dnd;

import java.util.List;

import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DragSourceListener;

import de.hswt.hrm.scheme.model.RenderedComponent;
import de.hswt.hrm.scheme.ui.tree.SchemeTreeItem;

/**
 * This class manages the drag from the tree to the SchemeGrid
 * 
 * @author Michael Sieger
 * 
 */
public class TreeDragListener implements DragSourceListener{

    private final TreeViewer tree;
    
    private final List<RenderedComponent> comps;
    
    private DragData dragging;

    public TreeDragListener(TreeViewer tree, List<RenderedComponent> comps) {
        super();
        this.tree = tree;
        this.comps = comps;
    }

    @Override
    public void dragStart(DragSourceEvent ev) { 
        ITreeSelection sel = (ITreeSelection) tree.getSelection();
        if(!sel.isEmpty()){
            if (sel.size() != 1) {
                throw new RuntimeException("Only one item is accepted for dragging");   
            }
            SchemeTreeItem item = ((SchemeTreeItem) sel.getFirstElement());
            dragging = new DragData(comps.indexOf(
            		item.getDragItem().getRenderedComponent()), item.getDragItem().getDirection());
            ev.image = null;
        }else{
        	ev.doit = false;
        }
    }

    @Override
    public void dragSetData(DragSourceEvent ev) {
    	ev.data = dragging;
    }

    @Override
    public void dragFinished(DragSourceEvent arg0) {
    	
    }
    
    public DragData getDraggingItem(){
    	return dragging;
    }

}
