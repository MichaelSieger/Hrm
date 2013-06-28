package de.hswt.hrm.inspection.dao.jdbc;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

import com.google.common.base.Optional;

import de.hswt.hrm.common.database.DatabaseFactory;
import de.hswt.hrm.common.database.NamedParameterStatement;
import de.hswt.hrm.common.database.SqlQueryBuilder;
import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.database.exception.ElementNotFoundException;
import de.hswt.hrm.common.database.exception.SaveException;
import de.hswt.hrm.inspection.dao.core.IInspectionDao;
import de.hswt.hrm.inspection.dao.core.IPhysicalRatingDao;
import de.hswt.hrm.inspection.model.Inspection;
import de.hswt.hrm.inspection.model.PhysicalRating;
import de.hswt.hrm.scheme.dao.core.ISchemeComponentDao;
import de.hswt.hrm.scheme.model.SchemeComponent;

public class PhysicalRatingDao implements IPhysicalRatingDao {
    private final IInspectionDao inspectionDao;
    private final ISchemeComponentDao schemeComponentDao;

    // TODO: add LOG messages
    @Inject
    public PhysicalRatingDao(final IInspectionDao inspectionDao,
            final ISchemeComponentDao schemeComponentDao) {

        checkNotNull(inspectionDao, "Inspectiondao not properly injected to PhysicalRatingDao");
        checkNotNull(schemeComponentDao,
                "SchemeComponentDao not properly injected to PhysicalRatingDao.");

        this.inspectionDao = inspectionDao;
        this.schemeComponentDao = schemeComponentDao;
    }

    @Override
    public Collection<PhysicalRating> findAll() throws DatabaseException {
        SqlQueryBuilder builder = new SqlQueryBuilder();
        builder.select(TABLE_NAME, Fields.ID, Fields.RATING, Fields.NOTE, Fields.COMPONENT_FK,
                Fields.REPORT_FK, Fields.QUANTIFIER);

        String query = builder.toString();

        try (Connection con = DatabaseFactory.getConnection()) {
            try (NamedParameterStatement stmt = NamedParameterStatement.fromConnection(con, query)) {

                ResultSet rs = stmt.executeQuery();
                Collection<PhysicalRating> ratings = fromResultSet(rs,
                        Optional.<Inspection> absent());
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
                Fields.REPORT_FK, Fields.QUANTIFIER);
        builder.where(Fields.ID);

        String query = builder.toString();

        try (Connection con = DatabaseFactory.getConnection()) {
            try (NamedParameterStatement stmt = NamedParameterStatement.fromConnection(con, query)) {
                stmt.setParameter(Fields.ID, id);

                ResultSet rs = stmt.executeQuery();
                Collection<PhysicalRating> ratings = fromResultSet(rs,
                        Optional.<Inspection> absent());
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
        checkNotNull(physicalRating, "Physical Rating must not be null.");
        checkState(physicalRating.isValid(), "Physical Rating is invalid");

        SqlQueryBuilder builder = new SqlQueryBuilder();
        builder.insert(TABLE_NAME, Fields.RATING, Fields.NOTE, Fields.COMPONENT_FK,
                Fields.REPORT_FK, Fields.QUANTIFIER);

        final String query = builder.toString();

        try (Connection con = DatabaseFactory.getConnection()) {
            try (NamedParameterStatement stmt = NamedParameterStatement.fromConnection(con, query)) {
                stmt.setParameter(Fields.RATING, physicalRating.getRating());
                stmt.setParameter(Fields.NOTE, physicalRating.getNote().orNull());
                stmt.setParameter(Fields.COMPONENT_FK, physicalRating.getComponent().getId());
                stmt.setParameter(Fields.REPORT_FK, physicalRating.getInspection().getId());
                stmt.setParameter(Fields.QUANTIFIER, physicalRating.getQuantifier());

                int affectedRows = stmt.executeUpdate();
                if (affectedRows != 1) {
                    throw new SaveException();
                }

                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int id = generatedKeys.getInt(1);

                        // Create new Physical Rating with id
                        PhysicalRating inserted = new PhysicalRating(id,
                                physicalRating.getInspection(), physicalRating.getComponent(),
                                physicalRating.getRating(), physicalRating.getQuantifier());

                        inserted.setNote(physicalRating.getNote().orNull());
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
        checkState(physicalRating.isValid(), "Physical Rating is invalid");

        if (physicalRating.getId() < 0) {
            throw new ElementNotFoundException("Element has no valid ID.");
        }

        SqlQueryBuilder builder = new SqlQueryBuilder();
        builder.update(TABLE_NAME, Fields.RATING, Fields.NOTE, Fields.COMPONENT_FK,
                Fields.REPORT_FK, Fields.QUANTIFIER);
        builder.where(Fields.ID);

        final String query = builder.toString();

        try (Connection con = DatabaseFactory.getConnection()) {
            try (NamedParameterStatement stmt = NamedParameterStatement.fromConnection(con, query)) {
                stmt.setParameter(Fields.RATING, physicalRating.getRating());
                stmt.setParameter(Fields.NOTE, physicalRating.getNote().orNull());
                stmt.setParameter(Fields.COMPONENT_FK, physicalRating.getComponent().getId());
                stmt.setParameter(Fields.REPORT_FK, physicalRating.getInspection().getId());
                stmt.setParameter(Fields.QUANTIFIER, physicalRating.getQuantifier());

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
                Fields.REPORT_FK, Fields.QUANTIFIER);
        builder.where(Fields.REPORT_FK);

        String query = builder.toString();

        try (Connection con = DatabaseFactory.getConnection()) {
            try (NamedParameterStatement stmt = NamedParameterStatement.fromConnection(con, query)) {
                stmt.setParameter(Fields.REPORT_FK, inspection.getId());

                ResultSet rs = stmt.executeQuery();
                Collection<PhysicalRating> ratings = fromResultSet(rs, Optional.of(inspection));
                rs.close();
                return ratings;
            }
        }
        catch (SQLException e) {
            throw new DatabaseException("Unexpected error.", e);
        }
    }

    /**
     * 
     * @param rs
     * @param inspection
     *            Optional field which can be used to avoid loading an already present inspection.
     * @return
     * @throws SQLException
     * @throws ElementNotFoundException
     * @throws DatabaseException
     */
    private Collection<PhysicalRating> fromResultSet(final ResultSet rs,
            Optional<Inspection> inspection) throws SQLException, ElementNotFoundException,
            DatabaseException {

        checkNotNull(rs, "Result must not be null.");
        Collection<PhysicalRating> physicalRatingList = new ArrayList<>();

        while (rs.next()) {
            int id = rs.getInt(Fields.ID);
            int rating = rs.getInt(Fields.RATING);
            String note = rs.getString(Fields.NOTE);
            int quantifier = rs.getInt(Fields.QUANTIFIER);
            int inspectionId = rs.getInt(Fields.REPORT_FK);
            if (!(inspection.isPresent()) || inspection.get().getId() != inspectionId) {
                inspection = Optional.of(inspectionDao.findById(inspectionId));
            }
            int componentId = rs.getInt(Fields.COMPONENT_FK);
            checkState(componentId >= 0, "Invalid component ID retrieved from database.");
            SchemeComponent component = schemeComponentDao.findById(componentId);

            PhysicalRating physicalRating = new PhysicalRating(id, inspection.get(), component,
                    rating, quantifier);
            physicalRating.setNote(note);
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
        private static final String QUANTIFIER = "Component_Physical_Rating_Quantifier";
    }
}
