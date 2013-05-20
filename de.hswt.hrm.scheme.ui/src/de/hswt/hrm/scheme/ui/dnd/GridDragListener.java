package de.hswt.hrm.scheme.ui.dnd;

import java.util.List;

import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DragSourceListener;

import de.hswt.hrm.scheme.model.RenderedComponent;
import de.hswt.hrm.scheme.ui.SchemeGrid;
import de.hswt.hrm.scheme.ui.SchemeGridItem;

/**
 * This class manages the drag in the intern of the SchemeGrid.
 * 
 * @author Michael Sieger
 *
 */
public class GridDragListener implements DragSourceListener{
	
	private final SchemeGrid grid;
	
	private final List<RenderedComponent> comps;
	
	private int startX, startY;
	
	private DragData dragging;

	public GridDragListener(SchemeGrid grid, List<RenderedComponent> comps){
		this.grid = grid;
		this.comps = comps;
	}

	@Override
	public void dragStart(DragSourceEvent ev) {
		/*
		 * Saved here because x and y is not set in dragSetData
		 */
		startX = ev.x;
		startY = ev.y;
		SchemeGridItem item = grid.removeImagePixel(startX, startY);
		if(item != null){
			RenderedComponent c = item.getRenderedComponent();
	        dragging = new DragData(comps.indexOf(c), 
	                item.getX(), item.getY(), item.getDirection());
		}else{
			ev.doit = false;
		}
	}
	
	@Override
	public void dragSetData(DragSourceEvent ev) {
		ev.data = dragging;
	}
	
	@Override
	public void dragFinished(DragSourceEvent ev) {
		startX = -1;
		startY = -1;
		dragging = null;
		grid.clearColors();
	}

	public DragData getDraggingItem() {
		return dragging;
	}

 
	

}
