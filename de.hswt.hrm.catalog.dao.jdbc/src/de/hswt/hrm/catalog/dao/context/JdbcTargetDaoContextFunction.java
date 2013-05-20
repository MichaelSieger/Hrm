package de.hswt.hrm.catalog.dao.context;

import org.eclipse.e4.core.contexts.ContextFunction;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hswt.hrm.catalog.dao.core.IActivityDao;
import de.hswt.hrm.catalog.dao.core.ITargetDao;
import de.hswt.hrm.catalog.dao.jdbc.ActivityDao;
import de.hswt.hrm.catalog.dao.jdbc.TargetDao;

public class JdbcTargetDaoContextFunction extends ContextFunction {
    private final static Logger LOG = LoggerFactory.getLogger(JdbcTargetDaoContextFunction.class);
    
	@Override
	public Object compute(IEclipseContext context) {
	    // Create target dao and inject context as it might need it for further injection
        ITargetDao targetDao = ContextInjectionFactory.make(TargetDao.class, context);
        context.set(ITargetDao.class, targetDao);
        
        LOG.debug("Made TargetDao available in Eclipse context.");
        return targetDao;
	}

}
