package de.hswt.hrm.scheme.ui;

import java.awt.Toolkit;

import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DragSourceListener;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.DropTargetListener;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import de.hswt.hrm.scheme.model.RenderedGridImage;

/**
 * This class manages the drag and drop from the tree to the SchemeGrid
 * 
 * @author Michael Sieger
 * 
 */
public class TreeDNDManager {

    private static final int DRAG_OPS = DND.DROP_COPY, DROP_OPS = DND.DROP_COPY;

    private final Tree tree;
    private final SchemeGrid grid;

    private RenderedGridImage dragging;

    public TreeDNDManager(Tree tree, SchemeGrid grid) {
        super();
        this.tree = tree;
        this.grid = grid;
        initDrag();
        initDrop();
    }

    private void initDrop() {
        DropTarget dt = new DropTarget(grid, DROP_OPS);
        dt.setTransfer(new Transfer[] { TextTransfer.getInstance() });
        dt.addDropListener(new DropTargetListener() {

            @Override
            public void dropAccept(DropTargetEvent arg0) {
            }

            @Override
            public void drop(DropTargetEvent ev) {
                if (dragging != null) {
                    Point loc = grid.toDisplay(0, 0);
                    try {
                    	grid.setImageAtPixel(dragging, ev.x - loc.x, ev.y - loc.y);
                    }
                    catch (PlaceOccupiedException | IllegalArgumentException e) {
                        e.printStackTrace();
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
                ev.detail = DND.DROP_NONE;
            }

            @Override
            public void dragEnter(DropTargetEvent ev) {
                ev.detail = DND.DROP_COPY;
            }
        });
    }

    private void initDrag() {
        DragSource src = new DragSource(tree, DRAG_OPS);
        src.setTransfer(new Transfer[] { TextTransfer.getInstance() });
        src.addDragListener(new DragSourceListener() {

            @Override
            public void dragStart(DragSourceEvent ev) {
                TreeItem[] items = tree.getSelection();
                if (items.length != 1) {
                    throw new RuntimeException("Only one item is accepted for dragging");
                }
                dragging = (RenderedGridImage) items[0].getData();
            }

            @Override
            public void dragSetData(DragSourceEvent ev) {
                ev.data = "bla";
            }

            @Override
            public void dragFinished(DragSourceEvent arg0) {
                dragging = null;
            }
        });
    }

}