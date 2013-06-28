package de.hswt.hrm.inspection.service;

import java.util.Collection;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Creatable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.exception.NotImplementedException;
import de.hswt.hrm.inspection.dao.core.IInspectionDao;
import de.hswt.hrm.inspection.model.BiologicalRating;
import de.hswt.hrm.inspection.model.Inspection;
import de.hswt.hrm.inspection.model.Performance;
import de.hswt.hrm.inspection.model.PhysicalRating;
import de.hswt.hrm.scheme.model.Scheme;

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
    
    public Inspection findById(int id) {
    	throw new NotImplementedException();
    }
    
    public Collection<BiologicalRating> findBiologicalRating(Inspection inspection) {
    	throw new NotImplementedException();
    }
    
    public Collection<PhysicalRating> findPhysicalRating(Inspection inspection) {
    	throw new NotImplementedException();
    }
    
    public Collection<Performance> findPerformance(Inspection inspection) {
    	throw new NotImplementedException();
    }
    
    public Scheme findScheme(Inspection inspection) {
    	throw new NotImplementedException();
    }
}
