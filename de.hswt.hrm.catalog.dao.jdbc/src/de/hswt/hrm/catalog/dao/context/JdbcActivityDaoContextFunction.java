package de.hswt.hrm.catalog.dao.context;

import org.eclipse.e4.core.contexts.ContextFunction;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hswt.hrm.catalog.dao.core.IActivityDao;
import de.hswt.hrm.catalog.dao.jdbc.ActivityDao;

public class JdbcActivityDaoContextFunction extends ContextFunction {
    private final static Logger LOG = LoggerFactory.getLogger(JdbcActivityDaoContextFunction.class);

	@Override
	public Object compute(IEclipseContext context) {
	    // Create activity dao and inject context as it might need it for further injection
        IActivityDao activityDao = ContextInjectionFactory.make(ActivityDao.class, context);
        context.set(IActivityDao.class, activityDao);
        
        LOG.debug("Made ActivityDao available in Eclipse context.");
        return activityDao;
	}

}
