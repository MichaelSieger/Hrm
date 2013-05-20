package de.hswt.hrm.scheme.service;

import java.util.Collection;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkArgument;

import de.hswt.hrm.plant.model.Plant;
import de.hswt.hrm.scheme.dao.core.ISchemeComponentDao;
import de.hswt.hrm.scheme.dao.core.ISchemeDao;
import de.hswt.hrm.scheme.model.Scheme;
import de.hswt.hrm.scheme.model.SchemeComponent;

/**
 * Service class which should be used to interact with the storage system for scheme.
 */
public class SchemeService {
	
    private final ISchemeDao schemeDao;
    private final ISchemeComponentDao schemeComponentDao;
    
    // FIXME: add @inject
    public SchemeService(final ISchemeDao schemeDao, final ISchemeComponentDao schemeComponentDao) {
        this.schemeDao = schemeDao;
        this.schemeComponentDao = schemeComponentDao;
    }
    
	/**
	 * Inserts a new Scheme.
	 * 
	 * @param plant The Plant which the Scheme belongs to
	 * @param components The Scheme defined by its components
	 */
	public void insert(Plant plant, Collection<SchemeComponent> components){
	    checkNotNull(plant, "Plant is mandatory.");
	    checkArgument(plant.getId() >= 0, "Plant must have a valid ID.");
	    
		// We insert a new scheme here !
	    Scheme scheme = new Scheme();
	    scheme.setPlant(plant);
	    scheme = schemeDao.insert(scheme);
	    
	    // Add all components
	    for (SchemeComponent comp : components) {
	        schemeComponentDao.insertComponent(scheme, comp);
	    }
	}

}
