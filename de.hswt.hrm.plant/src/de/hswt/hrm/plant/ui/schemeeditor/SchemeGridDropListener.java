package de.hswt.hrm.plant.ui.schemeeditor;

import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.DropTargetListener;

public class SchemeGridDropListener 
	implements DropTargetListener
{
	private final SchemeGrid grid;

	public SchemeGridDropListener(SchemeGrid grid) {
		super();
		this.grid = grid;
	}

	@Override
	public void dragEnter(DropTargetEvent ev) {
		ev.operations = DND.DROP_COPY;
		System.out.println("enter");
	}

	@Override
	public void dragLeave(DropTargetEvent ev) {
		ev.operations = DND.DROP_NONE;
	}

	@Override
	public void dragOperationChanged(DropTargetEvent arg0) {
		
	}

	@Override
	public void dragOver(DropTargetEvent arg0) {
		
	}

	@Override
	public void drop(DropTargetEvent arg0) {
		System.out.println("Drop");
	}

	@Override
	public void dropAccept(DropTargetEvent arg0) {
		
	}

}
