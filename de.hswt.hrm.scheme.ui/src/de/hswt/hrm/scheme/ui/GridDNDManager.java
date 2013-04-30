package de.hswt.hrm.scheme.ui;

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

import de.hswt.hrm.scheme.model.RenderedGridImage;

/**
 * This class manages the drag and drop in the intern of the SchemeGrid
 * 
 * @author Michael Sieger
 *
 */
public class GridDNDManager {
	
	private static final int OPS = DND.DROP_MOVE;
	
	private final SchemeGrid grid;
	private RenderedGridImage dragging;
//	private startX, startY;
	
	public GridDNDManager(SchemeGrid grid){
		this.grid = grid;
		initDrag();
		initDrop();
	}
	
	private void initDrop(){
	    DropTarget target = new DropTarget(grid, OPS);
	    target.setTransfer(new Transfer[]{TextTransfer.getInstance()});
	    target.addDropListener(new DropTargetListener() {
            
            @Override
            public void dropAccept(DropTargetEvent arg0) {}
            
            @Override
            public void drop(DropTargetEvent ev) {
                if(dragging != null){
                    Point loc = grid.toDisplay(0, 0);
                    final int x = ev.x - loc.x;
                    final int y = ev.y - loc.y;
                    try {
                        grid.setImageAtPixel(dragging, x, y);
                    } catch (PlaceOccupiedException e) {
//                        Toolbox.getDefaultToolbox().beep();
//                        grid.setImageAtPixel(dragging, startX, startY);
                    }
                    dragging = null;
                }
            }
            
            @Override
            public void dragOver(DropTargetEvent arg0) {}
            
            @Override
            public void dragOperationChanged(DropTargetEvent arg0) {}
            
            @Override
            public void dragLeave(DropTargetEvent ev) {
                ev.detail = DND.DROP_NONE;
            }
            
            @Override
            public void dragEnter(DropTargetEvent ev) {
                ev.detail = DND.DROP_MOVE;
            }
        });
	}
	
	private void initDrag(){
		DragSource source = new DragSource(grid, OPS);
		source.setTransfer(new Transfer[]{TextTransfer.getInstance()});
		source.addDragListener(new DragSourceListener() {
			
			@Override
			public void dragStart(DragSourceEvent ev) {
                Point loc = grid.toDisplay(0, 0);
//                startX = ev.x - loc.x;
//                startY = ev.y - loc.y;
               // dragging = grid.removeImagePixel(startX, startY);
			}
			
			@Override
			public void dragSetData(DragSourceEvent ev) {
				ev.data = dragging.toString();
			}
			
			@Override
			public void dragFinished(DragSourceEvent ev) {

			}
		});
	}
	

}
