package de.hswt.hrm.inspection.dao.context;

import org.eclipse.e4.core.contexts.ContextFunction;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hswt.hrm.inspection.dao.core.IPhysicalRatingDao;
import de.hswt.hrm.inspection.dao.jdbc.PhysicalRatingDao;

public class JdbcPhysicalRatingDaoContextFunction extends ContextFunction {
	private final static Logger LOG = LoggerFactory.getLogger(JdbcPhysicalRatingDaoContextFunction.class);

	@Override
	public Object compute(IEclipseContext context) {
	    // Create physical rating dao and inject it into context as it might need it for further injection
        IPhysicalRatingDao physicalRatingDao = ContextInjectionFactory.make(
        		PhysicalRatingDao.class, 
        		context);
        context.set(IPhysicalRatingDao.class, physicalRatingDao);
        
        LOG.debug("Made PhysicalRatingDao available in Eclipse context.");
        return physicalRatingDao;
	}
}
