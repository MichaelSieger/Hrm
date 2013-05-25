package de.hswt.hrm.catalog.dao.context;

import org.eclipse.e4.core.contexts.ContextFunction;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hswt.hrm.catalog.dao.core.ICatalogDao;
import de.hswt.hrm.catalog.dao.jdbc.CatalogDao;

public class JdbcCatalogDaoContextFunction extends ContextFunction {
    private final static Logger LOG = LoggerFactory.getLogger(JdbcCatalogDaoContextFunction.class);
    
    @Override
    public Object compute(IEclipseContext context) {
        // Create catalog dao and inject context as it might need it for further injection
        ICatalogDao catalogDao = ContextInjectionFactory.make(CatalogDao.class, context);
        context.set(ICatalogDao.class, catalogDao);
        
        LOG.debug("Made CatalogDao available in Eclipse context.");
        return catalogDao;
    }
}
