package de.hswt.hrm.scheme.ui.dnd;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.DropTargetListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;

import com.google.common.base.Preconditions;

import de.hswt.hrm.component.service.ComponentService;
import de.hswt.hrm.scheme.model.RenderedComponent;
import de.hswt.hrm.scheme.model.Scheme;
import de.hswt.hrm.scheme.service.SchemeService;
import de.hswt.hrm.scheme.ui.PlaceOccupiedException;
import de.hswt.hrm.scheme.ui.SchemeGrid;
import de.hswt.hrm.scheme.ui.SchemeGridItem;
import de.hswt.hrm.scheme.ui.part.SchemeComposite;

/**
 * Handles the drop in the SchemeGrid
 * 
 * @author Michael Sieger
 *
 */
public class GridDropTargetListener implements DropTargetListener { 

	private final SchemeGrid grid;
	private List<RenderedComponent> comps = Collections.emptyList();
	private final SchemeComposite composite;

	private Scheme scheme;
	
	private ComponentService componentService;

	public GridDropTargetListener(SchemeGrid grid, SchemeComposite schemeCompositeNew, ComponentService componentService) {
		super();
		Preconditions.checkNotNull(grid);
		Preconditions.checkNotNull(schemeCompositeNew);
		Preconditions.checkNotNull(componentService);
		this.grid = grid;
		this.composite = schemeCompositeNew;
		this.componentService = componentService;
	}

	@Override
	public void dropAccept(DropTargetEvent arg0) {
	}

	@Override
	public void drop(DropTargetEvent ev) {
		DragData dragging = (DragData) ev.data;
		if (dragging != null) {
			SchemeGridItem item = dragging.toSchemeGridItem(comps, scheme, componentService);
			Point loc = grid.toDisplay(0, 0);
			final int x = ev.x - loc.x;
			final int y = ev.y - loc.y;
			try {
				grid.setImageAtPixel(item, x, y);
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
		DragData data = composite.getDraggingItem();
		if(data != null){
			//chemeGridItem item = data.toSchemeGridItem(comps, schemeService, componentService);
			final Point org = grid.toDisplay(0, 0);
			final int x = ev.x - org.x;
			final int y = ev.y - org.y;
			grid.clearColors();
			grid.setColorPixel(getShadowColor(), x, y, data.getWidth(), data.getHeight());
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

	public void setComponents(List<RenderedComponent> comps) {
		this.comps = comps;
	}
	
	public void setScheme(Scheme scheme){
		this.scheme = scheme;
	}
	
}
