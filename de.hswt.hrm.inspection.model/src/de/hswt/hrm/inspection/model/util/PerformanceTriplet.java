package de.hswt.hrm.inspection.model.util;

import de.hswt.hrm.catalog.model.Activity;
import de.hswt.hrm.catalog.model.Current;
import de.hswt.hrm.catalog.model.Target;

/**
 * Represents a triplet with a target state, current state and activity. 
 */
public class PerformanceTriplet {
	private final Target target;
	private final Current current;
	private final Activity activity;
	
	public PerformanceTriplet(final Target target, final Current current, 
			final Activity activity) {
		
		this.target = target;
		this.current = current;
		this.activity = activity;
	}

	public Target getTarget() {
		return target;
	}

	public Current getCurrent() {
		return current;
	}

	public Activity getActivity() {
		return activity;
	}
}
