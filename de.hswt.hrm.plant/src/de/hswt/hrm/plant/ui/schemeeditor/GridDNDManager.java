package de.hswt.hrm.plant.ui.schemeeditor;

import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DragSourceListener;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.graphics.Point;

import de.hswt.hrm.plant.model.RenderedGridImage;

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
	
	public GridDNDManager(SchemeGrid grid){
		this.grid = grid;
		initDrag();
	}
	
	private void initDrag(){
		DragSource source = new DragSource(grid, OPS);
		source.setTransfer(new Transfer[]{TextTransfer.getInstance()});
		source.addDragListener(new DragSourceListener() {
			
			@Override
			public void dragStart(DragSourceEvent ev) {
                Point loc = grid.toDisplay(0, 0);
                final int x = ev.x - loc.x;
                final int y = ev.y - loc.y;
                dragging = grid.removeImagePixel(x, y);
			}
			
			@Override
			public void dragSetData(DragSourceEvent ev) {
				ev.data = "bla";
			}
			
			@Override
			public void dragFinished(DragSourceEvent ev) {
				if(dragging != null){
					Point loc = grid.toDisplay(0, 0);
	                final int x = ev.x - loc.x;
	                final int y = ev.y - loc.y;
					try {
						grid.setImageAtPixel(dragging, x, y);
					} catch (PlaceOccupiedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					dragging = null;
				}
			}
		});
	}
	

}
