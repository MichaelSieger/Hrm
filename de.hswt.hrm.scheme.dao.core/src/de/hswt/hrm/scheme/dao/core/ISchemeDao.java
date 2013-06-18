package de.hswt.hrm.scheme.dao.core;

import java.util.Collection;

import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.database.exception.ElementNotFoundException;
import de.hswt.hrm.common.database.exception.SaveException;
import de.hswt.hrm.plant.model.Plant;
import de.hswt.hrm.scheme.model.Scheme;

/**
 * Defines all the public methods to interact with the storage system for schemes.
 */
public interface ISchemeDao {
    /**
     * @return All Schemes from storage.
     */
    Collection<Scheme> findAll() throws DatabaseException;
    
    /**
     * @param id of the target scheme.
     * @return Scheme with the given id.
     * @throws ElementNotFoundException If the given id is not present in storage.
     */
    Scheme findById(int id) throws DatabaseException, ElementNotFoundException;
    
    /**
     * @param plant
     * @return List of schemes for the given plant.
     * @throws DatabaseException 
     */
    Collection<Scheme> findByPlant(Plant plant) throws DatabaseException;
    
    /**
     * @param plant
     * @return The current scheme for the given plant.
     * @throws ElementNotFoundException If there is no scheme for the given plant.
     * @throws DatabaseException 
     */
    Scheme findCurrentSchemeByPlant(Plant plant) 
    		throws ElementNotFoundException, DatabaseException;
    
    /**
     * Add a new Scheme to storage.
     * 
     * @param scheme Scheme that should be stored.
     * @return Newly generated scheme (also holding the correct id).
     * @throws SaveException If the scheme could not be inserted.
     */
    Scheme insert(Scheme scheme) throws SaveException;
    
    /**
     * Update an existing scheme in storage.
     * 
     * @param scheme Scheme that should be updated.
     * @throws ElementNotFoundException If the given scheme is not present in the database.
     * @throws SaveException If the scheme could not be updated.
     */
    void update(Scheme scheme) throws ElementNotFoundException, SaveException;
}
