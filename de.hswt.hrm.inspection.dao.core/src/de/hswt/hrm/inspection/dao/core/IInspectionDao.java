package de.hswt.hrm.inspection.dao.core;

import java.util.Collection;

import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.database.exception.ElementNotFoundException;
import de.hswt.hrm.common.database.exception.SaveException;
import de.hswt.hrm.inspection.model.Inspection;

/**
 * Defines all the public methods to interact with the storage system for activitys.
 */
public interface IInspectionDao {

    /**
     * @return All inspections from storage.
     */
    Collection<Inspection> findAll() throws DatabaseException;

    /**
     * @param id of the target inspection.
     * @return inspection with the given id.
     * @throws ElementNotFoundException If the given id is not present in the database.
     */
    Inspection findById(int id) throws DatabaseException, ElementNotFoundException;

    /**
     * Add a new inspection to storage.
     * 
     * @param inspection Inspection that should be stored.
     * @return Newly generated inspection (also holding the correct id).
     * @throws SaveException If the inspection could not be inserted.
     */
    Inspection insert(Inspection inspection) throws SaveException;

    /**
     * Update an existing inspection in storage.
     * 
     * @param inspection Inspection that should be updated.
     * @throws ElementNotFoundException If the given Inspection is not present in the database.
     * @throws SaveException If the inspection could not be updated.
     */
    void update(Inspection inspection) throws ElementNotFoundException, SaveException;
}

