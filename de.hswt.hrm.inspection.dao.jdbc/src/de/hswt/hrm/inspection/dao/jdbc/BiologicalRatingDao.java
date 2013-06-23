package de.hswt.hrm.inspection.dao.jdbc;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hswt.hrm.common.database.DatabaseFactory;
import de.hswt.hrm.common.database.JdbcUtil;
import de.hswt.hrm.common.database.NamedParameterStatement;
import de.hswt.hrm.common.database.SqlQueryBuilder;
import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.database.exception.ElementNotFoundException;
import de.hswt.hrm.common.database.exception.SaveException;
import de.hswt.hrm.common.exception.NotImplementedException;
import de.hswt.hrm.component.dao.core.IComponentDao;
import de.hswt.hrm.component.model.Component;
import de.hswt.hrm.inspection.dao.core.IBiologicalRatingDao;
import de.hswt.hrm.inspection.dao.core.IInspectionDao;
import de.hswt.hrm.inspection.model.BiologicalRating;
import de.hswt.hrm.inspection.model.Inspection;

public class BiologicalRatingDao implements IBiologicalRatingDao {
	private final static Logger LOG = LoggerFactory.getLogger(BiologicalRatingDao.class);
	private final IInspectionDao inspectionDao;
	private final IComponentDao componentDao;

    public BiologicalRatingDao(final IInspectionDao inspectionDao, 
    		final IComponentDao componentDao) {
    	
    	checkNotNull(inspectionDao, "InspectionDao not properly injected to BiologicalRatingDao");
        checkNotNull(componentDao, "ComponentDao not properly injected to BiologicalRatingDao");

        this.inspectionDao = inspectionDao;
        LOG.debug("InspectionDao injected into BiologicalRatingDao.");
        this.componentDao = componentDao;
        LOG.debug("ComponentDao injected into BiologicalRatingDao.");
    }

    @Override
    public Collection<BiologicalRating> findAll() throws DatabaseException {
        SqlQueryBuilder builder = new SqlQueryBuilder();
        builder.select(TABLE_NAME, TABLE_NAME + "." + Fields.ID,
                TABLE_NAME + "." + Fields.BACTERIA, TABLE_NAME + "." + Fields.RATING, TABLE_NAME
                        + "." + Fields.QUANTIFIER, TABLE_NAME + "." + Fields.COMMENT, TABLE_NAME
                        + "." + Fields.FK_COMPONENT, TABLE_NAME + "." + Fields.FK_REPORT,
                TABLE_NAME + "." + Fields.FK_FLAG, FLAG_TABLE_NAME + "." + FlagFields.NAME);
        builder.join(FLAG_TABLE_NAME, Fields.FK_FLAG, FlagFields.ID);

        String query = builder.toString();

        try (Connection con = DatabaseFactory.getConnection()) {
            try (NamedParameterStatement stmt = NamedParameterStatement.fromConnection(con, query)) {

                ResultSet rs = stmt.executeQuery();
                Collection<BiologicalRating> ratings = fromResultSet(rs);
                rs.close();

                return ratings;
            }
        }
        catch (SQLException e) {
            throw new DatabaseException("Unexpected error.", e);
        }
    }

