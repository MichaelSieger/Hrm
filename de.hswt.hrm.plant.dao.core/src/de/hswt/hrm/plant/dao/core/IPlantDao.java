package de.hswt.hrm.plant.dao.core;

import java.util.Collection;

import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.database.exception.ElementNotFoundException;
import de.hswt.hrm.common.database.exception.SaveException;
import de.hswt.hrm.plant.model.Plant;

/**
 * Defines all the public methods to interact with the storage system for plants.
 */
public interface IPlantDao {
    /**
     * @return All plants from storage.
     */
    Collection<Plant> findAll() throws DatabaseException;
    
    /**
     * @param id of the target plant.
     * @return Plant with the given id.
     * @throws ElementNotFoundException If the given id is not present in storage.
     */
    Plant findById(int id) throws DatabaseException, ElementNotFoundException;
    
    /**
     * Add a new plant to storage.
     * 
     * @param plant Plant that should be stored.
     * @return Newly generated plant (also holding the correct id).
     * @throws SaveException If the plant could not be inserted.
     */
    Plant insert(Plant plant) throws SaveException;
    
    /**
     * Update an existing plant in storage.
     * 
     * @param plant Plant that should be updated.
     * @throws ElementNotFoundException If the given plant is not present in the database.
     * @throws SaveException If the plant could not be updated.
     */
    void update(Plant plant) throws ElementNotFoundException, SaveException;
}
