package de.hswt.hrm.scheme.ui;

import java.util.List;

import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DragSourceListener;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.DropTargetListener;
import org.eclipse.swt.graphics.Point;

import de.hswt.hrm.scheme.model.RenderedComponent;

/**
 * This class manages the drag and drop in the intern of the SchemeGrid.
 * 
 * @author Michael Sieger
 *
 */
public class GridDNDManager implements DragSourceListener{
	
	private final SchemeGrid grid;
	
	private List<RenderedComponent> comps;
	
	private DragData data;
	
	public GridDNDManager(SchemeGrid grid, List<RenderedComponent> comps){
		this.grid = grid;
		this.comps = comps;
	}

	@Override
	public void dragStart(DragSourceEvent ev) {
	    SchemeGridItem item = grid.removeImagePixel(ev.x, ev.y);
	    if(item != null){
		    RenderedComponent c = item.getRenderedComponent();
	        data = new DragData(comps.indexOf(c), 
	                item.getX(), item.getY(), item.getDirection());
	    }else{
	    	ev.doit = false;
	    }
	}
	
	@Override
	public void dragSetData(DragSourceEvent ev) {
		ev.data = data;
	}
	
	@Override
	public void dragFinished(DragSourceEvent ev) {

	}

 
	

}
