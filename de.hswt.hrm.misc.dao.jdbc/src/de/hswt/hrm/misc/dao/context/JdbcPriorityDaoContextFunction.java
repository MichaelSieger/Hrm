package de.hswt.hrm.misc.dao.context;

import org.eclipse.e4.core.contexts.ContextFunction;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hswt.hrm.misc.priority.dao.core.IPriorityDao;
import de.hswt.hrm.misc.priority.dao.jdbc.PriorityDao;

public class JdbcPriorityDaoContextFunction extends ContextFunction {

    private static final Logger LOG = LoggerFactory.getLogger(JdbcPriorityDaoContextFunction.class);

    @Override
    public Object compute(IEclipseContext context) {

        IPriorityDao prioDao = ContextInjectionFactory.make(PriorityDao.class, context);
        context.set(IPriorityDao.class, prioDao);

        LOG.debug("Made PriorityDao available in Eclipse Context");

        return prioDao;
    }

}
