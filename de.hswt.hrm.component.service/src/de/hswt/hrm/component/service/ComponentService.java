package de.hswt.hrm.component.service;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Collection;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Creatable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.database.exception.ElementNotFoundException;
import de.hswt.hrm.common.database.exception.SaveException;
import de.hswt.hrm.component.dao.core.IComponentDao;
import de.hswt.hrm.component.model.Attribute;
import de.hswt.hrm.component.model.Component;

/**
 * Service class which should be used to interact with
 * the storage system for components.
 */
@Creatable
public class ComponentService {
    private final static Logger LOG = LoggerFactory.getLogger(ComponentService.class);
	private final IComponentDao componentDao;
	
	@Inject
	public ComponentService(final IComponentDao componentDao) {
	    checkNotNull(componentDao, "ComponentDao not correctly injected.");
	    
	    this.componentDao = componentDao;
	    LOG.debug("ComponentDao injected into ComponentService.");
	}
	
    public Component insert(Component component) throws SaveException {
        return componentDao.insert(component);
    }
	
    /**
     * @param id of the target category
     * @return component with the given id
     * @throws DatabaseException
     */
    public Component findById(int id) throws DatabaseException {
        return componentDao.findById(id);
    }
    
    /**
     * Update an existing component in storage
     * 
     * @param component CategorComponent that should be updated
     * @throws ElementNotFoundException
     * @throws SaveException
     */
    public void update(Component component) throws ElementNotFoundException, SaveException {
        componentDao.update(component);
    }
    
    /**
     * @return All components from storage
     * @throws DatabaseException
     */
    public Collection<Component> findAll() throws DatabaseException {
        return componentDao.findAll();
    }
    
    /**
     * @param id
     * @return The attribute with the given ID.
     * @throws ElementNotFoundException
     * @throws DatabaseException
     */
	public Attribute findAttributeById(int id) 
			throws ElementNotFoundException, DatabaseException {
		return componentDao.findAttributeById(id);
	}
	
	/**
     * Returns the list of attributes for a given component.
     * 
     * @param component
     * @return List of attributes for the given component.
     * @throws DatabaseException
     */
	public Collection<Attribute> findAttributesByComponent(Component component)
			throws DatabaseException {
		return componentDao.findAttributesByComponent(component);
	}

	/**
	 * Returns a list of all attribute names that were currently added to
	 * the database (attributes that were added to multiple components are
	 * just returned once, so there are no duplicates in the result).
	 * 
	 * @return Collection of attribute names already used.
	 * @throws DatabaseException
	 */
	public Collection<String> findAttributeNames() throws DatabaseException {
		return findAttributeNames();
	}
	
	/**
     * Add an attribute to a component.
     * 
     * @param component 
     * @param attributeName
     * @return The added attribute with its valid ID.
     * @throws SaveException 
     */
	public Attribute addAttribute(Component component, String attributeName) throws SaveException {
    	return componentDao.addAttribute(component, attributeName);
    }
    
    /**
     * Delete an attribute.
     * 
     * @param attribute
     * @throws DatabaseException
     */
	public void deleteAttribute(Attribute attribute) throws DatabaseException {
		componentDao.deleteAttribute(attribute);
	}

}
