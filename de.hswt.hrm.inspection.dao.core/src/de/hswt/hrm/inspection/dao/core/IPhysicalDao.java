package de.hswt.hrm.inspection.dao.core;

import java.util.Collection;

import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.database.exception.ElementNotFoundException;
import de.hswt.hrm.common.database.exception.SaveException;
import de.hswt.hrm.inspection.model.PhysicalRating;

/**
 * Defines all the public methods to interact with the storage system for activitys.
 */
public interface IPhysicalDao {

    /**
     * @return All pysicals from storage.
     */
    Collection<PhysicalRating> findAll() throws DatabaseException;

    /**
     * @param id of the target physical.
     * @return physical with the given id.
     * @throws ElementNotFoundException If the given id is not present in the database.
     */
    PhysicalRating findById(int id) throws DatabaseException, ElementNotFoundException;

    /**
     * Add a new physical to storage.
     * 
     * @param physical Physical that should be stored.
     * @return Newly generated physical (also holding the correct id).
     * @throws SaveException If the physical could not be inserted.
     */
    PhysicalRating insert(PhysicalRating physical) throws SaveException;

    /**
     * Update an existing physical in storage.
     * 
     * @param physical Physical that should be updated.
     * @throws ElementNotFoundException If the given Physical is not present in the database.
     * @throws SaveException If the physical could not be updated.
     */
    void update(PhysicalRating physical) throws ElementNotFoundException, SaveException;
}

