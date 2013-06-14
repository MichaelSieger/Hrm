package de.hswt.hrm.inspection.dao.core;

import java.util.Collection;

import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.database.exception.ElementNotFoundException;
import de.hswt.hrm.common.database.exception.SaveException;
import de.hswt.hrm.inspection.model.Inspection;
import de.hswt.hrm.inspection.model.PhysicalRating;
import de.hswt.hrm.scheme.model.Scheme;

/**
 * Defines all the public methods to interact with the storage system for activitys.
 */
public interface IPhysicalRatingDao {

    /**
     * @return All physical ratings from storage.
     */
    Collection<PhysicalRating> findAll() throws DatabaseException;

    /**
     * @param id of the target physical rating.
     * @return physical with the given id.
     * @throws ElementNotFoundException If the given id is not present in the database.
     */
    PhysicalRating findById(int id) throws DatabaseException, ElementNotFoundException;

    /**
     * @param scheme
     * @return All physical ratings for the given Inspection.
     * @throws DatabaseException 
     */
    Collection<PhysicalRating> findByInspection(final Inspection inspection) throws DatabaseException;
    
    /**
     * Add a new physical rating to storage.
     * 
     * @param physical Physical that should be stored.
     * @return Newly generated physical (also holding the correct id).
     * @throws SaveException If the physical could not be inserted.
     */
    PhysicalRating insert(PhysicalRating physical) throws SaveException;

    /**
     * Update an existing physical rating in storage.
     * 
     * @param physical Physical that should be updated.
     * @throws ElementNotFoundException If the given Physical is not present in the database.
     * @throws SaveException If the physical could not be updated.
     */
    void update(PhysicalRating physical) throws ElementNotFoundException, SaveException;
}

