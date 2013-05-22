package de.hswt.hrm.scheme.dao.core;

import java.util.Collection;

import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.database.exception.ElementNotFoundException;
import de.hswt.hrm.common.database.exception.SaveException;
import de.hswt.hrm.component.model.Category;

/**
 * Defines all the public methods to interact with the storage system for categorys.
 */
public interface ICategoryDao {
    
    /**
     * @return All categorys from storage.
     */
    Collection<Category> findAll() throws DatabaseException;
    
    /**
     * @param id of the target category.
     * @return Category with the given id.
     * @throws ElementNotFoundException If the given id is not present in the database.
     */
    Category findById(int id) throws DatabaseException, ElementNotFoundException;
    
    /**
     * Add a new category to storage.
     * 
     * @param category Category that should be stored.
     * @return Newly generated category (also holding the correct id).
     * @throws SaveException If the category could not be inserted.
     */
    Category insert(Category category) throws SaveException;
    
    /**
     * Update an existing category in storage.
     * 
     * @param category Category that should be updated.
     * @throws ElementNotFoundException If the given category is not present in the database.
     * @throws SaveException If the category could not be updated.
     */
    void update(Category category) throws ElementNotFoundException, SaveException;
}
