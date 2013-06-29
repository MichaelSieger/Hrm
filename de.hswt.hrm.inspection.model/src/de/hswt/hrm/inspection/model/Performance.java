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
    private final Inspection inspection;

    public Performance(final int id, final SchemeComponent schemeComponent, final Target target,
            final Current current, final Activity activity, final Priority priority,
            final Inspection inspection) {
        this.id = id;
        this.schemeComponent = schemeComponent;
        this.target = target;
        this.current = current;
        this.activity = activity;
        this.priority = priority;
        this.inspection = inspection;
    }

    public Performance(final SchemeComponent schemeComponent, final Target target,
            final Current current, final Activity activity, final Priority priority,
            final Inspection inspection) {
        this(-1, schemeComponent, target, current, activity, priority, inspection);
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

    public Inspection getInspection() {
        return inspection;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((activity == null) ? 0 : activity.hashCode());
        result = prime * result + ((current == null) ? 0 : current.hashCode());
        result = prime * result + id;
        result = prime * result + ((inspection == null) ? 0 : inspection.hashCode());
        result = prime * result + ((priority == null) ? 0 : priority.hashCode());
        result = prime * result + ((schemeComponent == null) ? 0 : schemeComponent.hashCode());
        result = prime * result + ((target == null) ? 0 : target.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Performance other = (Performance) obj;
        if (activity == null) {
            if (other.activity != null) {
                return false;
            }
        }
        else if (!activity.equals(other.activity)) {
            return false;
        }
        if (current == null) {
            if (other.current != null) {
                return false;
            }
        }
        else if (!current.equals(other.current)) {
            return false;
        }
        if (id != other.id) {
            return false;
        }
        if (inspection == null) {
            if (other.inspection != null) {
                return false;
            }
        }
        else if (!inspection.equals(other.inspection)) {
            return false;
        }
        if (priority == null) {
            if (other.priority != null) {
                return false;
            }
        }
        else if (!priority.equals(other.priority)) {
            return false;
        }
        if (schemeComponent == null) {
            if (other.schemeComponent != null) {
                return false;
            }
        }
        else if (!schemeComponent.equals(other.schemeComponent)) {
            return false;
        }
        if (target == null) {
            if (other.target != null) {
                return false;
            }
        }
        else if (!target.equals(other.target)) {
            return false;
        }
        return true;
    }

}
