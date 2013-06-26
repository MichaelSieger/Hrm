package de.hswt.hrm.inspection.dao.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import de.hswt.hrm.common.database.DatabaseFactory;
import de.hswt.hrm.common.database.NamedParameterStatement;
import de.hswt.hrm.common.database.SqlQueryBuilder;
import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.database.exception.SaveException;
import de.hswt.hrm.inspection.dao.core.IPerformanceDao;
import de.hswt.hrm.inspection.model.Performance;

public class PerformanceDao implements IPerformanceDao {
	
	public Performance insert(final Performance performance) 
			throws SaveException, DatabaseException {
		
		SqlQueryBuilder builder = new SqlQueryBuilder();
		builder.insert(TABLE_NAME,
				Fields.COMPONENT_FK, 
				Fields.TARGET_FK, 
				Fields.CURRENT_FK, 
				Fields.ACTIVITY_FK, 
				Fields.PRIORITY_FK);
		
		String query = builder.toString();
		
		try (Connection con = DatabaseFactory.getConnection()) {
			try (NamedParameterStatement stmt = NamedParameterStatement.fromConnection(con, query)) {
				stmt.setParameter(Fields.COMPONENT_FK, performance.getSchemeComponentId());
				stmt.setParameter(Fields.TARGET_FK, performance.getTargetId());
				stmt.setParameter(Fields.CURRENT_FK, performance.getCurrentId());
				stmt.setParameter(Fields.ACTIVITY_FK, performance.getActivityId());
				stmt.setParameter(Fields.PRIORITY_FK, performance.getPriorityId());
				
				int affected = stmt.executeUpdate();
				
				 if (affected != 1) {
                    throw new SaveException();
                }

                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int id = generatedKeys.getInt(1);
                        
                        return new Performance(
                        		id, 
                        		performance.getSchemeComponentId(),
                        		performance.getTargetId(), 
                        		performance.getCurrentId(), 
                        		performance.getActivityId(), 
                        		performance.getPriorityId());
                    }
                    else {
                    	throw new SaveException("Could not retrieve generated ID.");
                    }
                }
			}
		}
		catch (SQLException e) {
			throw new DatabaseException("Unexpected error.", e);
		}
	}
	
	private static final String TABLE_NAME = "";
	
	private static class Fields {
		private static final String ID = "Performance_ID";
		private static final String COMPONENT_FK = "Performance_Component_FK";
		private static final String TARGET_FK = "Performance_Target_FK";
		private static final String CURRENT_FK = "Performance_Current_FK";
		private static final String ACTIVITY_FK = "Performance_Activity_FK";
		private static final String PRIORITY_FK = "Performance_Priority_FK";
	}
}
