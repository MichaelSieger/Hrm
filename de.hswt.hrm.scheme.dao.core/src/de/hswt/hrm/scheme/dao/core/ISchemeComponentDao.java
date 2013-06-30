package de.hswt.hrm.scheme.dao.core;

import java.util.Collection;
import java.util.Map;

import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.database.exception.ElementNotFoundException;
import de.hswt.hrm.common.database.exception.SaveException;
import de.hswt.hrm.component.model.Attribute;
import de.hswt.hrm.scheme.model.Scheme;
import de.hswt.hrm.scheme.model.SchemeComponent;

public interface ISchemeComponentDao {
    
    Collection<SchemeComponent> findAllComponentByScheme(final Scheme scheme)
            throws DatabaseException;
    
    /**
     * @return All SchemeComponents from storage.
     */
    Collection<SchemeComponent> findAll() throws DatabaseException;
    
    /**
     * @param id of the target schemeComponent.
     * @return SchemeComponent with the given id.
     * @throws ElementNotFoundException If the given id is not present in storage.
     */
    SchemeComponent findById(int id) throws DatabaseException, ElementNotFoundException;
    
    /**
     * Returns a map of all attributes and their values for the given scheme component
     * (only attributes that has a value assigned are returned).
     * 
     * @param schemeComponent
     * @return
     * @throws DatabaseException 
     */
    Map<Attribute, String> findAttributesOfSchemeComponent(SchemeComponent schemeComponent) 
    		throws DatabaseException;
    
    Map<Attribute, String> findAttributesOfSchemeComponent(int componentId)
            throws DatabaseException;
    
    /**
     * Set a value for an attribute of the given scheme component.
     * 
     * @param component
     * @param attribute
     * @param value
     * @throws DatabaseException
     * @throws IllegalStateException If the attribute does not belong to the component.
     */
    void addAttributeValue(SchemeComponent component, Attribute attribute, String value)
    		throws DatabaseException;
    
    /**
     * Add a new SchemeComponent to storage. The scheme and component this 
     * SchemeComponent belongs to has to be in the database already.
     * 
     * @param schemeComponent SchemeComponent that should be stored.
     * @return Newly generated schemeComponent (also holding the correct id).
     * @throws SaveException If the schemeComponent could not be inserted.
     * @throws IllegalStateException If scheme or component have an invalid ID.
     */
    SchemeComponent insert(SchemeComponent schemeComponent) throws SaveException;
    
    /**
     * Update an existing schemeComponent in storage. The scheme and component this 
     * SchemeComponent belongs to has to be in the database already.
     * 
     * @param schemeComponent SchemeComponent that should be updated.
     * @throws ElementNotFoundException If the given schemeComponent is not present in the database.
     * @throws SaveException If the schemeComponent could not be updated.
     * @throws DatabaseException 
     * @throws IllegalStateException If scheme or component have an invalid ID.
     */
    void update(SchemeComponent schemeComponent) 
            throws ElementNotFoundException, SaveException, DatabaseException;

    /**
     * @param component
     * @throws ElementNotFoundException
     * @throws DatabaseException
     */
	void delete(SchemeComponent component) throws ElementNotFoundException,
			DatabaseException;

	/**
	 * Reassignes an attribute with its value to another scheme component.
	 * 
	 * @param attribute The attribute that should be reassigned.
	 * @param sourceComp Source component where the attribute is currently assigned.
	 * @param targetComp Target component where the attribute should be assigned.
	 * @throws SaveException
	 * @throws DatabaseException
	 */
	void reassignAttributeValue(Attribute attribute,
			SchemeComponent sourceComp, SchemeComponent targetComp)
					throws SaveException, DatabaseException;

	/**
	 * Deletes an attribute value from scheme component.
	 * 
	 * @param component
	 * @param attribute
	 * @throws DatabaseException
	 */
	void delete(SchemeComponent component, Attribute attribute)
			throws DatabaseException;

	void updateAttributeValue(SchemeComponent comp, Attribute attribute,
			String value) throws DatabaseException;

	boolean hasAttributeValue(SchemeComponent component, Attribute attribute)
			throws DatabaseException;
}
