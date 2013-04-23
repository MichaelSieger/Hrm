package de.hswt.hrm.place.dao.core;

import java.util.Collection;

import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.database.exception.ElementNotFoundException;
import de.hswt.hrm.common.database.exception.SaveException;
import de.hswt.hrm.place.model.Place;

/**
 * Defines all the public methods to interact with the storage system for places.
 */
public interface IPlaceDao {
    /**
     * @return All places from storage.
     */
    Collection<Place> findAll() throws DatabaseException;
    
    /**
     * @param id of the target place.
     * @return Place with the given id.
     * @throws ElementNotFoundException If the given id is not present in storage.
     */
    Place findById(int id) throws DatabaseException, ElementNotFoundException;
    
    /**
     * Add a new place to storage.
     * 
     * @param place Place that should be stored.
     * @return Newly generated place (also holding the correct id).
     * @throws SaveException If the place could not be inserted.
     */
    Place insert(Place place) throws SaveException;
    
    /**
     * Update an existing place in storage.
     * 
     * @param place Place that should be updated.
     * @throws ElementNotFoundException If the given place is not present in the database.
     * @throws SaveException If the place could not be updated.
     */
    void update(Place place) throws ElementNotFoundException, SaveException;
}
