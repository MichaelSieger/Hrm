package de.hswt.hrm.scheme.dao.context;

import org.eclipse.e4.core.contexts.ContextFunction;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hswt.hrm.scheme.dao.core.ISchemeComponentDao;
import de.hswt.hrm.scheme.dao.jdbc.SchemeComponentDao;

public class JdbcSchemeComponentDaoContextFunction extends ContextFunction {
    private final static Logger LOG = LoggerFactory.getLogger(
            JdbcSchemeComponentDaoContextFunction.class);
    
    @Override
    public Object compute(IEclipseContext context) {
        // Create schemecomponent dao and inject context as it might need it for further injection
        ISchemeComponentDao dao = ContextInjectionFactory.make(SchemeComponentDao.class, context);
        context.set(ISchemeComponentDao.class, dao);
        
        LOG.debug("Made SchemeComponentDao available in Eclipse context.");
        return dao;
    }

}
