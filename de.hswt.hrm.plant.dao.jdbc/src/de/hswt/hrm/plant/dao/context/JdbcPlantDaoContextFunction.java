package de.hswt.hrm.plant.dao.context;

import org.eclipse.e4.core.contexts.ContextFunction;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hswt.hrm.plant.dao.core.IPlantDao;
import de.hswt.hrm.plant.dao.jdbc.PlantDao;

public class JdbcPlantDaoContextFunction extends ContextFunction {
    private final static Logger LOG = LoggerFactory.getLogger(JdbcPlantDaoContextFunction.class);

    @Override
    public Object compute(IEclipseContext context) {
        // Create plant dao and inject context as it might need it for further injection
        IPlantDao plantDao = ContextInjectionFactory.make(PlantDao.class, context);
        context.set(IPlantDao.class, plantDao);
        
        LOG.debug("Made PlantDao available in Eclipse context.");
        return plantDao;
    }

}
