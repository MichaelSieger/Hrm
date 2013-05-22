package de.hswt.hrm.scheme.ui.dnd;

import java.util.List;

import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.DropTargetListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;

import com.google.common.base.Optional;

import de.hswt.hrm.component.model.Category;
import de.hswt.hrm.scheme.model.RenderedComponent;
import de.hswt.hrm.scheme.ui.PlaceOccupiedException;
import de.hswt.hrm.scheme.ui.SchemeGrid;
import de.hswt.hrm.scheme.ui.SchemeGridItem;
import de.hswt.hrm.scheme.ui.part.SchemePart;

/**
 * Handles the drop in the SchemeGrid
 * 
 * @author Michael Sieger
 *
 */
public class GridDropTargetListener implements DropTargetListener { 

	private final SchemeGrid grid;
	private final List<RenderedComponent> comps;
	private final SchemePart part;

	public GridDropTargetListener(SchemeGrid grid, List<RenderedComponent> comps, SchemePart part) {
		super();
		this.grid = grid;
		this.comps = comps;
		this.part = part;
	}

	@Override
	public void dropAccept(DropTargetEvent arg0) {
	}

	@Override
	public void drop(DropTargetEvent ev) {
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
	public void dragOver(DropTargetEvent ev) {
		DragData data = part.getDraggingItem();
		if(data != null){
			SchemeGridItem item = data.toSchemeGridItem(comps);
			Optional<Category> c = item.getRenderedComponent().getComponent().getCategory();
			if(!c.isPresent()){
				throw new IllegalArgumentException("The Category must be present here");
			}
			final Point org = grid.toDisplay(0, 0);
			final int x = ev.x - org.x;
			final int y = ev.y - org.y;
			grid.clearColors();
			grid.setColor(getShadowColor(), x, y, c.get().getWidth(), c.get().getHeight());
		}
	}
	
	private Color getShadowColor(){
		return new Color(grid.getDisplay(), 255, 165, 0);
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
