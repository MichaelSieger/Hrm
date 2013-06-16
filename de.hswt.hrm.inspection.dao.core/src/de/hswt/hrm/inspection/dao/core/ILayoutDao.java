package de.hswt.hrm.inspection.dao.core;

import java.util.Collection;

import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.database.exception.ElementNotFoundException;
import de.hswt.hrm.common.database.exception.SaveException;
import de.hswt.hrm.inspection.model.Layout;

/**
 * Defines all the public methods to interact with the storage system for layouts.
 */
public interface ILayoutDao {
    
    /**
     * @return All activitys from storage.
     */
    Collection<Layout> findAll() throws DatabaseException;
    
    /**
     * @param id of the layout.
     * @return Layout with the given id.
     * @throws ElementNotFoundException If the given id is not present in the database.
     */
    Layout findById(int id) throws DatabaseException, ElementNotFoundException;
    
    /**
     * Add a new layout to storage.
     * 
     * @param layout Layout that should be stored.
     * @return Newly generated layout (also holding the correct id).
     * @throws SaveException If the layout could not be inserted.
     */
    Layout insert(Layout layout) throws SaveException;
    
    /**
     * Update an existing layout in storage.
     * 
     * @param layout Layout that should be updated.
     * @throws ElementNotFoundException If the given layout is not present in the database.
     * @throws SaveException If the layout could not be updated.
     */
    void update(Layout layout) throws ElementNotFoundException, SaveException;

 
}
