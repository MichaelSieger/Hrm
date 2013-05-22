package de.hswt.hrm.scheme.service;

import java.util.Collection;

import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.database.exception.ElementNotFoundException;
import de.hswt.hrm.common.database.exception.SaveException;
import de.hswt.hrm.component.model.Category;
import de.hswt.hrm.scheme.dao.core.ICategoryDao;
import de.hswt.hrm.scheme.dao.jdbc.CategoryDao;

public class CategoryService {
    
    /**
     * Service class which schould be used to interact with
     * the storage system for categories
     */

    public CategoryService() { };
    
    private static ICategoryDao dao = new CategoryDao();
    
    /**
     * @return All categories from storage
     * @throws DatabaseException
     */
    public static Collection<Category> findAll() throws DatabaseException {
        return dao.findAll();
    }
    
    /**
     * @param id of the target category
     * @return category with the given id
     * @throws DatabaseException
     */
    public static Category findById(int id) throws DatabaseException {
        return dao.findById(id);
    }
    
    /**
     * Add a new category to storage
     * 
     * @param category Category that should be stored
     * @return the created category
     * @throws SaveException
     */
    public static Category insert(Category category) throws SaveException {
        return dao.insert(category);
    }
    
    /**
     * Update an existing category in storage
     * 
     * @param category Category that should be updated
     * @throws ElementNotFoundException
     * @throws SaveException
     */
    public static void update(Category category) throws ElementNotFoundException, SaveException {
        dao.update(category);
    }
    
    /**
     * Refreshes a given category with values from the database
     * 
     * @param category
     * @throws ElementNotFoundException
     * @throws DatabaseException
     */
    public static void refresh(Category category) throws ElementNotFoundException, DatabaseException {
        Category fromDB = dao.findById(category.getId());
        
        category.setName(fromDB.getName());
        category.setDefaultQuantifier(fromDB.getDefaultQuantifier());
        category.setDefaultBoolRating(fromDB.getDefaultBoolRating());
        category.setWidth(fromDB.getWidth());
        category.setHeight(fromDB.getHeight());
    }

}
