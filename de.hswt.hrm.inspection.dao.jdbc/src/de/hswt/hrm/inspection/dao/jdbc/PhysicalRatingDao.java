package de.hswt.hrm.inspection.dao.jdbc;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import de.hswt.hrm.common.database.DatabaseFactory;
import de.hswt.hrm.common.database.NamedParameterStatement;
import de.hswt.hrm.common.database.SqlQueryBuilder;
import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.database.exception.ElementNotFoundException;
import de.hswt.hrm.common.database.exception.SaveException;
import de.hswt.hrm.common.exception.NotImplementedException;
import de.hswt.hrm.component.dao.core.IComponentDao;
import de.hswt.hrm.component.model.Component;
import de.hswt.hrm.inspection.dao.core.IPhysicalRatingDao;
import de.hswt.hrm.inspection.model.BiologicalRating;
import de.hswt.hrm.inspection.model.Inspection;
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
        SqlQueryBuilder builder = new SqlQueryBuilder();
        builder.select(TABLE_NAME, Fields.ID, Fields.RATING, Fields.NOTE, Fields.COMPONENT_FK,
                Fields.REPORT_FK);

        String query = builder.toString();

        try (Connection con = DatabaseFactory.getConnection()) {
            try (NamedParameterStatement stmt = NamedParameterStatement.fromConnection(con, query)) {

                ResultSet rs = stmt.executeQuery();
                Collection<PhysicalRating> ratings = fromResultSet(rs);
                rs.close();

                return ratings;
            }
        }
        catch (SQLException e) {
            throw new DatabaseException("Unexpected error.", e);
        }
    }

    @Override
    public PhysicalRating findById(int id) throws DatabaseException, ElementNotFoundException {

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
    public PhysicalRating insert(PhysicalRating physicalRating) throws SaveException {
        SqlQueryBuilder builder = new SqlQueryBuilder();
        builder.insert(TABLE_NAME, Fields.RATING, Fields.NOTE, Fields.COMPONENT_FK,
                Fields.REPORT_FK);

        final String query = builder.toString();

        try (Connection con = DatabaseFactory.getConnection()) {
            try (NamedParameterStatement stmt = NamedParameterStatement.fromConnection(con, query)) {
                stmt.setParameter(Fields.RATING, physicalRating.getRating());
                stmt.setParameter(Fields.NOTE, physicalRating.getNote());
                stmt.setParameter(Fields.COMPONENT_FK, physicalRating.getComponent().get().getId());
                stmt.setParameter(Fields.REPORT_FK, physicalRating.getReport().get().getId());

                int affectedRows = stmt.executeUpdate();
                if (affectedRows != 1) {
                    throw new SaveException();
                }

                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int id = generatedKeys.getInt(1);

                        // Create new Place with id
                        PhysicalRating inserted = new PhysicalRating(id,
                                physicalRating.getRating(), physicalRating.getNote());

                        inserted.setComponent(physicalRating.getComponent().orNull());
                        inserted.setReport(physicalRating.getReport().orNull());

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
    public void update(PhysicalRating physicalRating) throws ElementNotFoundException,
            SaveException {
        checkNotNull(physicalRating, "Physical Rating must not be null.");

        if (physicalRating.getId() < 0) {
            throw new ElementNotFoundException("Element has no valid ID.");
        }

        SqlQueryBuilder builder = new SqlQueryBuilder();
        builder.update(TABLE_NAME, Fields.RATING, Fields.NOTE, Fields.COMPONENT_FK,
                Fields.REPORT_FK);
        builder.where(Fields.ID);

        final String query = builder.toString();

        try (Connection con = DatabaseFactory.getConnection()) {
            try (NamedParameterStatement stmt = NamedParameterStatement.fromConnection(con, query)) {
                stmt.setParameter(Fields.RATING, physicalRating.getRating());
                stmt.setParameter(Fields.NOTE, physicalRating.getNote());
                stmt.setParameter(Fields.COMPONENT_FK, physicalRating.getComponent().get().getId());
                stmt.setParameter(Fields.REPORT_FK, physicalRating.getReport().get().getId());

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

    @Override
    public Collection<PhysicalRating> findByInspection(Inspection inspection)
            throws DatabaseException {

        checkNotNull(inspection, "Inspection is mandatory.");
        checkArgument(inspection.getId() >= 0, "Inspection must have a valid ID.");

        SqlQueryBuilder builder = new SqlQueryBuilder();
        builder.select(TABLE_NAME, Fields.ID, Fields.RATING, Fields.NOTE, Fields.COMPONENT_FK,
                Fields.REPORT_FK);
        builder.where(Fields.REPORT_FK);

        String query = builder.toString();

        try (Connection con = DatabaseFactory.getConnection()) {
            try (NamedParameterStatement stmt = NamedParameterStatement.fromConnection(con, query)) {
                stmt.setParameter(Fields.REPORT_FK, inspection.getId());

                ResultSet rs = stmt.executeQuery();
                Collection<PhysicalRating> ratings = fromResultSet(rs);
                rs.close();
                return ratings;
            }
        }
        catch (SQLException e) {
            throw new DatabaseException("Unexpected error.", e);
        }
    }

    private Collection<PhysicalRating> fromResultSet(final ResultSet rs) {
        // FIXME Add component here

        checkNotNull(rs, "Result must not be null.");
        Collection<PhysicalRating> physicalRatingList = new ArrayList<>();

        while (rs.next()) {
            int id = rs.getInt(Fields.ID);
            int rating = rs.getInt(Fields.RATING);
            String note = rs.getString(Fields.NOTE);
            PhysicalRating physicalRating = new PhysicalRating(id, rating, note);
            physicalRating.setReport(InspectionDao.findById(rs.getInt(Fields.REPORT_FK)));
            physicalRating.setComponent(ComponentDao.findById(rs.getInt(Fields.COMPONENT_FK)));
            physicalRatingList.add(physicalRating);
        }

        return physicalRatingList;

    }

    private static final String TABLE_NAME = "Component_Physical_Rating";

    private static final class Fields {
        private static final String ID = "Component_Physical_Rating_ID";
        private static final String RATING = "Component_Physical_Rating_Rating";
        private static final String NOTE = "Component_Physical_Rating_Note";
        private static final String COMPONENT_FK = "Component_Physical_Rating_Component_FK";
        private static final String REPORT_FK = "Component_Physical_Rating_Report_FK";
    }
}
