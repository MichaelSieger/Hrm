package de.hswt.hrm.scheme.ui;

import java.util.List;

import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.DropTargetListener;
import org.eclipse.swt.graphics.Point;

import de.hswt.hrm.scheme.model.RenderedComponent;

public class GridDropTargetListener implements DropTargetListener {

	private final SchemeGrid grid;
	private final List<RenderedComponent> comps;

	public GridDropTargetListener(SchemeGrid grid, List<RenderedComponent> comps) {
		super();
		this.grid = grid;
		this.comps = comps;
	}

	@Override
	public void dropAccept(DropTargetEvent arg0) {
	}

	@Override
	public void drop(DropTargetEvent ev) {
		System.out.println("Drop");
		DragData dragging = (DragData) ev.data;
		if (dragging != null) {
			SchemeGridItem item = dragging.toSchemeGridItem(comps);
			Point loc = grid.toDisplay(0, 0);
			final int x = ev.x - loc.x;
			final int y = ev.y - loc.y;
			try {
				grid.setImageAtPixel(comps.get(dragging.getId()),
						dragging.getDirection(), x, y);
			} catch (PlaceOccupiedException | IllegalArgumentException e) {
				try {
					if(dragging.hasPosition()){
						grid.setImage(item);
					}
				} catch (PlaceOccupiedException | IllegalArgumentException e1) {
					e1.printStackTrace();
				}
			}
		}
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

}
