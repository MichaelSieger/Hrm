package de.hswt.hrm.misc.priority.service;

import java.util.Collection;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Creatable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.google.common.base.Preconditions.checkNotNull;

import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.database.exception.ElementNotFoundException;
import de.hswt.hrm.common.database.exception.SaveException;
import de.hswt.hrm.misc.priority.dao.core.IPriorityDao;
import de.hswt.hrm.misc.priority.model.Priority;

@Creatable
public class PriorityService {

    private final static Logger LOG = LoggerFactory.getLogger(PriorityService.class);

    private final IPriorityDao prioDao;

    @Inject
    public PriorityService(IPriorityDao prioDao) {

        checkNotNull(prioDao, "PriorityDao must be injected properly");
        this.prioDao = prioDao;
        LOG.debug("Priority Dao injected successfully");
    }
    
    public Collection<Priority> findAll() throws DatabaseException {
        return prioDao.findAll();

    }

    public Priority findById(int id) throws ElementNotFoundException, DatabaseException {
        return prioDao.findById(id);
    }

    public Priority insert(final Priority priority) throws SaveException {
        return prioDao.insert(priority);
    }

    public void update(final Priority priority) throws ElementNotFoundException, SaveException {
        prioDao.update(priority);
    }

    public void refresh(Priority prio) throws DatabaseException {

        Priority fromDb = prioDao.findById(prio.getId());

        prio.setName(fromDb.getName());
        prio.setText(fromDb.getText());
        prio.setPriority(fromDb.getPriority());

    }

}