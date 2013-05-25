package de.hswt.hrm.component.dao.context;

import org.eclipse.e4.core.contexts.ContextFunction;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hswt.hrm.component.dao.core.ICategoryDao;
import de.hswt.hrm.component.dao.jdbc.CategoryDao;

public class JdbcCategoryDaoContextFunction extends ContextFunction {
    private final static Logger LOG = LoggerFactory.getLogger(JdbcCategoryDaoContextFunction.class);

    @Override
    public Object compute(IEclipseContext context) {
        // Create category dao and inject context as it might need it for further injection
        ICategoryDao categoryDao = ContextInjectionFactory.make(CategoryDao.class, context);
        context.set(ICategoryDao.class, categoryDao);
        
        LOG.debug("Made CategoryDao available in Eclipse context.");
        return categoryDao;
    }

}
