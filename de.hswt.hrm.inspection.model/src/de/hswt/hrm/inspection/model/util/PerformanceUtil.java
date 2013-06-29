package de.hswt.hrm.inspection.model.util;

import java.util.ArrayList;
import java.util.List;

import de.hswt.hrm.catalog.model.Activity;
import de.hswt.hrm.catalog.model.Catalog;
import de.hswt.hrm.catalog.model.Current;
import de.hswt.hrm.catalog.model.Target;
import de.hswt.hrm.catalog.model.tree.TreeCatalog;
import de.hswt.hrm.catalog.model.tree.TreeCurrent;
import de.hswt.hrm.catalog.model.tree.TreeTarget;

/**
 * Util class to ease usage of performance / catalog classes.
 * @author ben
 *
 */
public final class PerformanceUtil {
	private PerformanceUtil() { }
	
	public static PerformanceTriplet createTriplet(final Target target, 
			final Current current, final Activity activity) {
		
		return new PerformanceTriplet(target, current, activity);
	}
	
	public static TreeCatalog createTreeCatalogTriplet(final Catalog catalog,
			final Target target, final Current current, final Activity activity) {
		
		List<TreeTarget> targets = new ArrayList<TreeTarget>(1);
		targets.add(createTreeTriplet(target, current, activity));
		
		return new TreeCatalog(catalog, targets);
	}
	
	public static TreeTarget createTreeTriplet(final Target target,
			final Current current, final Activity activity) {
		
		List<Activity> activities = new ArrayList<>(1);
		activities.add(activity);
		
		List<TreeCurrent> currents = new ArrayList<>(1);
		currents.add(new TreeCurrent(current, activities));
		
		return new TreeTarget(target, currents);
	}
}
