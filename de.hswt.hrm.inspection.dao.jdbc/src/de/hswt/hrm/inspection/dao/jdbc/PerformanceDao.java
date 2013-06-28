package de.hswt.hrm.inspection.dao.jdbc;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

import de.hswt.hrm.catalog.dao.core.IActivityDao;
import de.hswt.hrm.catalog.dao.core.ICurrentDao;
import de.hswt.hrm.catalog.dao.core.ITargetDao;
import de.hswt.hrm.catalog.model.Activity;
import de.hswt.hrm.catalog.model.Current;
import de.hswt.hrm.catalog.model.Target;
import de.hswt.hrm.common.database.DatabaseFactory;
import de.hswt.hrm.common.database.JdbcUtil;
import de.hswt.hrm.common.database.NamedParameterStatement;
import de.hswt.hrm.common.database.SqlQueryBuilder;
import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.database.exception.ElementNotFoundException;
import de.hswt.hrm.common.database.exception.SaveException;
import de.hswt.hrm.inspection.dao.core.IPerformanceDao;
import de.hswt.hrm.inspection.model.Inspection;
import de.hswt.hrm.inspection.model.Performance;
import de.hswt.hrm.misc.priority.dao.core.IPriorityDao;
import de.hswt.hrm.misc.priority.model.Priority;
import de.hswt.hrm.scheme.dao.core.ISchemeComponentDao;
import de.hswt.hrm.scheme.model.SchemeComponent;

public class PerformanceDao implements IPerformanceDao {
	private final ISchemeComponentDao schemeComponentDao;
	private final ITargetDao targetDao;
	private final ICurrentDao currentDao;
	private final IActivityDao activityDao;
	private final IPriorityDao priorityDao;
	
	@Inject
	public PerformanceDao(final ITargetDao targetDao, final ISchemeComponentDao schemeComponentDao,
			final ICurrentDao currentDao, final IActivityDao activityDao, 
			final IPriorityDao priorityDao) {
		// TODO add log messages
		
		this.schemeComponentDao = schemeComponentDao;
		this.targetDao = targetDao;
		this.currentDao = currentDao;
		this.activityDao = activityDao;
		this.priorityDao = priorityDao;
	}
	
	@Override
	public Collection<Performance> findAll() throws DatabaseException {
        SqlQueryBuilder builder = new SqlQueryBuilder();
        builder.insert(TABLE_NAME,
        		Fields.ID,
				Fields.COMPONENT_FK, 
				Fields.TARGET_FK, 
				Fields.CURRENT_FK, 
				Fields.ACTIVITY_FK, 
				Fields.PRIORITY_FK);

        String query = builder.toString();

        try (Connection con = DatabaseFactory.getConnection()) {
            try (NamedParameterStatement stmt = NamedParameterStatement.fromConnection(con, query)) {

                ResultSet rs = stmt.executeQuery();
                Collection<Performance> performanceList = fromResultSet(rs);
                rs.close();

                return performanceList;
            }
        }
        catch (SQLException e) {
            throw new DatabaseException("Unexpected error.", e);
        }
    }
	
	@Override
	public Collection<Performance> findByInspection(Inspection inspection) throws DatabaseException {
		checkNotNull(inspection, "Inspection must not be null");
		checkState(inspection.getId() >= 0, "Inspection has a invalid ID.");
		
		SqlQueryBuilder builder = new SqlQueryBuilder();
        builder.insert(TABLE_NAME,
        		Fields.ID,
				Fields.COMPONENT_FK, 
				Fields.TARGET_FK, 
				Fields.CURRENT_FK, 
				Fields.ACTIVITY_FK, 
				Fields.PRIORITY_FK);
        builder.where(Fields.INSPECTION_FK);

        String query = builder.toString();

        try (Connection con = DatabaseFactory.getConnection()) {
            try (NamedParameterStatement stmt = NamedParameterStatement.fromConnection(con, query)) {
            	stmt.setParameter(Fields.INSPECTION_FK, inspection.getId());
            	
                ResultSet rs = stmt.executeQuery();
                Collection<Performance> performanceList = fromResultSet(rs);
                rs.close();

                return performanceList;
            }
        }
        catch (SQLException e) {
            throw new DatabaseException("Unexpected error.", e);
        }
	}
	
