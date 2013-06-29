package de.hswt.hrm.photo.dao.jdbc;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.dbutils.DbUtils;

import de.hswt.hrm.common.database.DatabaseFactory;
import de.hswt.hrm.common.database.DatabaseUtil;
import de.hswt.hrm.common.database.NamedParameterStatement;
import de.hswt.hrm.common.database.SqlQueryBuilder;
import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.database.exception.ElementNotFoundException;
import de.hswt.hrm.common.database.exception.SaveException;
import de.hswt.hrm.photo.dao.core.IPhotoDao;
import de.hswt.hrm.photo.model.Photo;

public class PhotoDao implements IPhotoDao {

    @Override
    public Collection<Photo> findAll() throws DatabaseException {
        SqlQueryBuilder builder = new SqlQueryBuilder();
        builder.select(TABLE_NAME, Fields.ID, Fields.BLOB, Fields.NAME, Fields.LABEL);

        final String query = builder.toString();

        try (Connection con = DatabaseFactory.getConnection()) {
            try (NamedParameterStatement stmt = NamedParameterStatement.fromConnection(con, query)) {
                ResultSet result = stmt.executeQuery();

                Collection<Photo> photos = fromResultSet(result);
                DbUtils.closeQuietly(result);

                return photos;
            }
        }
        catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public Photo findById(int id) throws DatabaseException, ElementNotFoundException {
        checkArgument(id >= 0, "Id must not be negative.");

        SqlQueryBuilder builder = new SqlQueryBuilder();
        builder.select(TABLE_NAME, Fields.ID, Fields.BLOB, Fields.NAME, Fields.LABEL);
        builder.where(Fields.ID);

        final String query = builder.toString();

        try (Connection con = DatabaseFactory.getConnection()) {
            try (NamedParameterStatement stmt = NamedParameterStatement.fromConnection(con, query)) {
                stmt.setParameter(Fields.ID, id);
                ResultSet result = stmt.executeQuery();

                Collection<Photo> photos = fromResultSet(result);
                DbUtils.closeQuietly(result);

                if (photos.size() < 1) {
                    throw new ElementNotFoundException();
                }
                else if (photos.size() > 1) {
                    throw new DatabaseException("ID '" + id + "' is not unique.");
                }

                return photos.iterator().next();
            }
        }
        catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }
    
    @Override
    public void addPhoto(final int performanceId, final Photo photo)
    		throws SaveException, DatabaseException {
    	
    	checkNotNull(photo, "Photo must not be null.");
    	checkArgument(performanceId >= 0, "Invalid ID given.");
    	checkState(photo.getId() >= 0, "Photo must have a valid ID.");
    	
    	SqlQueryBuilder builder = new SqlQueryBuilder();
    	builder.insert(PERFOMANCE_CROSS_TABLE, 
    			PerfCrossFields.FK_PERFORMANCE, 
    			PerfCrossFields.FK_PICTURE);
    	
    	String query = builder.toString();
    	
    	try (Connection con = DatabaseFactory.getConnection()) {
    		try (NamedParameterStatement stmt = NamedParameterStatement.fromConnection(con, query)) {
    			stmt.setParameter(PerfCrossFields.FK_PERFORMANCE, performanceId);
    			stmt.setParameter(PerfCrossFields.FK_PICTURE, photo.getId());
    			
    			int affected = stmt.executeUpdate();
    			if (affected != 1) {
    				throw new SaveException("No row added, or more than one row affected.");
    			}
    		}
    	}
    	catch (SQLException e) {
    		throw DatabaseUtil.createUnexpectedException(e);
    	}
    }
    
    @Override
    public void removePhoto(final int performanceId, final Photo photo)
    		throws ElementNotFoundException, DatabaseException {
    	
    	checkNotNull(photo, "Photo must not be null.");
    	checkArgument(performanceId >= 0, "Invalid ID given.");
    	checkState(photo.getId() >= 0, "Photo must have a valid ID.");
    	
    	StringBuilder builder = new StringBuilder();
    	builder.append("DELETE FROM ").append(PERFOMANCE_CROSS_TABLE);
    	builder.append(" WHERE ");
    	builder.append(PerfCrossFields.FK_PERFORMANCE);
    	builder.append(" = :").append(PerfCrossFields.FK_PERFORMANCE);
    	builder.append(" AND ").append(PerfCrossFields.FK_PICTURE);
    	builder.append(" = :").append(PerfCrossFields.FK_PICTURE).append(";");
    	
    	String query = builder.toString();
    	
    	try (Connection con = DatabaseFactory.getConnection()) {
    		con.setAutoCommit(false);
    		
    		try (NamedParameterStatement stmt = NamedParameterStatement.fromConnection(con, query)) {
    			stmt.setParameter(PerfCrossFields.FK_PERFORMANCE, performanceId);
    			stmt.setParameter(PerfCrossFields.FK_PICTURE, photo.getId());
    			
    			int affected = stmt.executeUpdate();
    			if (affected > 1) {
    				con.rollback();
    				throw new DatabaseException("Query would accidently affected more than one row.");
    			}
    			else if (affected < 0) {
    				throw new ElementNotFoundException();
    			}
    			
    			con.commit();
    		}
    	}
    	catch (SQLException e) {
    		throw DatabaseUtil.createUnexpectedException(e);
    	}
    }
    
	@Override
	public Collection<Photo> findByPerformance(final int id) throws DatabaseException {
		checkArgument(id >= 0, "ID must be greater or equal to 0.");
		
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT");
		builder.append(" ").append(Fields.ID);
		builder.append(", ").append(Fields.BLOB);
		builder.append(", ").append(Fields.NAME);
		builder.append(", ").append(Fields.LABEL);
		builder.append(" FROM ").append(TABLE_NAME);
		builder.append(" JOIN ").append(PERFOMANCE_CROSS_TABLE);
		builder.append(" ON ").append(TABLE_NAME).append(".").append(Fields.ID);
		builder.append(" = ");
		builder.append(PERFOMANCE_CROSS_TABLE).append(".").append(PerfCrossFields.FK_PICTURE);
		builder.append(" WHERE ");
		builder.append(PERFOMANCE_CROSS_TABLE).append(".").append(PerfCrossFields.FK_PERFORMANCE);
		builder.append(" = :").append(PerfCrossFields.FK_PERFORMANCE).append(";");
		
		String query = builder.toString();
		
		try (Connection con = DatabaseFactory.getConnection()) {
			try (NamedParameterStatement stmt = NamedParameterStatement.fromConnection(con, query)) {
				stmt.setParameter(PerfCrossFields.FK_PERFORMANCE, id);
				
				ResultSet rs = stmt.executeQuery();
				Collection<Photo> photos = fromResultSet(rs);
				DbUtils.closeQuietly(rs);
				
				return photos;
			}
		}
		catch (SQLException e) {
			throw DatabaseUtil.createUnexpectedException(e);
		}
	}

    @Override
    public Photo insert(Photo photo) throws SaveException {
        SqlQueryBuilder builder = new SqlQueryBuilder();
        builder.insert(TABLE_NAME, Fields.BLOB, Fields.NAME, Fields.LABEL);

        final String query = builder.toString();

        try (Connection con = DatabaseFactory.getConnection()) {
            try (NamedParameterStatement stmt = NamedParameterStatement.fromConnection(con, query)) {
                stmt.setParameter(Fields.BLOB, photo.getBlob());
                stmt.setParameter(Fields.NAME, photo.getName());
                stmt.setParameter(Fields.LABEL, photo.getLabel());

                int affectedRows = stmt.executeUpdate();
                if (affectedRows != 1) {
                    throw new SaveException();
                }

                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int id = generatedKeys.getInt(1);

                        // Create new Photo with id
                        Photo inserted = new Photo(id, photo.getBlob(), photo.getName(),
                                photo.getLabel());

                        return inserted;
                    }
                    else {
                        throw new SaveException("Could not retrieve generated ID.");
                    }
                }
            }

        }
        catch (SQLException | DatabaseException e) {
            throw new SaveException(e);
        }
    }

    @Override
    public void update(Photo photo) throws ElementNotFoundException, SaveException {
        checkNotNull(photo, "Photo must not be null.");

        if (photo.getId() < 0) {
            throw new ElementNotFoundException("Element has no valid ID.");
        }

        SqlQueryBuilder builder = new SqlQueryBuilder();
        builder.update(TABLE_NAME, Fields.BLOB, Fields.NAME, Fields.LABEL);
        builder.where(Fields.ID);

        final String query = builder.toString();

        try (Connection con = DatabaseFactory.getConnection()) {
            try (NamedParameterStatement stmt = NamedParameterStatement.fromConnection(con, query)) {
                stmt.setParameter(Fields.ID, photo.getId());
                stmt.setParameter(Fields.BLOB, photo.getBlob());
                stmt.setParameter(Fields.NAME, photo.getName());
                stmt.setParameter(Fields.LABEL, photo.getLabel());
                int affectedRows = stmt.executeUpdate();
                if (affectedRows != 1) {
                    throw new SaveException();
                }
            }
        }
        catch (SQLException | DatabaseException e) {
            throw new SaveException(e);
        }

    }

    private Collection<Photo> fromResultSet(final ResultSet rs) throws SQLException {
        checkNotNull(rs, "Result must not be null.");
        Collection<Photo> photoList = new ArrayList<>();

        while (rs.next()) {
            int id = rs.getInt(Fields.ID);
            byte[] blob = rs.getBytes(Fields.BLOB);
            String name = rs.getString(Fields.NAME);
            String label = rs.getString(Fields.LABEL);

            Photo photo = new Photo(id, blob, name, label);

            photoList.add(photo);
        }

        return photoList;
    }

    private static final String TABLE_NAME = "Picture";

    private static final class Fields {
        private static final String ID = "Picture_ID";
        private static final String BLOB = "Picture_Blob";
        private static final String NAME = "Picture_Name";
        private static final String LABEL = "Picture_Label";
    }
    
    private static final String PERFOMANCE_CROSS_TABLE = "Picture_Catalog";
    
    private static final class PerfCrossFields {
    	private static final String FK_PICTURE = "Picture_Catalog_Picture_FK";
    	private static final String FK_PERFORMANCE = "Picture_Catalog_Performance_FK";
    }
}
