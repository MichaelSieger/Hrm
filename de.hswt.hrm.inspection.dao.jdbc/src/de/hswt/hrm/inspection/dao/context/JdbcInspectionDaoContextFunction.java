package de.hswt.hrm.inspection.dao.context;

import org.eclipse.e4.core.contexts.ContextFunction;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hswt.hrm.inspection.dao.core.IInspectionDao;
import de.hswt.hrm.inspection.dao.jdbc.InspectionDao;

public class JdbcInspectionDaoContextFunction extends ContextFunction {
    private final static Logger LOG = LoggerFactory
            .getLogger(JdbcInspectionDaoContextFunction.class);

    @Override
    public Object compute(IEclipseContext context) {
        // Create Inspection dao and inject it into context as it might need it for further
        // injection
        IInspectionDao inspectionDao = ContextInjectionFactory.make(InspectionDao.class, context);
        context.set(IInspectionDao.class, inspectionDao);

        LOG.debug("Made InspectionDao available in Eclipse context.");
        return inspectionDao;
    }
}
