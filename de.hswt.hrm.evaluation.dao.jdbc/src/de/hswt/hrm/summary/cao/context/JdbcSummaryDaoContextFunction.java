package de.hswt.hrm.summary.cao.context;

import org.eclipse.e4.core.contexts.ContextFunction;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hswt.hrm.summary.dao.core.ISummaryDao;
import de.hswt.hrm.summary.dao.jdbc.SummaryDao;

public class JdbcSummaryDaoContextFunction extends ContextFunction {

    private final static Logger LOG = LoggerFactory.getLogger(JdbcSummaryDaoContextFunction.class);

    @Override
    public Object compute(IEclipseContext context) {
        // Create summarydao and inject context as it might need it for further injection
        ISummaryDao sumDao = ContextInjectionFactory.make(SummaryDao.class, context);
        context.set(ISummaryDao.class, sumDao);

        LOG.debug("Made SummaryDao available in Eclipse Context");

        return sumDao;
    }

}
