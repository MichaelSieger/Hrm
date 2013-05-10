package de.hswt.hrm.scheme.dao.core;

import java.util.Collection;

import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.database.exception.ElementNotFoundException;
import de.hswt.hrm.common.database.exception.SaveException;
import de.hswt.hrm.scheme.model.Component;

/**
 * Defines all the public methods to interact with the storage system for components.
 */
public interface IComponentDao {
    
    /**
     * @return All components from storage.
     */
    Collection<Component> findAll() throws DatabaseException;
    
    /**
     * @param id of the target component.
     * @return Component with the given id.
     * @throws ElementNotFoundException If the given id is not present in the database.
     */
    Component findById(int id) throws DatabaseException, ElementNotFoundException;
    
    /**
     * Add a new component to storage.
     * 
     * @param component Component that should be stored.
     * @return Newly generated component (also holding the correct id).
     * @throws SaveException If the component could not be inserted.
     */
    Component insert(Component component) throws SaveException;
    
    /**
     * Update an existing component in storage.
     * 
     * @param component Component that should be updated.
     * @throws ElementNotFoundException If the given component is not present in the database.
     * @throws SaveException If the component could not be updated.
     */
    void update(Component component) throws ElementNotFoundException, SaveException;
}
