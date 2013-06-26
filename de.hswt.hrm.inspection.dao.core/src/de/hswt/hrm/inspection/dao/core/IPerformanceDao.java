package de.hswt.hrm.inspection.dao.core;

import java.util.Collection;

import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.database.exception.SaveException;
import de.hswt.hrm.inspection.model.Performance;

public interface IPerformanceDao {

	Collection<Performance> findAll() throws DatabaseException;
	
	Performance insert(Performance performance) 
			throws SaveException, DatabaseException;

}
