package de.hswt.hrm.place.dao.context;

import org.eclipse.e4.core.contexts.ContextFunction;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hswt.hrm.place.dao.core.IPlaceDao;
import de.hswt.hrm.place.dao.jdbc.PlaceDao;

public class JdbcPlaceDaoContextFunktion extends ContextFunction {
    private final static Logger LOG = LoggerFactory.getLogger(JdbcPlaceDaoContextFunktion.class);

    @Override
    public Object compute(IEclipseContext context) {
        // Create place dao and inject context as it might need it for further injection
        IPlaceDao placeDao = ContextInjectionFactory.make(PlaceDao.class, context);
        context.set(IPlaceDao.class, placeDao);
        
        LOG.debug("Made PlaceDao available in Eclipse context.");
        return placeDao;
    }

}
