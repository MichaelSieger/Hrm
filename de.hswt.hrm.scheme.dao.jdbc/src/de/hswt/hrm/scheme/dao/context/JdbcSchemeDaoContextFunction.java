package de.hswt.hrm.scheme.dao.context;

import org.eclipse.e4.core.contexts.ContextFunction;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hswt.hrm.scheme.dao.core.ISchemeDao;
import de.hswt.hrm.scheme.dao.jdbc.SchemeDao;

public class JdbcSchemeDaoContextFunction extends ContextFunction {
    private final static Logger LOG = LoggerFactory.getLogger(JdbcSchemeDaoContextFunction.class);

    @Override
    public Object compute(IEclipseContext context) {
        // Create scheme dao and inject context as it might need it for further injection
        ISchemeDao schemeDao = ContextInjectionFactory.make(SchemeDao.class, context);
        context.set(ISchemeDao.class, schemeDao);
        
        LOG.debug("Made SchemeDao available in Eclipse context.");
        return schemeDao;
    }

}
