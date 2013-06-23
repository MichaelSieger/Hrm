package de.hswt.hrm.misc.priority.dao.core;

import java.util.Collection;

import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.database.exception.ElementNotFoundException;
import de.hswt.hrm.common.database.exception.SaveException;

import de.hswt.hrm.misc.priority.model.Priority;

/**
 * Defines all the public methods to interact with the storage system for priorities.
 */
public interface IPriorityDao {

    /**
     * @return All priorities from storage.
     */
    Collection<Priority> findAll() throws DatabaseException;

    /**
     * @param id
     *            of the target priority.
     * @return Priority with the given id.
     * @throws ElementNotFoundException
     *             If the given id is not present in the database.
     */
    Priority findById(int id) throws DatabaseException, ElementNotFoundException;

    /**
     * Add a new priority to storage.
     * 
     * @param priority
     *            Priority that should be stored.
     * @return Newly generated priority (also holding the correct id).
     * @throws SaveException
     *             If the priority could not be inserted.
     */
    Priority insert(Priority priority) throws SaveException;

    /**
     * Update an existing priority in storage.
     * 
     * @param priority
     *            Priority that should be updated.
     * @throws ElementNotFoundException
     *             If the given priority is not present in the database.
     * @throws SaveException
     *             If the priority could not be updated.
     */
    void update(Priority priority) throws ElementNotFoundException, SaveException;

}
