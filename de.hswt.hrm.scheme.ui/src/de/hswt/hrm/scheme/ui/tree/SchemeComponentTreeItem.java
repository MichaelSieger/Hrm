package de.hswt.hrm.scheme.ui.tree;

import java.util.List;

import org.eclipse.swt.graphics.Image;

import de.hswt.hrm.scheme.model.Direction;
import de.hswt.hrm.scheme.model.RenderedComponent;
import de.hswt.hrm.scheme.model.Scheme;
import de.hswt.hrm.scheme.model.SchemeComponent;
import de.hswt.hrm.scheme.ui.dnd.DragData;

/**
 * This class represents a direction in the SchemeTreeViewer
 * 
 * @author Michael Sieger
 *
 */
public class SchemeComponentTreeItem extends SchemeTreeItem{
	
	private final RenderedComponent comp;
	private final Direction dir;
	
	public SchemeComponentTreeItem(ComponentTreeItem parent, RenderedComponent comp, Direction dir){
		super(parent);
		this.comp = comp;
		this.dir = dir;
	}

	@Override
	public SchemeTreeItem[] getChildren() {
		return null;
	}

	@Override
	public boolean hasChildren() {
		return false;
	}

	@Override
	public String getText() {
		return null;
	}

	@Override
	public Image getImage() {
		return comp.getByDirection(dir).getThumbnail();
	}

	@Override
	public DragData getDragItem(Scheme scheme, List<RenderedComponent> renderedComponents) {
		//Creates a new SchemeComponent
		return new DragData(renderedComponents.indexOf(comp), new SchemeComponent(scheme, 0, 0, dir, comp.getComponent()));
	}
	
	

}
