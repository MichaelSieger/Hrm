package de.hswt.hrm.component.service;

import java.util.Collection;

import org.eclipse.e4.core.di.annotations.Creatable;

import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.component.dao.core.IComponentDao;
import de.hswt.hrm.component.dao.jdbc.ComponentDao;
import de.hswt.hrm.component.model.Component;

/**
 * Service class which should be used to interact with
 * the storage system for components.
 */
@Creatable
public class ComponentService {
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
