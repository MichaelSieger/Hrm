package de.hswt.hrm.scheme.ui.dnd;

import java.util.Collections;
import java.util.List;

import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DragSourceListener;

import com.google.common.base.Throwables;

import de.hswt.hrm.scheme.model.RenderedComponent;
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

    public GridDragListener(SchemeGrid grid) {
        this.grid = grid;
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
            dragging = new DragData(comps.indexOf(c), item.getX(), item.getY(), item.getDirection());
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
            try {
                grid.setImage(dragging.toSchemeGridItem(comps));
            }
            catch (PlaceOccupiedException e) {
                //Should not be possible
                Throwables.propagate(e);
            }
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
