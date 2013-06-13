package de.hswt.hrm.inspection.dao.context;

import org.eclipse.e4.core.contexts.ContextFunction;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hswt.hrm.inspection.dao.core.IBiologicalRatingDao;
import de.hswt.hrm.inspection.dao.jdbc.BiologicalRatingDao;

public class JdbcBiologicalRatingDaoContextFunction extends ContextFunction {
	private final static Logger LOG = LoggerFactory.getLogger(JdbcBiologicalRatingDaoContextFunction.class);

	@Override
	public Object compute(IEclipseContext context) {
	    // Create biological rating dao and inject it into context as it might need it for further injection
        IBiologicalRatingDao biologicalRatingDao = ContextInjectionFactory.make(
        		BiologicalRatingDao.class,
        		context);
        context.set(IBiologicalRatingDao.class, biologicalRatingDao);
        
        LOG.debug("Made BiologicalRatingDao available in Eclipse context.");
        return biologicalRatingDao;
	}
}
