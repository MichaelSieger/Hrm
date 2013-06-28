package de.hswt.hrm.inspection.dao.context;

import org.eclipse.e4.core.contexts.ContextFunction;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hswt.hrm.inspection.dao.core.IPerformanceDao;
import de.hswt.hrm.inspection.dao.jdbc.PerformanceDao;

public class JdbcPerformanceDaoContextFunction extends ContextFunction {
	private final static Logger LOG = LoggerFactory
			.getLogger(JdbcPerformanceDaoContextFunction.class);

	@Override
	public Object compute(IEclipseContext context) {
		// Create performance  dao and inject it into context as it might
		// need it for further injection
		IPerformanceDao performanceDao = ContextInjectionFactory.make(
				PerformanceDao.class, context);
		context.set(IPerformanceDao.class, performanceDao);

		LOG.debug("Made PerformanceDao available in Eclipse context.");
		return performanceDao;
	}
}
