package de.hswt.hrm.inspection.dao.context;

import org.eclipse.e4.core.contexts.ContextFunction;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hswt.hrm.inspection.dao.core.ILayoutDao;
import de.hswt.hrm.inspection.dao.jdbc.LayoutDao;

public class JdbcLayoutDaoContextFunction extends ContextFunction {
	private final static Logger LOG = LoggerFactory.getLogger(JdbcLayoutDaoContextFunction.class);

	@Override
	public Object compute(IEclipseContext context) {
	    // Create biological rating dao and inject it into context as it might need it for further injection
        ILayoutDao layoutDao = ContextInjectionFactory.make(LayoutDao.class, context);
        context.set(ILayoutDao.class, layoutDao);
        
        LOG.debug("Made LayoutDao available in Eclipse context.");
        return layoutDao;
	}

}
