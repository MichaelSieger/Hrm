package de.hswt.hrm.evaluation.dao.jdbc;

import org.eclipse.e4.core.contexts.ContextFunction;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hswt.hrm.evaluation.dao.core.IEvaluationDao;

public class JdbcEvaluationDaoContextFunction extends ContextFunction {

    private final static Logger LOG = LoggerFactory
            .getLogger(JdbcEvaluationDaoContextFunction.class);

    @Override
    public Object compute(IEclipseContext context) {
        // Create evaluation dao and inject context as it might need it for further injection
        IEvaluationDao evalDao = ContextInjectionFactory.make(EvaluationDao.class, context);
        context.set(IEvaluationDao.class, evalDao);

        LOG.debug("Made EvaluationDao available in Eclipse Context");

        return evalDao;
    }

}
