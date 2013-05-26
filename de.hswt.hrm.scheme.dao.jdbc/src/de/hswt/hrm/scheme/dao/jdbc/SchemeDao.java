package de.hswt.hrm.scheme.dao.jdbc;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

import org.apache.commons.dbutils.DbUtils;

import de.hswt.hrm.common.database.DatabaseFactory;
import de.hswt.hrm.common.database.NamedParameterStatement;
import de.hswt.hrm.common.database.SqlQueryBuilder;
import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.database.exception.ElementNotFoundException;
import de.hswt.hrm.common.database.exception.SaveException;
import de.hswt.hrm.scheme.dao.core.ISchemeDao;
import de.hswt.hrm.plant.model.Plant;
import de.hswt.hrm.plant.dao.core.IPlantDao;
import de.hswt.hrm.scheme.model.Scheme;

public class SchemeDao implements ISchemeDao {
    private final IPlantDao plantDao;
    
    @Inject
    public SchemeDao(final IPlantDao plantDao) {
        checkNotNull(plantDao, "PlantDao not injected properly.");
        
        this.plantDao = plantDao;
        // TODO: Add log message
    }

    @Override
    public Scheme insert(Scheme scheme) throws SaveException{
        SqlQueryBuilder builder = new SqlQueryBuilder();
        builder.insert(TABLE_NAME, Fields.TIMESTAMP, Fields.PLANT);
        
        final String query = builder.toString();

        try (Connection con = DatabaseFactory.getConnection()) {
            try (NamedParameterStatement stmt = NamedParameterStatement.fromConnection(con, query)) {
                stmt.setParameter(Fields.TIMESTAMP, scheme.getTimestamp());
                stmt.setParameter(Fields.PLANT, scheme.getPlant());


                int affectedRows = stmt.executeUpdate();
                if (affectedRows != 1) {
                    throw new SaveException();
                }

                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int id = generatedKeys.getInt(1);

                        Scheme inserted = new Scheme(id, scheme.getPlant().orNull(), 
                                scheme.getTimestamp().orNull());

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
    public Collection<Scheme> findAll() throws DatabaseException {
        SqlQueryBuilder builder = new SqlQueryBuilder();
        builder.select(TABLE_NAME, Fields.ID, Fields.TIMESTAMP, Fields.PLANT);

        final String query = builder.toString();

        try (Connection con = DatabaseFactory.getConnection()) {
            try (NamedParameterStatement stmt = NamedParameterStatement.fromConnection(con, query)) {
                ResultSet result = stmt.executeQuery();

                Collection<Scheme> schemes = fromResultSet(result);
                DbUtils.closeQuietly(result);

                return schemes;
            }
        }
        catch (SQLException e) {
            throw new DatabaseException(e);
        }

    }

    @Override
    public Scheme findById(int id) throws DatabaseException, ElementNotFoundException {
        checkArgument(id >= 0, "Id must not be negative.");

        SqlQueryBuilder builder = new SqlQueryBuilder();
        builder.select(TABLE_NAME, Fields.ID, Fields.TIMESTAMP, Fields.PLANT);
        builder.where(Fields.ID);

        final String query = builder.toString();

        try (Connection con = DatabaseFactory.getConnection()) {
            try (NamedParameterStatement stmt = NamedParameterStatement.fromConnection(con, query)) {
                stmt.setParameter(Fields.ID, id);
                ResultSet result = stmt.executeQuery();

                Collection<Scheme> schemes = fromResultSet(result);
                DbUtils.closeQuietly(result);

                if (schemes.size() < 1) {
                    throw new ElementNotFoundException();
                }
                else if (schemes.size() > 1) {
                    throw new DatabaseException("ID '" + id + "' is not unique.");
                }

                return schemes.iterator().next();
            }
        }
        catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public void update(Scheme scheme) throws ElementNotFoundException, SaveException {
        checkNotNull(scheme, "Scheme must not be null.");

        if (scheme.getId() < 0) {
            throw new ElementNotFoundException("Element has no valid ID.");
        }

        SqlQueryBuilder builder = new SqlQueryBuilder();
        builder.update(TABLE_NAME, Fields.TIMESTAMP, Fields.PLANT);
        builder.where(Fields.ID);
        
        final String query = builder.toString();

        try (Connection con = DatabaseFactory.getConnection()) {
            try (NamedParameterStatement stmt = NamedParameterStatement.fromConnection(con, query)) {
                stmt.setParameter(Fields.ID, scheme.getId());
                stmt.setParameter(Fields.TIMESTAMP, scheme.getTimestamp());
                stmt.setParameter(Fields.PLANT, scheme.getPlant());

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

    private Collection<Scheme> fromResultSet(ResultSet rs) throws SQLException, ElementNotFoundException, DatabaseException {
        checkNotNull(rs, "Result must not be null.");
        Collection<Scheme> placeList = new ArrayList<>();

        while (rs.next()) {
            int id = rs.getInt(Fields.ID);

            Timestamp timestamp = rs.getTimestamp("Session_Timestamp");
            
            int plantId = rs.getInt(Fields.PLANT);
            Plant plant = null;
            if (plantId >= 0) {
                plant = plantDao.findById(plantId);
            }

            Scheme scheme = new Scheme(id, plant, timestamp);

            placeList.add(scheme);
        }

        return placeList;
    }

    private static final String TABLE_NAME = "Scheme";

    private static class Fields {
        public static final String ID = "Scheme_ID";
        public static final String TIMESTAMP = "Scheme_Timestamp";
        public static final String PLANT = "Scheme_Plant_FK";

    }

}
