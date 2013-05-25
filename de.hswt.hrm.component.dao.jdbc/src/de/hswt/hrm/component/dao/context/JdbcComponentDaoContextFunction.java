package de.hswt.hrm.component.dao.context;

import org.eclipse.e4.core.contexts.ContextFunction;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hswt.hrm.component.dao.core.IComponentDao;
import de.hswt.hrm.component.dao.jdbc.ComponentDao;

public class JdbcComponentDaoContextFunction extends ContextFunction {
    private final static Logger LOG = LoggerFactory.getLogger(JdbcComponentDaoContextFunction.class);

    @Override
    public Object compute(IEclipseContext context) {
        // Create component dao and inject context as it might need it for further injection
        IComponentDao componentDao = ContextInjectionFactory.make(ComponentDao.class, context);
        context.set(IComponentDao.class, componentDao);
        
        LOG.debug("Made ComponentDao available in Eclipse context.");
        return componentDao;
    }

}
