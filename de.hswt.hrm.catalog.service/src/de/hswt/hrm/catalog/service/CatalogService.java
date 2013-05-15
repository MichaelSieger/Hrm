package de.hswt.hrm.catalog.service;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

import javax.activity.InvalidActivityException;
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
import de.hswt.hrm.common.exception.NotImplementedException;

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
	
	Collection<Activity> findAllActivity() {
		throw new NotImplementedException();
	}
	
	Collection<Current> findAllCurrent() {
		throw new NotImplementedException();
	}
	
	Collection<Target> findAllTarget() {
		throw new NotImplementedException();
	}
	
	Activity insertActivity(Activity activity) {
		throw new NotImplementedException();
	}
	
	Current insertCurrent(Current current) {
		throw new NotImplementedException();
	}
	
	Target insertTarget(Target target) {
		throw new NotImplementedException();
	}
	
	void updateActivity(Activity activity) {
		throw new NotImplementedException();
	}
	
	void updateCurrent(Current current) {
		throw new NotImplementedException();
	}
	
	void updateTarget(Target target) {
		throw new NotImplementedException();
	}
	
	
}
