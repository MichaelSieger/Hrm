package de.hswt.hrm.scheme.dao.jdbc;

import static com.google.common.base.Preconditions.checkNotNull;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.dbutils.DbUtils;

import de.hswt.hrm.common.database.DatabaseFactory;
import de.hswt.hrm.common.database.NamedParameterStatement;
import de.hswt.hrm.common.database.SqlQueryBuilder;
import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.database.exception.ElementNotFoundException;
import de.hswt.hrm.common.database.exception.SaveException;
import de.hswt.hrm.component.model.Component;
import de.hswt.hrm.scheme.dao.core.ISchemeDao;
import de.hswt.hrm.plant.model.Plant;
import de.hswt.hrm.plant.dao.jdbc.PlantDao;
import de.hswt.hrm.scheme.model.Scheme;

public class SchemeDao implements ISchemeDao {
    

    
    @Override
    public Scheme insert(Scheme scheme) {
        // TODO Auto-generated method stub
        return null;
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
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void update(Scheme scheme) throws ElementNotFoundException, SaveException {
        // TODO Auto-generated method stub

    }

    private Collection<Scheme> fromResultSet(ResultSet rs) throws SQLException {
        checkNotNull(rs, "Result must not be null.");
        Collection<Scheme> placeList = new ArrayList<>();

        while (rs.next()) {
            int id = rs.getInt(Fields.ID);
            
            Timestamp timestamp = rs.getTimestamp("Session_Timestamp");
          //FIXME
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
