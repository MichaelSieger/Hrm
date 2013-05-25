package de.hswt.hrm.component.service;

import java.util.Collection;

import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.component.dao.core.IComponentDao;
import de.hswt.hrm.component.dao.jdbc.ComponentDao;
import de.hswt.hrm.component.model.Component;

public class ComponentService {
	
    /**
     * Service class which schould be used to interact with
     * the storage system for components
     */
	
	private static IComponentDao dao = new ComponentDao();
	
	public ComponentService(){}
	
    /**
     * @return All components from storage
     * @throws DatabaseException
     */
    public static Collection<Component> findAll() throws DatabaseException {
        return dao.findAll();
    }

}
