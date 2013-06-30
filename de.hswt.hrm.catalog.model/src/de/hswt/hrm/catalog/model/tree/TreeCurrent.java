package de.hswt.hrm.catalog.model.tree;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import de.hswt.hrm.catalog.model.Activity;
import de.hswt.hrm.catalog.model.Current;

public class TreeCurrent {
	private final Current current;
	private List<Activity> activities;
	
    public TreeCurrent(final Current current) {
        this(current, new LinkedList<Activity>());
    }
    
    public TreeCurrent(final Current current, List<Activity> activities) {
    	this.current = current;
    	this.activities = activities;
    }

    public String getName() {
        return current.getName();
    }

    /**
     * @return Unmodifiable collection of matching currents.
     */
    public List<Activity> getActivities() {
    	return Collections.unmodifiableList(activities);
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((activities == null) ? 0 : activities.hashCode());
		result = prime * result + ((current == null) ? 0 : current.hashCode());
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
		TreeCurrent other = (TreeCurrent) obj;
		if (activities == null) {
			if (other.activities != null)
				return false;
		} else if (!activities.equals(other.activities))
			return false;
		if (current == null) {
			if (other.current != null)
				return false;
		} else if (!current.equals(other.current))
			return false;
		return true;
	}
}
