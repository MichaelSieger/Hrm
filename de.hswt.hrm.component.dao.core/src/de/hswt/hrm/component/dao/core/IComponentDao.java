package de.hswt.hrm.component.dao.core;

import java.util.Collection;

import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.database.exception.ElementNotFoundException;
import de.hswt.hrm.common.database.exception.SaveException;
import de.hswt.hrm.component.model.Attribute;
import de.hswt.hrm.component.model.Component;

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
     * Returns the list of attributes for a given component.
     * 
     * @param component
     * @return List of attributes for the given component.
     * @throws DatabaseException
     */
	Collection<Attribute> findAttributesByComponent(Component component)
			throws DatabaseException;

	/**
	 * Returns a list of all attribute names that were currently added to
	 * the database (attributes that were added to multiple components are
	 * just returned once, so there are no duplicates in the result).
	 * 
	 * @return Collection of attribute names already used.
	 * @throws DatabaseException
	 */
	Collection<String> findAttributeNames() throws DatabaseException;
    
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

    /**
     * Add an attribute to a component.
     * 
     * @param component 
     * @param attributeName
     * @return The added attribute with its valid ID.
     * @throws SaveException 
     */
    Attribute addAttribute(Component component, String attributeName) throws SaveException;
    
    /**
     * Delete an attribute.
     * 
     * @param attribute
     * @throws DatabaseException
     */
	void deleteAttribute(Attribute attribute) throws DatabaseException;
}
