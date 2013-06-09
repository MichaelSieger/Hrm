package de.hswt.hrm.place.dao.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.dbutils.DbUtils;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import de.hswt.hrm.common.database.DatabaseFactory;
import de.hswt.hrm.common.database.NamedParameterStatement;
import de.hswt.hrm.common.database.SqlQueryBuilder;
import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.database.exception.ElementNotFoundException;
import de.hswt.hrm.common.database.exception.SaveException;
import de.hswt.hrm.place.model.Place;
import de.hswt.hrm.place.dao.core.IPlaceDao;

public class PlaceDao implements IPlaceDao {

    @Override
    public Collection<Place> findAll() throws DatabaseException {
        SqlQueryBuilder builder = new SqlQueryBuilder();
        builder.select(TABLE_NAME, Fields.ID, Fields.NAME, Fields.POSTCODE, Fields.CITY,
                Fields.STREET, Fields.STREET_NO);

        final String query = builder.toString();

        try (Connection con = DatabaseFactory.getConnection()) {
            try (NamedParameterStatement stmt = NamedParameterStatement.fromConnection(con, query)) {
                ResultSet result = stmt.executeQuery();

                Collection<Place> places = fromResultSet(result);
                DbUtils.closeQuietly(result);

                return places;
            }
        }
        catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public Place findById(int id) throws DatabaseException, ElementNotFoundException {
        checkArgument(id >= 0, "Id must not be negative.");

        SqlQueryBuilder builder = new SqlQueryBuilder();
        builder.select(TABLE_NAME, Fields.ID, Fields.NAME, Fields.POSTCODE, Fields.CITY,
                Fields.STREET, Fields.STREET_NO);
        builder.where(Fields.ID);

        final String query = builder.toString();

        try (Connection con = DatabaseFactory.getConnection()) {
            try (NamedParameterStatement stmt = NamedParameterStatement.fromConnection(con, query)) {
                stmt.setParameter(Fields.ID, id);
                ResultSet result = stmt.executeQuery();

                Collection<Place> places = fromResultSet(result);
                DbUtils.closeQuietly(result);

                if (places.size() < 1) {
                    throw new ElementNotFoundException();
                }
                else if (places.size() > 1) {
                    throw new DatabaseException("ID '" + id + "' is not unique.");
                }

                return places.iterator().next();
            }
        }
        catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    /**
     * @see {@link IPlaceDao#insert(Place)}
     */
    @Override
    public Place insert(Place place) throws SaveException {
        SqlQueryBuilder builder = new SqlQueryBuilder();
        builder.insert(TABLE_NAME, Fields.NAME, Fields.POSTCODE, Fields.CITY, Fields.STREET,
                Fields.STREET_NO);

        final String query = builder.toString();

        try (Connection con = DatabaseFactory.getConnection()) {
            try (NamedParameterStatement stmt = NamedParameterStatement.fromConnection(con, query)) {
                stmt.setParameter(Fields.NAME, place.getPlaceName());
                stmt.setParameter(Fields.POSTCODE, place.getPostCode());
                stmt.setParameter(Fields.CITY, place.getCity());
                stmt.setParameter(Fields.STREET, place.getStreet());
                stmt.setParameter(Fields.STREET_NO, place.getStreetNo());

                int affectedRows = stmt.executeUpdate();
                if (affectedRows != 1) {
                    throw new SaveException();
                }

                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int id = generatedKeys.getInt(1);

                        // Create new Place with id
                        Place inserted = new Place(id, place.getPlaceName(), place.getPostCode(),
                                place.getCity(), place.getStreet(), place.getStreetNo());

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
    public void update(Place place) throws ElementNotFoundException, SaveException {
        checkNotNull(place, "Place must not be null.");

        if (place.getId() < 0) {
            throw new ElementNotFoundException("Element has no valid ID.");
        }

        SqlQueryBuilder builder = new SqlQueryBuilder();
        builder.update(TABLE_NAME, Fields.NAME, Fields.POSTCODE, Fields.CITY, Fields.STREET,
                Fields.STREET_NO);
        builder.where(Fields.ID);

        final String query = builder.toString();

        try (Connection con = DatabaseFactory.getConnection()) {
            try (NamedParameterStatement stmt = NamedParameterStatement.fromConnection(con, query)) {
                stmt.setParameter(Fields.ID, place.getId());
                stmt.setParameter(Fields.NAME, place.getPlaceName());
                stmt.setParameter(Fields.POSTCODE, place.getPostCode());
                stmt.setParameter(Fields.CITY, place.getCity());
                stmt.setParameter(Fields.STREET, place.getStreet());
                stmt.setParameter(Fields.STREET_NO, place.getStreetNo());

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

    private Collection<Place> fromResultSet(ResultSet rs) throws SQLException {
        checkNotNull(rs, "Result must not be null.");
        Collection<Place> placeList = new ArrayList<>();

        while (rs.next()) {
            int id = rs.getInt(Fields.ID);
            String placeName = rs.getString(Fields.NAME);
            String postCode = rs.getString(Fields.POSTCODE);
            String city = rs.getString(Fields.CITY);
            String street = rs.getString(Fields.STREET);
            String streetNo = rs.getString(Fields.STREET_NO);

            Place place = new Place(id, placeName, postCode, city, street, streetNo);

            placeList.add(place);
        }

        return placeList;
    }

    private static final String TABLE_NAME = "Place";

    private static class Fields {
        public static final String ID = "Place_ID";
        public static final String NAME = "Place_Name";
        public static final String POSTCODE = "Place_Zip_Code";
        public static final String CITY = "Place_City";
        public static final String STREET = "Place_Street";
        public static final String STREET_NO = "Place_Street_Number";

    }
}
