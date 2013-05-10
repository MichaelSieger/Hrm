package de.hswt.hrm.scheme.ui.tree;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;

import de.hswt.hrm.scheme.model.Direction;
import de.hswt.hrm.scheme.model.RenderedComponent;

public class ComponentTreeItem implements SchemeTreeItem{
	
	private final RenderedComponent c;
	
	public ComponentTreeItem(RenderedComponent c){
		this.c = c;
	}

	@Override
	public SchemeTreeItem[] getChildren() {
		List<SchemeTreeItem> list = new ArrayList<>();
		addDirection(list, Direction.downUp);
		addDirection(list, Direction.leftRight);
		addDirection(list, Direction.rightLeft);
		addDirection(list, Direction.upDown);
		SchemeTreeItem[] result = new SchemeTreeItem[list.size()];
		list.toArray(result);
		return result;
	}
	
	private void addDirection(List<SchemeTreeItem> list, Direction d){
		if(c.getByDirection(d) != null){
			list.add(new RenderedComponentTreeItem(c, d));
		}
	}

	@Override
	public boolean hasChildren() {
		return true;
	}

	@Override
	public SchemeTreeItem getParent() {
		return null;
	}

	@Override
	public String getText() {
		return c.getComponent().getName();
	}

	@Override
	public Image getImage() {
		return null;
	}

}
