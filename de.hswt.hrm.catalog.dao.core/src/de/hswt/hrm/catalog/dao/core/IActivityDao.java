package de.hswt.hrm.catalog.dao.core;

import java.util.Collection;

import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.database.exception.ElementNotFoundException;
import de.hswt.hrm.common.database.exception.SaveException;
import de.hswt.hrm.catalog.model.Activity;
import de.hswt.hrm.catalog.model.Current;

/**
 * Defines all the public methods to interact with the storage system for activitys.
 */
public interface IActivityDao {
    
    /**
     * @return All activitys from storage.
     */
    Collection<Activity> findAll() throws DatabaseException;
    
    /**
     * @param id of the target activity.
     * @return Activity with the given id.
     * @throws ElementNotFoundException If the given id is not present in the database.
     */
    Activity findById(int id) throws DatabaseException, ElementNotFoundException;
    
    /**
     * Add a new activity to storage.
     * 
     * @param activity Activity that should be stored.
     * @return Newly generated activity (also holding the correct id).
     * @throws SaveException If the activity could not be inserted.
     */
    Activity insert(Activity activity) throws SaveException;
    
    /**
     * Update an existing activity in storage.
     * 
     * @param activity Activity that should be updated.
     * @throws ElementNotFoundException If the given activity is not present in the database.
     * @throws SaveException If the activity could not be updated.
     */
    void update(Activity activity) throws ElementNotFoundException, SaveException;

    /**
     * Add an activity state to a current state.
     * 
     * @param current
     * @param activity
     * @throws SaveException 
     */
    void addToCurrent(final Current current, final Activity activity) throws SaveException;
    
    /**
     * 
     * @param current
     * @return All possible activity states for the given current state.
     * @throws DatabaseException
     */
	Collection<Activity> findByCurrent(Current current)
			throws DatabaseException;
}
