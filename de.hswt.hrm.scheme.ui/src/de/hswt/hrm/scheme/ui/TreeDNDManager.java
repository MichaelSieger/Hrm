package de.hswt.hrm.scheme.ui;

import java.awt.Toolkit;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DragSourceListener;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.DropTargetListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

/**
 * This class manages the drag and drop from the tree to the SchemeGrid
 * 
 * @author Michael Sieger
 * 
 */
public class TreeDNDManager {

    private final TreeViewer tree;
    private final SchemeGrid grid;
    private final DropTarget dt;
    private final DragSource src;

    private TreeData dragging;

    public TreeDNDManager(TreeViewer tree, SchemeGrid grid, DropTarget dt, DragSource src) {
        super();
        this.tree = tree;
        this.grid = grid;
        this.dt = dt;
        this.src = src;
        initDrag();
        initDrop();
    }

    private void initDrop() {
        dt.addDropListener(new DropTargetListener() {

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
        });
    }

    private void initDrag() {
        src.addDragListener(new DragSourceListener() {

            @Override
            public void dragStart(DragSourceEvent ev) { 
                ITreeSelection sel = (ITreeSelection) tree.getSelection();
                if(!sel.isEmpty()){
                    if (sel.size() != 1) {
                        throw new RuntimeException("Only one item is accepted for dragging");   
                    }
                    dragging = (TreeData) sel.getFirstElement();
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
        });
    }

}
