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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((currents == null) ? 0 : currents.hashCode());
		result = prime * result + ((target == null) ? 0 : target.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TreeTarget other = (TreeTarget) obj;
		if (currents == null) {
			if (other.currents != null)
				return false;
		} else if (!currents.equals(other.currents))
			return false;
		if (target == null) {
			if (other.target != null)
				return false;
		} else if (!target.equals(other.target))
			return false;
		return true;
	}
}
