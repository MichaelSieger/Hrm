package de.hswt.hrm.scheme.service;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import java.util.Collection;
import java.util.Map;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Creatable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.database.exception.ElementNotFoundException;
import de.hswt.hrm.common.database.exception.SaveException;
import de.hswt.hrm.component.model.Attribute;
import de.hswt.hrm.plant.model.Plant;
import de.hswt.hrm.scheme.dao.core.ISchemeComponentDao;
import de.hswt.hrm.scheme.dao.core.ISchemeDao;
import de.hswt.hrm.scheme.dao.jdbc.SchemeDao;
import de.hswt.hrm.scheme.model.Scheme;
import de.hswt.hrm.scheme.model.SchemeComponent;

/**
 * Service class which should be used to interact with the storage system for scheme.
 */
@Creatable
public class SchemeService {
	private final static Logger LOG = LoggerFactory.getLogger(SchemeService.class);
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
	 * @return The created scheme.
	 * @throws SaveException 
	 * @throws ElementNotFoundException 
	 */
	public Scheme insert(Plant plant, Collection<SchemeComponent> components)
			throws SaveException, ElementNotFoundException {
		
	    checkNotNull(plant, "Plant is mandatory.");
	    checkArgument(plant.getId() >= 0, "Plant must have a valid ID.");
	    
	    // Cut away unused space from the scheme
	    components = SchemeCutter.cut(components);
	    
		// We insert a new scheme here !
	    Scheme scheme = new Scheme(plant);
	    scheme = schemeDao.insert(scheme);
	    scheme.setSchemeComponents(components);
	    
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
	    
	    return scheme;
	}
	
	public Scheme copy(final Scheme scheme) 
			throws SaveException, ElementNotFoundException, DatabaseException {
		
		checkNotNull("Scheme must not be null.");
		checkArgument(scheme.getId() >= 0, "Scheme must have a valid ID.");
		
		// Create new scheme row
		Scheme schemeCopy = schemeDao.insert(scheme);
		checkState(scheme.getId() != schemeCopy.getId(), "Copy should not have the same ID.");
		
		// Copy components
		for (SchemeComponent comp : findSchemeComponents(scheme)) {
			comp.setScheme(schemeCopy);
			schemeComponentDao.insert(comp);
		}
		
		return findById(schemeCopy.getId());
	}
	
	public Scheme copy (final Scheme scheme, final Plant plant) 
			throws ElementNotFoundException, SaveException, DatabaseException {
		
		Scheme copy = copy(scheme);
		copy.setPlant(plant);
		schemeDao.update(copy);
		
		return copy;
	}
	
	public void update(Scheme scheme, Collection<SchemeComponent> components) 
			throws ElementNotFoundException, SaveException, DatabaseException {
		
		schemeDao.update(scheme);
		
		// Remember old components to delete
		Collection<SchemeComponent> toDelete = findSchemeComponents(scheme);
		
		// Add new componentslist
		for (SchemeComponent comp : components) {
			comp.setScheme(scheme);
			schemeComponentDao.insert(comp);
		}
		
		// Delete old scheme components
		for (SchemeComponent comp : toDelete) {
			schemeComponentDao.delete(comp);
		}
	}
	
	public Scheme findById(final int id) throws ElementNotFoundException, DatabaseException {
	    checkArgument(id >= 0, "Invalid ID.");
	    Scheme scheme = schemeDao.findById(id);
	    
	    // Eager loading of the scheme components
	    Collection<SchemeComponent> components = findSchemeComponents(scheme);
	    scheme.setSchemeComponents(components);
	    return scheme;
	}
	
	/**
	 * This method does not eager load scheme components. If you need
	 * components for a certain scheme, you can use {@link #findSchemeComponents(Scheme)}
	 * to retrieve them.
	 * 
	 * @param plant
	 * @return All schemes for the given plant.
	 * @throws DatabaseException 
	 */
	public Collection<Scheme> findByPlant(final Plant plant) throws DatabaseException {
		return schemeDao.findByPlant(plant);
	}
	
	/**
	 * Scheme components get eager load and are present in the returned scheme object.
	 * 
	 * @param plant
	 * @return Current scheme of the given plant.
	 * @throws DatabaseException 
	 * @throws ElementNotFoundException If no scheme is available for the given plant.
	 */
	public Scheme findCurrentSchemeByPlant(final Plant plant) 
			throws ElementNotFoundException, DatabaseException {
		
		LOG.debug(String.format("Loading current scheme for plant '%s'.", plant.getDescription()));
		
		Scheme scheme = schemeDao.findCurrentSchemeByPlant(plant);
		Collection<SchemeComponent> components = schemeComponentDao.findAllComponentByScheme(scheme);
		scheme.setSchemeComponents(components);
		
		LOG.debug(String.format("Current scheme load for plant '%s'.", plant.getDescription()));
		
		return scheme;
	}
	
	public Collection<SchemeComponent> findSchemeComponents(final Scheme scheme)
	        throws DatabaseException {
	    
	    checkNotNull(scheme, "Scheme is mandatory.");
	    
	    return schemeComponentDao.findAllComponentByScheme(scheme);
	}

	public Map<Attribute, String> findAttributesOfSchemeComponent(SchemeComponent component) 
    		throws DatabaseException {

		return schemeComponentDao.findAttributesOfSchemeComponent(component);
	}
	
	public void setAttributeValue(SchemeComponent component, Attribute attribute, String value) 
			throws DatabaseException {
		
		schemeComponentDao.setAttributeValue(component, attribute, value);
	}
}
