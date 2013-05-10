package de.hswt.hrm.scheme.ui;

import java.awt.Toolkit;

import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DragSourceListener;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.DropTargetListener;
import org.eclipse.swt.graphics.Point;

import de.hswt.hrm.scheme.ui.tree.SchemeTreeItem;

/**
 * This class manages the drag and drop from the tree to the SchemeGrid
 * 
 * @author Michael Sieger
 * 
 */
public class TreeDNDManager implements DragSourceListener, DropTargetListener{

    private final TreeViewer tree;
    private final SchemeGrid grid;

    private DirectedRenderedComponent dragging;

    public TreeDNDManager(TreeViewer tree, SchemeGrid grid) {
        super();
        this.tree = tree;
        this.grid = grid;
    }

    @Override
    public void dragStart(DragSourceEvent ev) { 
        ITreeSelection sel = (ITreeSelection) tree.getSelection();
        if(!sel.isEmpty()){
            if (sel.size() != 1) {
                throw new RuntimeException("Only one item is accepted for dragging");   
            }
            dragging = ((SchemeTreeItem) sel.getFirstElement())
            		.getDragItem();
        }
    }

    @Override
    public void dragSetData(DragSourceEvent ev) {
    	if(dragging != null){
    		ev.data = " ";
    	}
    }

    @Override
    public void dragFinished(DragSourceEvent arg0) {
    	dragging = null;
    }

    @Override
    public void dropAccept(DropTargetEvent arg0) {
    }

    @Override
    public void drop(DropTargetEvent ev) {
        if (dragging != null) {
            Point loc = grid.toDisplay(0, 0);
            try {
            	grid.setImageAtPixel(dragging.getRenderedComponent(), dragging.getDirection(), 
            							ev.x - loc.x, ev.y - loc.y);
            }
            catch (PlaceOccupiedException | IllegalArgumentException e) {
                Toolkit.getDefaultToolkit().beep();
            }
        }
        /*
         * Else case means, that an item from an different gui was dropped which is not
         * allowed
         */
    }

    @Override
    public void dragOver(DropTargetEvent arg0) {
    }

    @Override
    public void dragOperationChanged(DropTargetEvent arg0) {
    }

    @Override
    public void dragLeave(DropTargetEvent ev) {
    	if(dragging != null) {
    		ev.detail = DND.DROP_NONE;
    	}
    }

    @Override
    public void dragEnter(DropTargetEvent ev) {
    	if(dragging != null){
    		ev.detail = DND.DROP_COPY;
    	}
    }

}
