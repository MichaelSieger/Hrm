package de.hswt.hrm.contact.dao.context;

import org.eclipse.e4.core.contexts.ContextFunction;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hswt.hrm.contact.dao.core.IContactDao;
import de.hswt.hrm.contact.dao.jdbc.ContactDao;

public class JdbcContactDaoContextFunction extends ContextFunction {

    private final static Logger LOG = LoggerFactory.getLogger(JdbcContactDaoContextFunction.class);

    @Override
    public Object compute(IEclipseContext context) {
        // Create contact dao and inject context as it might need it for further injection
        IContactDao contactDao = ContextInjectionFactory.make(ContactDao.class, context);
        context.set(IContactDao.class, contactDao);

        LOG.debug("Made ContactDao available in Eclipse Context");
        return contactDao;
    }

}
