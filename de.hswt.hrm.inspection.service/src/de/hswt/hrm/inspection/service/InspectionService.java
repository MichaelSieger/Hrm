package de.hswt.hrm.inspection.service;

import java.util.Collection;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Creatable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.inspection.dao.core.IInspectionDao;
import de.hswt.hrm.inspection.model.Inspection;

@Creatable
public class InspectionService {

    private static final Logger LOG = LoggerFactory.getLogger(InspectionService.class);

    private final IInspectionDao dao;

    @Inject
    public InspectionService(IInspectionDao dao) {
        this.dao = dao;

        if (dao == null) {
            LOG.error("ContactDao not injected to ContactService.");
        }
    }
    
    public Collection<Inspection> findAll() throws DatabaseException{
        return dao.findAll();
    }
}
