package de.hswt.hrm.catalog.dao.jdbc;

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
import de.hswt.hrm.catalog.model.Catalog;
import de.hswt.hrm.catalog.dao.core.ICatalogDao;

public class CatalogDao implements ICatalogDao {

    @Override
    public Collection<Catalog> findAll() throws DatabaseException {
        SqlQueryBuilder builder = new SqlQueryBuilder();
        builder.select(TABLE_NAME, Fields.ID, Fields.NAME);

        final String query = builder.toString();

        try (Connection con = DatabaseFactory.getConnection()) {
            try (NamedParameterStatement stmt = NamedParameterStatement.fromConnection(con, query)) {
                ResultSet result = stmt.executeQuery();

                Collection<Catalog> cataloges = fromResultSet(result);
                DbUtils.closeQuietly(result);

                return cataloges;
            }
        }
        catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public Catalog findById(int id) throws DatabaseException, ElementNotFoundException {
        checkArgument(id >= 0, "Id must not be negative.");

        SqlQueryBuilder builder = new SqlQueryBuilder();
        builder.select(TABLE_NAME, Fields.ID, Fields.NAME);
        builder.where(Fields.ID);

        final String query = builder.toString();

        try (Connection con = DatabaseFactory.getConnection()) {
            try (NamedParameterStatement stmt = NamedParameterStatement.fromConnection(con, query)) {
                stmt.setParameter(Fields.ID, id);
                ResultSet result = stmt.executeQuery();

                Collection<Catalog> cataloges = fromResultSet(result);
                DbUtils.closeQuietly(result);

                if (cataloges.size() < 1) {
                    throw new ElementNotFoundException();
                }
                else if (cataloges.size() > 1) {
                    throw new DatabaseException("ID '" + id + "' is not unique.");
                }

                return cataloges.iterator().next();
            }
        }
        catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    /**
     * @see {@link ICatalogDao#insert(Catalog)}
     */
    @Override
    public Catalog insert(Catalog catalog) throws SaveException {
        SqlQueryBuilder builder = new SqlQueryBuilder();
        builder.insert(TABLE_NAME, Fields.NAME);

        final String query = builder.toString();

        try (Connection con = DatabaseFactory.getConnection()) {
            try (NamedParameterStatement stmt = NamedParameterStatement.fromConnection(con, query)) {
                stmt.setParameter(Fields.NAME, catalog.getName());
                
                int affectedRows = stmt.executeUpdate();
                if (affectedRows != 1) {
                    throw new SaveException();
                }

                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int id = generatedKeys.getInt(1);

                        // Create new Catalog with id
                        Catalog inserted = new Catalog(id, catalog.getName());

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
    public void update(Catalog catalog) throws ElementNotFoundException, SaveException {
        checkNotNull(catalog, "Catalog must not be null.");

        if (catalog.getId() < 0) {
            throw new ElementNotFoundException("Element has no valid ID.");
        }

        SqlQueryBuilder builder = new SqlQueryBuilder();
        builder.update(TABLE_NAME, Fields.NAME);
        builder.where(Fields.ID);

        final String query = builder.toString();

        try (Connection con = DatabaseFactory.getConnection()) {
            try (NamedParameterStatement stmt = NamedParameterStatement.fromConnection(con, query)) {
                stmt.setParameter(Fields.ID, catalog.getId());
                stmt.setParameter(Fields.NAME, catalog.getName());

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

    private Collection<Catalog> fromResultSet(ResultSet rs) throws SQLException {
        checkNotNull(rs, "Result must not be null.");
        Collection<Catalog> catalogList = new ArrayList<>();

        while (rs.next()) {
            int id = rs.getInt(Fields.ID);
            String name = rs.getString(Fields.NAME);

            Catalog catalog = new Catalog(id, name);

            catalogList.add(catalog);
        }

        return catalogList;
    }

    private static final String TABLE_NAME = "Catalog";

    private static class Fields {
        public static final String ID = "Catalog_ID";
        public static final String NAME = "Catalog_Name";

    }
}
