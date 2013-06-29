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
}
