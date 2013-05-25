package de.hswt.hrm.component.service;

import java.util.Collection;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Creatable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.database.exception.ElementNotFoundException;
import de.hswt.hrm.common.database.exception.SaveException;
import de.hswt.hrm.component.dao.core.ICategoryDao;
import de.hswt.hrm.component.model.Category;

/**
 * Service class which should be used to interact with
 * the storage system for categories.
 */
@Creatable
public class CategoryService {
    private final static Logger LOG = LoggerFactory.getLogger(CategoryService.class);
    private final ICategoryDao categoryDao;
    
    @Inject
    public CategoryService(final ICategoryDao categoryDao) { 
        this.categoryDao = categoryDao;
    };
    
    /**
     * @return All categories from storage
     * @throws DatabaseException
     */
    public Collection<Category> findAll() throws DatabaseException {
        return categoryDao.findAll();
    }
    
    /**
     * @param id of the target category
     * @return category with the given id
     * @throws DatabaseException
     */
    public Category findById(int id) throws DatabaseException {
        return categoryDao.findById(id);
    }
    
    /**
     * Add a new category to storage
     * 
     * @param category Category that should be stored
     * @return the created category
     * @throws SaveException
     */
    public Category insert(Category category) throws SaveException {
        return categoryDao.insert(category);
    }
    
    /**
     * Update an existing category in storage
     * 
     * @param category Category that should be updated
     * @throws ElementNotFoundException
     * @throws SaveException
     */
    public void update(Category category) throws ElementNotFoundException, SaveException {
        categoryDao.update(category);
    }
    
    /**
     * Refreshes a given category with values from the database
     * 
     * @param category
     * @throws ElementNotFoundException
     * @throws DatabaseException
     */
    public void refresh(Category category) throws ElementNotFoundException, DatabaseException {
        Category fromDB = categoryDao.findById(category.getId());
        
        category.setName(fromDB.getName());
        category.setDefaultQuantifier(fromDB.getDefaultQuantifier());
        category.setDefaultBoolRating(fromDB.getDefaultBoolRating());
        category.setWidth(fromDB.getWidth());
        category.setHeight(fromDB.getHeight());
    }

}
