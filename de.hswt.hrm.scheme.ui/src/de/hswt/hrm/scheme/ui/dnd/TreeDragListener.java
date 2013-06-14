package de.hswt.hrm.scheme.ui.dnd;

import java.util.List;

import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DragSourceListener;

import de.hswt.hrm.scheme.model.RenderedComponent;
import de.hswt.hrm.scheme.ui.DirectedRenderedComponent;
import de.hswt.hrm.scheme.ui.SchemeGrid;
import de.hswt.hrm.scheme.ui.tree.SchemeTreeItem;

/**
 * This class manages the drag from the tree to the SchemeGrid
 * 
 * @author Michael Sieger
 * 
 */
public class TreeDragListener implements DragSourceListener{

    private final TreeViewer tree;
    private List<RenderedComponent> components;
    private final SchemeGrid grid;
    
    private DragData dragging;

    public TreeDragListener(TreeViewer tree, List<RenderedComponent> components, SchemeGrid grid) {
        super();
        this.tree = tree;
        this.components = components;
        this.grid = grid;
    }

    @Override
    public void dragStart(DragSourceEvent ev) { 
        ITreeSelection sel = (ITreeSelection) tree.getSelection();
        if(!sel.isEmpty()){
            if (sel.size() != 1) {
                throw new RuntimeException("Only one item is accepted for dragging");   
            }
            SchemeTreeItem item = ((SchemeTreeItem) sel.getFirstElement());
            dragging = new DragData(
            		getItemIndex(item.getDragItem()), item.getDragItem().getDirection());
            ev.image = null;
        }else{
        	ev.doit = false;
        }
    }
    
    private int getItemIndex(DirectedRenderedComponent dc){
    	if (components == null) {
    		return -1;
    	}
    	for(int i = 0; i < components.size(); i++){
    		if(components.get(i).getComponent().equals(dc.getComponent())){
    			return i;
    		}
    	}
    	throw new RuntimeException("Internal Error");
    }

    @Override
    public void dragSetData(DragSourceEvent ev) {
    	ev.data = dragging;
    }

    @Override
    public void dragFinished(DragSourceEvent arg0) {
    	dragging = null;
    	grid.clearColors();
    }
    
    public DragData getDraggingItem(){
    	return dragging;
    }

	public void setComponents(List<RenderedComponent> comps) {
		this.components = comps;
	}
}
