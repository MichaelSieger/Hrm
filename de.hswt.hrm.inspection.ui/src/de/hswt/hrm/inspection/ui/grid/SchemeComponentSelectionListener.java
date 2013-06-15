package de.hswt.hrm.inspection.ui.grid;

import de.hswt.hrm.scheme.model.SchemeComponent;

/**
 * A Listener that is called, if a component is selected.
 * 
 * @author Michael Sieger
 *
 */
public interface SchemeComponentSelectionListener {
	
	/**
	 * Called on component selection
	 * @param selected The selected component
	 */
	void selected(SchemeComponent selected);

}
