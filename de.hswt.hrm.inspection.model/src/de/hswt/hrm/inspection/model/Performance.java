package de.hswt.hrm.inspection.model;

import de.hswt.hrm.catalog.model.Activity;
import de.hswt.hrm.catalog.model.Current;
import de.hswt.hrm.catalog.model.Target;
import de.hswt.hrm.misc.priority.model.Priority;
import de.hswt.hrm.scheme.model.SchemeComponent;

public final class Performance {
	private final int id;
	private final SchemeComponent schemeComponent;
	private final Target target;
	private final Current current;
	private final Activity activity;
	private final Priority priority;
	
	public Performance(final int id, final SchemeComponent schemeComponent, final Target target,
			final Current current, final Activity activity, final Priority priority) {
		this.id = id;
		this.schemeComponent = schemeComponent;
		this.target = target;
		this.current = current;
		this.activity = activity;
		this.priority = priority;
	}
	
	public Performance(final SchemeComponent schemeComponent, final Target target,
			final Current current, final Activity activity, final Priority priority) {
		this(-1, schemeComponent, target, current, activity, priority);
	}
	
	public int getId() {
		return id;
	}

	public SchemeComponent getSchemeComponent() {
		return schemeComponent;
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

	public Priority getPriority() {
		return priority;
	}
}
