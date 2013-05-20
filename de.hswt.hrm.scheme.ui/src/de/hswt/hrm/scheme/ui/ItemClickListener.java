package de.hswt.hrm.scheme.ui;

import org.eclipse.swt.events.MouseEvent;

/**
 * Listens for a item click.
 * 
 * @author Michael Sieger
 *
 */
public interface ItemClickListener {
	
	void itemClicked(MouseEvent e, SchemeGridItem item);

}
