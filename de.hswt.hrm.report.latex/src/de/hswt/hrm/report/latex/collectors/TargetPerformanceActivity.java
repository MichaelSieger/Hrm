package de.hswt.hrm.report.latex.collectors;

import de.hswt.hrm.catalog.model.Activity;
import de.hswt.hrm.catalog.model.Current;
import de.hswt.hrm.catalog.model.Target;

public class TargetPerformanceActivity {

    private Target target;
    private Current current;
    private Activity activity;

    public Target getTarget() {
        return target;
    }

    public void setTarget(Target target) {
        this.target = target;
    }

    public Current getCurrent() {
        return current;
    }

    public void setCurrent(Current current) {
        this.current = current;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

}
