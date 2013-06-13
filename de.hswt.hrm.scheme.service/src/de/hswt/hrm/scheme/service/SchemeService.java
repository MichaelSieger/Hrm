package de.hswt.hrm.scheme.service;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Collection;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Creatable;

import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.database.exception.ElementNotFoundException;
import de.hswt.hrm.common.database.exception.SaveException;
import de.hswt.hrm.plant.model.Plant;
import de.hswt.hrm.scheme.dao.core.ISchemeComponentDao;
import de.hswt.hrm.scheme.dao.core.ISchemeDao;
import de.hswt.hrm.scheme.model.Scheme;
import de.hswt.hrm.scheme.model.SchemeComponent;

/**
 * Service class which should be used to interact with the storage system for scheme.
 */
@Creatable
public class SchemeService {
	
    private final ISchemeDao schemeDao;
    private final ISchemeComponentDao schemeComponentDao;
    
    @Inject
    public SchemeService(final ISchemeDao schemeDao, final ISchemeComponentDao schemeComponentDao) {
        checkNotNull(schemeDao, "SchemeDao must be injected properly.");
        checkNotNull(schemeComponentDao, "SchemeComponentDao must be injected properly");
        
        this.schemeDao = schemeDao;
        this.schemeComponentDao = schemeComponentDao;
    }
    
	/**
	 * Inserts a new Scheme.
	 * 
	 * @param plant The Plant which the Scheme belongs to
	 * @param components The Scheme defined by its components
	 * @throws SaveException 
	 * @throws ElementNotFoundException 
	 */
	public void insert(Plant plant, Collection<SchemeComponent> components)
			throws SaveException, ElementNotFoundException {
		
	    checkNotNull(plant, "Plant is mandatory.");
	    checkArgument(plant.getId() >= 0, "Plant must have a valid ID.");
	    
	    //Cut away unused space from the scheme
	    components = SchemeCutter.cut(components);
	    
		// We insert a new scheme here !
	    Scheme scheme = new Scheme(plant);
	    scheme = schemeDao.insert(scheme);
	    
	    // Add all components
	    for (SchemeComponent comp : components) {
	    	comp.setScheme(scheme);
	    	if (comp.getId() < 0) {
	    		schemeComponentDao.insert(comp);
	    	}
	    	else {
	    		schemeComponentDao.update(comp);
	    	}
	    }
	}
	
	public Scheme findById(final int id) throws ElementNotFoundException, DatabaseException {
	    checkArgument(id >= 0, "Invalid ID.");
	    
	    return schemeDao.findById(id);
	}
	
	public Collection<SchemeComponent> findSchemeComponents(final Scheme scheme)
	        throws DatabaseException {
	    
	    checkNotNull(scheme, "Scheme is mandatory.");
	    
	    return schemeComponentDao.findAllComponentByScheme(scheme);
	}

}
