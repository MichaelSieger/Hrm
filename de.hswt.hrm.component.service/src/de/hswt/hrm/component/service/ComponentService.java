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
import de.hswt.hrm.component.model.Category;
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

}
