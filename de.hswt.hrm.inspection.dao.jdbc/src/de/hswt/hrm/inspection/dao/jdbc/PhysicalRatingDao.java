package de.hswt.hrm.inspection.dao.jdbc;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

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
import de.hswt.hrm.inspection.dao.core.IPhysicalRatingDao;
import de.hswt.hrm.inspection.model.PhysicalRating;
import de.hswt.hrm.scheme.model.Scheme;

public class PhysicalRatingDao implements IPhysicalRatingDao {

	private final IComponentDao componentDao;
	
	// TODO: add LOG messages
	public PhysicalRatingDao(final IComponentDao componentDao) {
		checkNotNull(componentDao, "ComponentDao not properly injected to PhysicalRatingDao.");
		
		this.componentDao = componentDao;
	}
	
	@Override
	public Collection<PhysicalRating> findAll() throws DatabaseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PhysicalRating findById(int id) throws DatabaseException,
			ElementNotFoundException {
		
		checkArgument(id >= 0, "ID must be non negative.");
		
		SqlQueryBuilder builder = new SqlQueryBuilder();
		builder.select(TABLE_NAME, Fields.ID, Fields.RATING, Fields.NOTE, Fields.COMPONENT_FK,
				Fields.REPORT_FK);
		builder.where(Fields.ID);
		
		String query = builder.toString();
		
		try (Connection con = DatabaseFactory.getConnection()) {
			try (NamedParameterStatement stmt = NamedParameterStatement.fromConnection(con, query)) {
				stmt.setParameter(Fields.ID, id);
				
				ResultSet rs = stmt.executeQuery();
				Collection<PhysicalRating> ratings = fromResultSet(rs);
				rs.close();
				
				if (ratings.isEmpty()) {
					throw new ElementNotFoundException();
				}
				else if (ratings.size() > 1) {
					throw new DatabaseException("ID is not unique.");
				}
				
				return ratings.iterator().next();
			}
		}
		catch (SQLException e) {
			throw new DatabaseException("Unknown error.", e);
		}
	}

	@Override
	public PhysicalRating insert(PhysicalRating physical) throws SaveException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(PhysicalRating physical)
			throws ElementNotFoundException, SaveException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Collection<PhysicalRating> findByScheme(Scheme scheme) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private Collection<PhysicalRating> fromResultSet(final ResultSet rs) {
		// Add component here
		throw new NotImplementedException();
	}
	
	private static final String TABLE_NAME = "Component_Physical_Rating";
	
	private static final class Fields {
		private static final String ID = "";
		private static final String RATING = "";
		private static final String NOTE = "";
		private static final String COMPONENT_FK = "";
		private static final String REPORT_FK = "";
	}
}
