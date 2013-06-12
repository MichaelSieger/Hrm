package de.hswt.hrm.catalog.dao.core;

import java.util.Collection;

import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.database.exception.ElementNotFoundException;
import de.hswt.hrm.common.database.exception.SaveException;
import de.hswt.hrm.catalog.model.Catalog;
import de.hswt.hrm.catalog.model.Target;

/**
 * Defines all the public methods to interact with the storage system for targets.
 */
public interface ITargetDao {
    
    /**
     * @return All targets from storage.
     */
    Collection<Target> findAll() throws DatabaseException;
    
    /**
     * @param id of the target target.
     * @return Target with the given id.
     * @throws ElementNotFoundException If the given id is not present in the database.
     */
    Target findById(int id) throws DatabaseException, ElementNotFoundException;
    
    Collection<Target> findByCatalog(Catalog catalog) throws DatabaseException;
    
    /**
     * Add a new target to storage.
     * 
     * @param target Target that should be stored.
     * @return Newly generated target (also holding the correct id).
     * @throws SaveException If the target could not be inserted.
     */
    Target insert(Target target) throws SaveException;
    
    void addToCatalog(Catalog catalog, Target target) throws SaveException;

    void removeFromCatalog(Catalog catalog, Target target)
			throws DatabaseException;
    
    /**
     * Update an existing target in storage.
     * 
     * @param target Target that should be updated.
     * @throws ElementNotFoundException If the given target is not present in the database.
     * @throws SaveException If the target could not be updated.
     */
    void update(Target target) throws ElementNotFoundException, SaveException;
}
