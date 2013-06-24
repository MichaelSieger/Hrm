package de.hswt.hrm.photo.dao.jdbc;

import org.eclipse.e4.core.contexts.ContextFunction;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hswt.hrm.photo.dao.core.IPhotoDao;

public class JdbcPhotoDaoContextFunction extends ContextFunction {
    private final static Logger LOG = LoggerFactory.getLogger(JdbcPhotoDaoContextFunction.class);

    @Override
    public Object compute(IEclipseContext context) {
        // Create Photo dao and inject context as it might need it for further injection
        IPhotoDao photoDao = ContextInjectionFactory.make(PhotoDao.class, context);
        context.set(IPhotoDao.class, photoDao);

        LOG.debug("Made Photo available in Eclipse context.");
        return photoDao;
    }

}
