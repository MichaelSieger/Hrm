package de.hswt.hrm.catalog.dao.core;

import java.util.Collection;

import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.database.exception.ElementNotFoundException;
import de.hswt.hrm.common.database.exception.SaveException;
import de.hswt.hrm.catalog.model.Current;
import de.hswt.hrm.catalog.model.Target;

/**
 * Defines all the public methods to interact with the storage system for currents.
 */
public interface ICurrentDao {
    
    /**
     * @return All currents from storage.
     */
    Collection<Current> findAll() throws DatabaseException;
    
    /**
     * @param id of the target current.
     * @return Current with the given id.
     * @throws ElementNotFoundException If the given id is not present in the database.
     */
    Current findById(int id) throws DatabaseException, ElementNotFoundException;
    
    /**
     * Add a new current to storage.
     * 
     * @param current Current that should be stored.
     * @return Newly generated current (also holding the correct id).
     * @throws SaveException If the current could not be inserted.
     */
    Current insert(Current current) throws SaveException;
    
    /**
     * Update an existing current in storage.
     * 
     * @param current Current that should be updated.
     * @throws ElementNotFoundException If the given current is not present in the database.
     * @throws SaveException If the current could not be updated.
     */
    void update(Current current) throws ElementNotFoundException, SaveException;

    /**
     * Add a current state to a target state.
     * 
     * @param target
     * @param current
     * @throws SaveException 
     */
    void addToTarget(final Target target, final Current current) throws SaveException;

    void removeFromTarget(Target target, Current current)
			throws DatabaseException;
    
    /**
     * 
     * @param target
     * @return All possible current states for the given target state.
     * @throws DatabaseException
     */
    Collection<Current> findByTarget(Target target) throws DatabaseException;
}