	@Override
	public Performance insert(final Performance performance) 
			throws SaveException, DatabaseException {
		
		checkState(performance.getActivity().getId() >= 0, "Activity has an invalid ID.");
		checkState(performance.getCurrent().getId() >= 0, "Current hat an invalid ID.");
		checkState(performance.getPriority().getId() >= 0, "Performance has an invalid ID.");
		checkState(performance.getSchemeComponent().getId() >= 0, "SchemeComponent has an invalid ID.");
		checkState(performance.getTarget().getId() >= 0, "Target has an invalid ID.");
		
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
				stmt.setParameter(Fields.COMPONENT_FK, performance.getSchemeComponent().getId());
				stmt.setParameter(Fields.TARGET_FK, performance.getTarget().getId());
				stmt.setParameter(Fields.CURRENT_FK, performance.getCurrent().getId());
				stmt.setParameter(Fields.ACTIVITY_FK, performance.getActivity().getId());
				stmt.setParameter(Fields.PRIORITY_FK, performance.getPriority().getId());
				
				int affected = stmt.executeUpdate();
				
				 if (affected != 1) {
                    throw new SaveException();
                }

                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int id = generatedKeys.getInt(1);
                        
                        return new Performance(
                        		id, 
                        		performance.getSchemeComponent(),
                        		performance.getTarget(), 
                        		performance.getCurrent(), 
                        		performance.getActivity(), 
                        		performance.getPriority());
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
	
	private Collection<Performance> fromResultSet(ResultSet rs) 
			throws SQLException, ElementNotFoundException, DatabaseException {
		
		checkNotNull(rs, "Result must not be null.");
        
		Collection<Performance> performanceList = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt(Fields.ID);
            int componentId = JdbcUtil.getId(rs, Fields.COMPONENT_FK);
            checkState(componentId >= 0, "Invalid component ID retrieved from database.");
            SchemeComponent component = schemeComponentDao.findById(componentId);
			int targetId = JdbcUtil.getId(rs, Fields.TARGET_FK);
			checkState(targetId >= 0, "Invalid target ID retrieved from database.");
			Target target = targetDao.findById(targetId);
			int currentId = JdbcUtil.getId(rs, Fields.CURRENT_FK);
			checkState(currentId >= 0, "Invalid current ID retrieved from database.");
			Current current = currentDao.findById(currentId);
			int activityId = JdbcUtil.getId(rs, Fields.ACTIVITY_FK); 
			checkState(activityId >= 0, "Invalid activity ID retrieved from database.");
			Activity activity = activityDao.findById(activityId);
			int priorityId = JdbcUtil.getId(rs, Fields.PRIORITY_FK);
			checkState(priorityId >= 0, "Invalid priority ID retrieved from database.");
			Priority priority = priorityDao.findById(priorityId);
			
			performanceList.add(new Performance(
					id, 
					component, 
					target, 
					current, 
					activity, 
					priority));
        }
        
        return performanceList;
	}
	
	private static final String TABLE_NAME = "";
	
	private static class Fields {
		private static final String ID = "Performance_ID";
		private static final String INSPECTION_FK = "Performance_Inspection_FK";
		private static final String COMPONENT_FK = "Performance_Component_FK";
		private static final String TARGET_FK = "Performance_Target_FK";
		private static final String CURRENT_FK = "Performance_Current_FK";
		private static final String ACTIVITY_FK = "Performance_Activity_FK";
		private static final String PRIORITY_FK = "Performance_Priority_FK";
	}
}
