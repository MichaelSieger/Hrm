package de.hswt.hrm.plant.dao.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.dbutils.DbUtils;

import static com.google.common.base.Preconditions.*;

import de.hswt.hrm.common.database.DatabaseFactory;
import de.hswt.hrm.common.database.NamedParameterStatement;
import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.database.exception.ElementNotFoundException;
import de.hswt.hrm.common.database.exception.SaveException;
import de.hswt.hrm.common.exception.NotImplementedException;
import de.hswt.hrm.plant.dao.core.IPlantDao;
import de.hswt.hrm.plant.model.Plant;

public class PlantDao implements IPlantDao {

    @Override
    public Collection<Plant> findAll() throws DatabaseException {

        final String query = "SELECT Plant_ID, Plant_Place_FK, Plant_Inspection_Intervall, "
                + "Plant_Manufacturer, Plant_Year_Of_Construction, Plant_Type"
                + "Plant_Airperformance, Plant_Motorpower, Plant_Motor_Rpm, Plant_Ventilatorperformance, "
                + "Plant_Current, Plant_Voltage, Plant_Note, Plant_Description FROM Plant ;";

        try (Connection con = DatabaseFactory.getConnection()) {
            try (NamedParameterStatement stmt = NamedParameterStatement.fromConnection(con, query)) {
                ResultSet result = stmt.executeQuery();

                Collection<Plant> plants = fromResultSet(result);
                DbUtils.closeQuietly(result);
                return plants;
            }
        }
        catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public Plant findById(int id) throws DatabaseException, ElementNotFoundException {
        return null;
    }

    /**
     * @see {@link IPlantDao#insert(Plant)}
     */
    @Override
    public Plant insert(Plant plant) throws SaveException {
        return null;
    }

    @Override
    public void update(Plant plant) throws ElementNotFoundException, SaveException {
     
    }

    private Collection<Plant> fromResultSet(ResultSet rs) throws SQLException {
        checkNotNull(rs, "Result must not be null.");
        Collection<Plant> plantList = new ArrayList<>();

        while (rs.next()) {
//            "SELECT Plant_ID, Plant_Place_FK, Plant_Inspection_Intervall, "
//                    + "Plant_Manufacturer, Plant_Year_Of_Construction, Plant_Type"
//                    + "Plant_Airperformance, Plant_Motorpower, Plant_Motor_Rpm, Plant_Ventilatorperformance, "
//                    + "Plant_Current, Plant_Voltage, Plant_Note, Plant_Description FROM Plant ;";

            int id = rs.getInt("plant_ID");
            int inspectionIntervall = rs.getInt("Plant_Inspection_Intervall");
            String description = rs.getString("Plant_Description");


            Plant plant = new Plant(id, inspectionIntervall, description);
            plant.setConstructionYear(rs.getInt("Plant_Year_Of_Construction"));
            plant.setManufactor(rs.getString("Plant_Manufacturer"));
            plant.setType(rs.getString("Plant_Type"));
            plant.setAirPerformance(rs.getString("Plant_Airperformance"));
            plant.setMotorPower(rs.getString("Plant_Motorpower"));
            plant.setMotorRpm(rs.getString("Plant_Motor_Rpm"));
            plant.setVentilatorPerformance(rs.getString("Plant_Ventilatorperformance"));
            plant.setCurrent(rs.getString("Plant_Current"));
            plant.setVoltage(rs.getString("Plant_Voltage"));
            plant.setNote(rs.getString("Plant_Note"));
            
            plantList.add(plant);
        }

        return plantList;


    }
}
