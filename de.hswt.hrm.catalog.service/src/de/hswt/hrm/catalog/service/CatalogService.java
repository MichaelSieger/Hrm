package de.hswt.hrm.catalog.service;

import java.util.Collection;
import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Creatable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Iterables;
import static com.google.common.base.Preconditions.checkNotNull;

import de.hswt.hrm.catalog.dao.core.IActivityDao;
import de.hswt.hrm.catalog.dao.core.ICurrentDao;
import de.hswt.hrm.catalog.dao.core.ITargetDao;
import de.hswt.hrm.catalog.model.Activity;
import de.hswt.hrm.catalog.model.Current;
import de.hswt.hrm.catalog.model.ICatalogItem;
import de.hswt.hrm.catalog.model.Target;
import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.database.exception.ElementNotFoundException;
import de.hswt.hrm.common.database.exception.SaveException;

@Creatable
public class CatalogService {
	private final static Logger LOG = LoggerFactory.getLogger(CatalogService.class);

	private final IActivityDao activityDao;
	private final ICurrentDao currentDao;
	private final ITargetDao targetDao;
	
	@Inject
	public CatalogService(IActivityDao activityDao, ICurrentDao currentDao,
			ITargetDao targetDao) {
		checkNotNull("Activity DAO must be injected properly.", activityDao);
		checkNotNull("Current DAO must be injected properly.", currentDao);
		checkNotNull("Target DAO must be injected properly.", targetDao);
		
		this.activityDao = activityDao;
		this.currentDao = currentDao;
		this.targetDao = targetDao;
	}
	
	Iterable<ICatalogItem> findAllCatalogItem() throws DatabaseException {
		return Iterables.concat(activityDao.findAll(), currentDao.findAll(), targetDao.findAll());
	}
	
	Collection<Activity> findAllActivity() throws DatabaseException {
		return activityDao.findAll();
	}
	
	Collection<Current> findAllCurrent() throws DatabaseException {
		return currentDao.findAll();
	}
	
	Collection<Target> findAllTarget() throws DatabaseException {
		return targetDao.findAll();
	}
	
	Activity insertActivity(Activity activity) throws SaveException {
		return activityDao.insert(activity);
	}
	
	Current insertCurrent(Current current) throws SaveException {
		return currentDao.insert(current);
	}
	
	Target insertTarget(Target target) throws SaveException {
		return targetDao.insert(target);
	}
	
	void updateActivity(Activity activity) throws ElementNotFoundException, SaveException {
		activityDao.update(activity);
	}
	
	void updateCurrent(Current current) throws ElementNotFoundException, SaveException {
		currentDao.update(current);
	}
	
	void updateTarget(Target target) throws ElementNotFoundException, SaveException {
		targetDao.update(target);
	}
	
	
}