    @Override
    public BiologicalRating findById(final int id) throws DatabaseException,
            ElementNotFoundException {

        checkArgument(id >= 0, "ID must be non negative.");

        SqlQueryBuilder builder = new SqlQueryBuilder();
        builder.select(TABLE_NAME, TABLE_NAME + "." + Fields.ID,
                TABLE_NAME + "." + Fields.BACTERIA, TABLE_NAME + "." + Fields.RATING, TABLE_NAME
                        + "." + Fields.QUANTIFIER, TABLE_NAME + "." + Fields.COMMENT, TABLE_NAME
                        + "." + Fields.FK_COMPONENT, TABLE_NAME + "." + Fields.FK_REPORT,
                TABLE_NAME + "." + Fields.FK_FLAG, FLAG_TABLE_NAME + "." + FlagFields.NAME);
        builder.join(FLAG_TABLE_NAME, Fields.FK_FLAG, FlagFields.ID);
        builder.where(Fields.ID);

        String query = builder.toString();

        try (Connection con = DatabaseFactory.getConnection()) {
            try (NamedParameterStatement stmt = NamedParameterStatement.fromConnection(con, query)) {
                stmt.setParameter(Fields.ID, id);

                ResultSet rs = stmt.executeQuery();
                Collection<BiologicalRating> ratings = fromResultSet(rs);
                rs.close();

                if (ratings.isEmpty()) {
                    throw new ElementNotFoundException();
                }
                else if (ratings.size() > 1) {
                    throw new DatabaseException("ID '" + id + "' is not unique.");
                }

                return ratings.iterator().next();
            }
        }
        catch (SQLException e) {
            throw new DatabaseException("Unexpected error.", e);
        }
    }

    @Override
    public Collection<BiologicalRating> findByInspection(final Inspection inspection)
            throws DatabaseException {

        checkNotNull(inspection, "Inspection is mandatory.");
        checkArgument(inspection.getId() >= 0, "Inspection must have a valid ID.");

        SqlQueryBuilder builder = new SqlQueryBuilder();
        builder.select(TABLE_NAME, TABLE_NAME + "." + Fields.ID,
                TABLE_NAME + "." + Fields.BACTERIA, TABLE_NAME + "." + Fields.RATING, TABLE_NAME
                        + "." + Fields.QUANTIFIER, TABLE_NAME + "." + Fields.COMMENT, TABLE_NAME
                        + "." + Fields.FK_COMPONENT, TABLE_NAME + "." + Fields.FK_REPORT,
                TABLE_NAME + "." + Fields.FK_FLAG, FLAG_TABLE_NAME + "." + FlagFields.NAME);
        builder.join(FLAG_TABLE_NAME, Fields.FK_FLAG, FlagFields.ID);
        builder.where(Fields.FK_REPORT);

        String query = builder.toString();

        try (Connection con = DatabaseFactory.getConnection()) {
            try (NamedParameterStatement stmt = NamedParameterStatement.fromConnection(con, query)) {
                stmt.setParameter(Fields.FK_REPORT, inspection.getId());

                ResultSet rs = stmt.executeQuery();
                Collection<BiologicalRating> ratings = fromResultSet(rs);
                rs.close();
                return ratings;
            }
        }
        catch (SQLException e) {
            throw new DatabaseException("Unexpected error.", e);
        }
    }

