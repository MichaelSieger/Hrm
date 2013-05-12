package de.hswt.hrm.scheme.service;

import de.hswt.hrm.scheme.dao.core.ICategoryDao;
import de.hswt.hrm.scheme.dao.jdbc.CategoryDao;

public class CategoryService {

    public CategoryService() { };
    
    private static ICategoryDao dao = new CategoryDao();

}
