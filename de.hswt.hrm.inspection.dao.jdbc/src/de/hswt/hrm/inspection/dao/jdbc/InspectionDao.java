package de.hswt.hrm.inspection.dao.jdbc;

import java.util.Collection;

import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.database.exception.ElementNotFoundException;
import de.hswt.hrm.common.database.exception.SaveException;
import de.hswt.hrm.inspection.dao.core.IInspectionDao;
import de.hswt.hrm.inspection.model.Inspection;

public class InspectionDao implements IInspectionDao {

    @Override
    public Collection<Inspection> findAll() throws DatabaseException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Inspection findById(int id) throws DatabaseException, ElementNotFoundException {
        // TODO Auto-generated method stub
        return null;
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

    private static final String TABLE_NAME = "Report";

    private static final class Fields {
        public static final String ID = "Report_ID";
        public static final String LAYOUT = "Report_Layout_FK";
        public static final String PLANT = "Report_Plant_FK";
        public static final String REQUESTER = "Report_Requester_FK";
        public static final String CONTRACTOR = "Report_Contractor_FK";
        public static final String CHECKER = "Report_Checker_FK";
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
