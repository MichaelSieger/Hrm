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
    public BiologicalRating insert(BiologicalRating biological) throws SaveException {
        throw new NotImplementedException();
    }

    @Override
    public void update(BiologicalRating biological) throws ElementNotFoundException, SaveException {
        throw new NotImplementedException();
    }

    private Collection<BiologicalRating> fromResultSet(final ResultSet rs) {
        // All statements should join the flag table to be able to parse it
        // correctly here...
        throw new NotImplementedException();
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
