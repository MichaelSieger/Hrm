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
    
    void addComponentToScheme(final Scheme scheme, final SchemeComponent component);
    
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
     * Returns a map of all attributes and their values for the given scheme component.
     * 
     * @param schemeComponent
     * @return
     * @throws DatabaseException 
     */
    Map<Attribute, String> findAttributesOfSchemeComponent(SchemeComponent schemeComponent) 
    		throws DatabaseException;
    
    /**
     * Add a new SchemeComponent to storage.
     * 
     * @param schemeComponent SchemeComponent that should be stored.
     * @return Newly generated schemeComponent (also holding the correct id).
     * @throws SaveException If the schemeComponent could not be inserted.
     */
    SchemeComponent insert(SchemeComponent schemeComponent) throws SaveException;
    
    /**
     * Update an existing schemeComponent in storage.
     * 
     * @param schemeComponent SchemeComponent that should be updated.
     * @throws ElementNotFoundException If the given schemeComponent is not present in the database.
     * @throws SaveException If the schemeComponent could not be updated.
     */
    void update(SchemeComponent schemeComponent) throws ElementNotFoundException, SaveException;
}
