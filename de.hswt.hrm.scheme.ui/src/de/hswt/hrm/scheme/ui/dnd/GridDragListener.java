package de.hswt.hrm.scheme.ui.dnd;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DragSourceListener;

import com.google.common.base.Throwables;

import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.database.exception.ElementNotFoundException;
import de.hswt.hrm.component.service.ComponentService;
import de.hswt.hrm.scheme.model.RenderedComponent;
import de.hswt.hrm.scheme.service.SchemeService;
import de.hswt.hrm.scheme.ui.PlaceOccupiedException;
import de.hswt.hrm.scheme.ui.SchemeGrid;
import de.hswt.hrm.scheme.ui.SchemeGridItem;

/**
 * This class manages the drag in the intern of the SchemeGrid.
 * 
 * @author Michael Sieger
 * 
 */
public class GridDragListener implements DragSourceListener {

    private final SchemeGrid grid;

    private List<RenderedComponent> comps = Collections.emptyList();

    private int startX, startY;

    private DragData dragging;
    
    private final SchemeService schemeService;

    private final ComponentService componentService;
    
    public GridDragListener(SchemeGrid grid, SchemeService schemeService, ComponentService componentService) {
        this.grid = grid;
        this.schemeService = schemeService;
        this.componentService = componentService;
    }

    @Override
    public void dragStart(DragSourceEvent ev) {
        /*
         * Saved here because x and y is not set in dragSetData
         */
        startX = ev.x;
        startY = ev.y;
        SchemeGridItem item = grid.removeImagePixel(startX, startY);
        if (item != null) {
            RenderedComponent c = item.getRenderedComponent();
            dragging = new DragData(comps.indexOf(c), item.asSchemeComponent());
        }
        else {
            ev.doit = false;
        }
    }

    @Override
    public void dragSetData(DragSourceEvent ev) {
        ev.data = dragging;
    }

    @Override
    public void dragFinished(DragSourceEvent ev) {
        if (!ev.doit) {
        	grid.setImage(dragging.toSchemeGridItem(comps, schemeService, componentService));
        }
        startX = -1;
        startY = -1;
        dragging = null;
        grid.clearColors();
    }

    public DragData getDraggingItem() {
        return dragging;
    }

	public void setComponents(List<RenderedComponent> comps) {
		this.comps = comps;
	}

    
}
