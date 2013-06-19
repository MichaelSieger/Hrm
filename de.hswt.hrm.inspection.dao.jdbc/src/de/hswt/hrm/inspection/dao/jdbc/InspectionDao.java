package de.hswt.hrm.inspection.dao.jdbc;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import com.google.common.base.Optional;

import de.hswt.hrm.common.database.DatabaseFactory;
import de.hswt.hrm.common.database.NamedParameterStatement;
import de.hswt.hrm.common.database.SqlQueryBuilder;
import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.database.exception.ElementNotFoundException;
import de.hswt.hrm.common.database.exception.SaveException;
import de.hswt.hrm.inspection.dao.core.IInspectionDao;
import de.hswt.hrm.inspection.model.Inspection;

public class InspectionDao implements IInspectionDao {

    @Override
    public Collection<Inspection> findAll() throws DatabaseException {
        SqlQueryBuilder builder = new SqlQueryBuilder();
        builder.select(TABLE_NAME, Fields.ID, Fields.REQUESTER_FK, Fields.CONTRACTOR_FK,
                Fields.CHECKER_FK, Fields.JOBDATE, Fields.REPORTDATE, Fields.NEXTDATE,
                Fields.TEMPERATURE, Fields.HUMIDITY, Fields.SUMMARY, Fields.TITEL,
                Fields.TEMPERATURERATING, Fields.TEMPERATUREQUANTIFIER, Fields.HUMIDITYRATING,
                Fields.HUMIDITYQUANTIFIER);

        String query = builder.toString();

        try (Connection con = DatabaseFactory.getConnection()) {
            try (NamedParameterStatement stmt = NamedParameterStatement.fromConnection(con, query)) {

                ResultSet rs = stmt.executeQuery();
                Collection<Inspection> inspections = fromResultSet(rs,
                        Optional.<Inspection> absent());
                rs.close();

                return inspections;
            }
        }
        catch (SQLException e) {
            throw new DatabaseException("Unexpected error.", e);
        }
    }

    @Override
    public Inspection findById(int id) throws DatabaseException, ElementNotFoundException {
        checkArgument(id >= 0, "ID must be non negative.");

        SqlQueryBuilder builder = new SqlQueryBuilder();
        builder.select(TABLE_NAME, Fields.ID, Fields.REQUESTER_FK, Fields.CONTRACTOR_FK,
                Fields.CHECKER_FK, Fields.JOBDATE, Fields.REPORTDATE, Fields.NEXTDATE,
                Fields.TEMPERATURE, Fields.HUMIDITY, Fields.SUMMARY, Fields.TITEL,
                Fields.TEMPERATURERATING, Fields.TEMPERATUREQUANTIFIER, Fields.HUMIDITYRATING,
                Fields.HUMIDITYQUANTIFIER);
        builder.where(Fields.ID);

        String query = builder.toString();

        try (Connection con = DatabaseFactory.getConnection()) {
            try (NamedParameterStatement stmt = NamedParameterStatement.fromConnection(con, query)) {
                stmt.setParameter(Fields.ID, id);

                ResultSet rs = stmt.executeQuery();
                Collection<Inspection> ratings = fromResultSet(rs, Optional.<Inspection> absent());
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
    public Inspection insert(Inspection inspection) throws SaveException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void update(Inspection inspection) throws ElementNotFoundException, SaveException {
        // TODO Auto-generated method stub

    }

    private Collection<Inspection> fromResultSet(final ResultSet rs, Optional<Inspection> inspection)
            throws SQLException, ElementNotFoundException, DatabaseException {

        checkNotNull(rs, "Result must not be null.");
        Collection<Inspection> inspectionList = new ArrayList<>();

        // FIXME

        return inspectionList;
    }

    private static final String TABLE_NAME = "Report";

    private static final class Fields {
        public static final String ID = "Report_ID";
        public static final String LAYOUT_KF = "Report_Layout_FK";
        public static final String PLANT_FK = "Report_Plant_FK";
        public static final String REQUESTER_FK = "Report_Requester_FK";
        public static final String CONTRACTOR_FK = "Report_Contractor_FK";
        public static final String CHECKER_FK = "Report_Checker_FK";
        public static final String JOBDATE = "Report_Jobdate";
        public static final String REPORTDATE = "Report_Reportdate";
        public static final String NEXTDATE = "Report_Nextdate";
        public static final String TEMPERATURE = "Report_Airtemperature";
        public static final String HUMIDITY = "Report_Humidity";
        public static final String SUMMARY = "Report_Summary";
        public static final String TITEL = "Report_Titel";
        public static final String TEMPERATURERATING = "Report_Airtemperature_Rating";
        public static final String TEMPERATUREQUANTIFIER = "Report_Airtemperature_Quantifier";
        public static final String HUMIDITYRATING = "Report_Humidity_Rating";
        public static final String HUMIDITYQUANTIFIER = "Report_Humidity_Quantifier";
    }

}
