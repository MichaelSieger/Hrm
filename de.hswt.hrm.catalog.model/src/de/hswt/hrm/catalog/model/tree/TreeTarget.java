package de.hswt.hrm.catalog.model.tree;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import de.hswt.hrm.catalog.model.Target;

/**
 * Wrapper for the target class that provides a tree like interface.
 */
public class TreeTarget {
	private final Target target;
	private List<TreeCurrent> currents;
	
    public TreeTarget(final Target target) {
        this(target, new LinkedList<TreeCurrent>());
    }
    
    public TreeTarget(final Target target, List<TreeCurrent> currents) {
    	this.target = target;
    	this.currents = currents;
    }

    public String getName() {
        return target.getName();
    }

    /**
     * @return Unmodifiable collection of matching currents.
     */
    public List<TreeCurrent> getCurrents() {
    	return Collections.unmodifiableList(currents);
    }

    public void setCurrents(List<TreeCurrent> currents) {
        this.currents = currents;
    }
}
