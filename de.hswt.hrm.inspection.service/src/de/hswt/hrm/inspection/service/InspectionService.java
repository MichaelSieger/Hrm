package de.hswt.hrm.inspection.service;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Collection;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Creatable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.database.exception.ElementNotFoundException;
import de.hswt.hrm.common.exception.NotImplementedException;
import de.hswt.hrm.inspection.dao.core.IBiologicalRatingDao;
import de.hswt.hrm.inspection.dao.core.IInspectionDao;
import de.hswt.hrm.inspection.dao.core.IPerformanceDao;
import de.hswt.hrm.inspection.dao.core.IPhysicalRatingDao;
import de.hswt.hrm.inspection.model.BiologicalRating;
import de.hswt.hrm.inspection.model.Inspection;
import de.hswt.hrm.inspection.model.Performance;
import de.hswt.hrm.inspection.model.PhysicalRating;
import de.hswt.hrm.scheme.dao.core.ISchemeDao;
import de.hswt.hrm.scheme.model.Scheme;

@Creatable
public class InspectionService {
    private static final Logger LOG = LoggerFactory.getLogger(InspectionService.class);

    private final IInspectionDao inspectionDao;
    private final IPhysicalRatingDao physicalDao;
    private final IBiologicalRatingDao biologicalDao;
    private final IPerformanceDao performanceDao;
    private final ISchemeDao schemeDao;

    @Inject
    public InspectionService(IInspectionDao inspectionDao, IPhysicalRatingDao physicalDao,
    		IBiologicalRatingDao biologicalDao, IPerformanceDao performanceDao,
    		ISchemeDao schemeDao) {
        checkNotNull(inspectionDao, "InspectionDao not properly injected.");
        checkNotNull(physicalDao, "PhysicalRatingDao not properly injected.");
        checkNotNull(biologicalDao, "BiologicalRatingDao not properly injected.");
        checkNotNull(performanceDao, "PerformanceDao not properly injected.");
        checkNotNull(schemeDao, "SchemeDAo not properly injected.");
    	
    	this.inspectionDao = inspectionDao;
    	this.physicalDao = physicalDao;
    	this.biologicalDao = biologicalDao;
    	this.performanceDao = performanceDao;
    	this.schemeDao = schemeDao;

    	// TODO Add log outputs
        if (inspectionDao == null) {
            LOG.error("ContactDao not injected to ContactService.");
        }
    }
    
    public Collection<Inspection> findAll() throws DatabaseException{
        return inspectionDao.findAll();
    }
    
    public Inspection findById(int id) throws ElementNotFoundException, DatabaseException {
    	return inspectionDao.findById(id);
    }
    
    public Collection<BiologicalRating> findBiologicalRating(Inspection inspection) 
    		throws DatabaseException {
    	
    	return biologicalDao.findByInspection(inspection);
    }
    
    public Collection<PhysicalRating> findPhysicalRating(Inspection inspection)
    		throws DatabaseException {
    	
    	return physicalDao.findByInspection(inspection);
    }
    
    public Collection<Performance> findPerformance(Inspection inspection) 
    		throws DatabaseException {
 
    	return performanceDao.findByInspection(inspection);
    }
    
    public Scheme findScheme(Inspection inspection) {
    	throw new NotImplementedException();
    }
}
