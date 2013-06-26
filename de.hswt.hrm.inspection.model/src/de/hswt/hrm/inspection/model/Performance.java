package de.hswt.hrm.inspection.model;

public final class Performance {
	private final int id;
	private final int schemeComponentId;
	private final int targetId;
	private final int currentId;
	private final int activityId;
	private final int priorityId;
	
	public Performance(final int id, final int schemeComponentId, final int targetId,
			final int currentId, final int activityId, final int priorityId) {
		this.id = id;
		this.schemeComponentId = schemeComponentId;
		this.targetId = targetId;
		this.currentId = currentId;
		this.activityId = activityId;
		this.priorityId = priorityId;
	}
	
	public Performance(final int schemeComponentId, final int targetId,
			final int currentId, final int activityId, final int priorityId) {
		this(-1, schemeComponentId, targetId, currentId, activityId, priorityId);
	}
	
	public int getId() {
		return id;
	}

	public int getSchemeComponentId() {
		return schemeComponentId;
	}

	public int getTargetId() {
		return targetId;
	}

	public int getCurrentId() {
		return currentId;
	}

	public int getActivityId() {
		return activityId;
	}

	public int getPriorityId() {
		return priorityId;
	}
}
