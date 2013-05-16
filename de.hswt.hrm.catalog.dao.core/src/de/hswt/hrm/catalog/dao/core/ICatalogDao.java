package de.hswt.hrm.catalog.dao.core;

import java.util.Collection;

import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.database.exception.ElementNotFoundException;
import de.hswt.hrm.common.database.exception.SaveException;
import de.hswt.hrm.catalog.model.Catalog;

/**
 * Defines all the public methods to interact with the storage system for activitys.
 */
public interface ICatalogDao {
    
    /**
     * @return All catalogs from storage.
     */
    Collection<Catalog> findAll() throws DatabaseException;
    
    /**
     * @param id of the target catalog.
     * @return Catalog with the given id.
     * @throws ElementNotFoundException If the given id is not present in the database.
     */
    Catalog findById(int id) throws DatabaseException, ElementNotFoundException;
    
    /**
     * Add a new catalog to storage.
     * 
     * @param catalog Catalog that should be stored.
     * @return Newly generated catalog (also holding the correct id).
     * @throws SaveException If the catalog could not be inserted.
     */
    Catalog insert(Catalog catalog) throws SaveException;
    
    /**
     * Update an existing catalog in storage.
     * 
     * @param catalog Catalog that should be updated.
     * @throws ElementNotFoundException If the given catalog is not present in the database.
     * @throws SaveException If the catalog could not be updated.
     */
    void update(Catalog catalog) throws ElementNotFoundException, SaveException;
}
