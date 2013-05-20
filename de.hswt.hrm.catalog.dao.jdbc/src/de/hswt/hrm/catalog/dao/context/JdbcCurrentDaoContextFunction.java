package de.hswt.hrm.catalog.dao.context;

import org.eclipse.e4.core.contexts.ContextFunction;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hswt.hrm.catalog.dao.core.ICurrentDao;
import de.hswt.hrm.catalog.dao.jdbc.CurrentDao;

public class JdbcCurrentDaoContextFunction extends ContextFunction {
    private final static Logger LOG = LoggerFactory.getLogger(JdbcCurrentDaoContextFunction.class);
    
	@Override
	public Object compute(IEclipseContext context) {
	    // Create current dao and inject context as it might need it for further injection
        ICurrentDao currentDao = ContextInjectionFactory.make(CurrentDao.class, context);
        context.set(ICurrentDao.class, currentDao);
        
        LOG.debug("Made CurrentDao available in Eclipse context.");
        return currentDao;
	}

}
