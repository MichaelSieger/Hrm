package de.hswt.hrm.inspection.dao.jdbc;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkArgument;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import de.hswt.hrm.common.database.DatabaseFactory;
import de.hswt.hrm.common.database.NamedParameterStatement;
import de.hswt.hrm.common.database.SqlQueryBuilder;
import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.database.exception.ElementNotFoundException;
import de.hswt.hrm.common.database.exception.SaveException;
import de.hswt.hrm.common.exception.NotImplementedException;
import de.hswt.hrm.component.dao.core.IComponentDao;
import de.hswt.hrm.inspection.dao.core.IBiologicalRatingDao;
import de.hswt.hrm.inspection.model.BiologicalRating;
import de.hswt.hrm.inspection.model.Inspection;

public class BiologicalRatingDao implements IBiologicalRatingDao {
	private final IComponentDao componentDao;

	// TODO: add log messages
	public BiologicalRatingDao(final IComponentDao componentDao) {
		checkNotNull(componentDao, "ComponentDao not properly injected to BiologicalRatingDao");
		
		this.componentDao = componentDao;
	}
	
	
	@Override
	public Collection<BiologicalRating> findAll() throws DatabaseException {
		throw new NotImplementedException();
	}

	@Override
	public BiologicalRating findById(int id) throws DatabaseException,
			ElementNotFoundException {
		throw new NotImplementedException();
	}
	
	@Override
	public Collection<BiologicalRating> findByInspection(final Inspection inspection) 
			throws DatabaseException {
		
		checkNotNull(inspection, "Inspection is mandatory.");
		checkArgument(inspection.getId() >= 0, "Inspection must have a valid ID.");
		
		SqlQueryBuilder builder = new SqlQueryBuilder();
		// FIXME: Check if we should resolve the flag using a join
		builder.select(TABLE_NAME, Fields.ID, Fields.BACTERIA, Fields.RATING, Fields.QUANTIFIER,
				Fields.COMMENT, Fields.FK_COMPONENT, Fields.FK_REPORT, Fields.FK_FLAG);
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
		} catch (SQLException e) {
			throw new DatabaseException("Unexpected error.", e);
		}
	}

	@Override
	public BiologicalRating insert(BiologicalRating biological)
			throws SaveException {
		throw new NotImplementedException();
	}

	@Override
	public void update(BiologicalRating biological)
			throws ElementNotFoundException, SaveException {
		throw new NotImplementedException();
	}
	
	private Collection<BiologicalRating> fromResultSet(final ResultSet rs) {
		throw new NotImplementedException();
	}
	
	private static final String TABLE_NAME = "Biological_Rating";
	private static final class Fields {
		public static final String ID = "";
		public static final String BACTERIA = "";
		public static final String RATING = "";
		public static final String QUANTIFIER = "";
		public static final String COMMENT = "";
		public static final String FK_COMPONENT = "";
		public static final String FK_REPORT = "";
		public static final String FK_FLAG = "";
	}
}