    @Override
    public BiologicalRating insert(BiologicalRating biological) throws DatabaseException {
    	checkNotNull(biological, "BiologicalRating must not be null.");
    	checkState(biological.getComponent().getId() >= 0, "Component must have a valid ID.");
    	checkState(biological.getInspection().getId() >= 0, "Inspection must have a valid ID.");
    	
        SqlQueryBuilder builder = new SqlQueryBuilder();
        builder.insert(
        		TABLE_NAME, 
		        Fields.BACTERIA, 
		        Fields.RATING, 
		        Fields.QUANTIFIER, 
		        Fields.COMMENT, 
		        Fields.FK_COMPONENT,
		        Fields.FK_REPORT,
		        Fields.FK_FLAG);

        String query = builder.toString();
        
        try (Connection con = DatabaseFactory.getConnection()) {
        	try (NamedParameterStatement stmt = NamedParameterStatement.fromConnection(con, query)) {
        		
        		stmt.setParameter(Fields.BACTERIA, biological.getBacteriaCount());
        		stmt.setParameter(Fields.RATING, biological.getRating());
        		stmt.setParameter(Fields.QUANTIFIER, biological.getQuantifier());
        		stmt.setParameter(Fields.COMMENT, biological.getComment());
        		stmt.setParameter(Fields.FK_COMPONENT, biological.getComponent().getId());
        		stmt.setParameter(Fields.FK_REPORT, biological.getInspection().getId());
        		stmt.setParameter(Fields.FK_FLAG, getFlagId(con, biological.getFlag()));
        	
        		int affectedRows = stmt.executeUpdate();
                if (affectedRows != 1) {
                    throw new SaveException();
                }

                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int id = generatedKeys.getInt(1);

                        // Create new biological rating with id
                        BiologicalRating inserted = new BiologicalRating(
                        		id,
                        		biological.getInspection(), 
                        		biological.getComponent(), 
                        		biological.getBacteriaCount(),
                        		biological.getRating(),
                        		biological.getQuantifier(),
                        		biological.getComment(),
                        		biological.getFlag());

                        return inserted;
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

    @Override
    public void update(BiologicalRating biological) throws ElementNotFoundException, SaveException {
        throw new NotImplementedException();
    }

    /**
     * All statements should join the flag table to be able to parse it
     * correctly here...
     * @param rs
     * @return
     * @throws SQLException 
     * @throws DatabaseException 
     * @throws ElementNotFoundException 
     */
    private Collection<BiologicalRating> fromResultSet(final ResultSet rs) 
    		throws SQLException, ElementNotFoundException, DatabaseException {
    	
        checkNotNull(rs, "ResultSet must not be null.");
        
        Collection<BiologicalRating> ratingList = new ArrayList<>();

        while (rs.next()) {
            int id = rs.getInt(Fields.ID);
            int bacteria = rs.getInt(Fields.BACTERIA);
            int rating = rs.getInt(Fields.RATING);
            int quantifier = rs.getInt(Fields.QUANTIFIER);
            String comment = rs.getString(Fields.COMMENT);
            int componentId = JdbcUtil.getId(rs, Fields.FK_COMPONENT);
            checkState(componentId >= 0, "Invalid component ID returned from database");
            Component component = componentDao.findById(componentId);
            int inspectionId = JdbcUtil.getId(rs, Fields.FK_REPORT);
            checkState(inspectionId >= 0, "Invalid report ID returned from database.");
            Inspection inspection = inspectionDao.findById(inspectionId);
            // Should be added through a join
            String flag = rs.getString(FlagFields.NAME);
            
            BiologicalRating biological = new BiologicalRating(
            		id,
            		inspection,
            		component,
            		bacteria,
            		rating,
            		quantifier,
            		comment,
            		flag);
            
            ratingList.add(biological);
        }

        return ratingList;
    }
    
    private int getFlagId(final Connection con, final String name) throws SQLException {
    	SqlQueryBuilder builder = new SqlQueryBuilder();
    	builder.select(FLAG_TABLE_NAME, FlagFields.ID);
    	builder.where(FlagFields.NAME);

    	try (NamedParameterStatement stmt = 
    			NamedParameterStatement.fromConnection(con, builder.toString())) {
    		
    		stmt.setParameter(FlagFields.NAME, name);
    		
    		ResultSet rs = stmt.executeQuery();
    		if (!rs.next()) {
    			throw new IllegalStateException("Could not find flag '" + name + "' in database.");
    		}
    		
    		int id = rs.getInt(FlagFields.ID);
    		rs.close();
    		
    		return id;
    	}
    }

    private static final String TABLE_NAME = "Biological_Rating";

    private static final class Fields {
        public static final String ID = "Biological_Rating_ID";
        public static final String BACTERIA = "Biological_Rating_Bacteria_Count";
        public static final String RATING = "Biological_Rating_Rating";
        public static final String QUANTIFIER = "Biological_Rating_Quantifier";
        public static final String COMMENT = "Biological_Rating_Comment";
        public static final String FK_COMPONENT = "Biological_Rating_Component_FK";
        public static final String FK_REPORT = "Biological_Rating_Report_FK";
        public static final String FK_FLAG = "Biological_Rating_Flag_FK";
    }

    private static final String FLAG_TABLE_NAME = "Biological_Flag";

    private static final class FlagFields {
        public static final String ID = "Flag_ID";
        public static final String NAME = "Flag_Name";
    }
}
