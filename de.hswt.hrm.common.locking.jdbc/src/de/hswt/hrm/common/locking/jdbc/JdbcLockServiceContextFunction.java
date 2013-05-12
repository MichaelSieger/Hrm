package de.hswt.hrm.common.locking.jdbc;

import java.sql.SQLException;

import org.eclipse.e4.core.contexts.ContextFunction;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hswt.hrm.common.database.exception.DatabaseException;

public class JdbcLockServiceContextFunction extends ContextFunction {
    private final static Logger LOG = LoggerFactory.getLogger(JdbcLockServiceContextFunction.class);
    
    @Override
    public Object compute(IEclipseContext context) {
//        ILockService lockService = ContextInjectionFactory.make(LockService.class, context);
        ILockService lockService;
        try {
            lockService = new LockService();
        }
        catch (DatabaseException | SQLException e) {
            LOG.error("Could not create instance of LockService.", e);
            return null;
        }
        
        context.set(ILockService.class, lockService);
        
        LOG.debug("Made LockService available in Eclipse context.");
        return lockService;
    }

}
