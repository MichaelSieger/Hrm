package de.hswt.hrm.plant.service;

import java.util.Collection;

import org.eclipse.e4.core.di.annotations.Creatable;

import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.database.exception.ElementNotFoundException;
import de.hswt.hrm.common.database.exception.SaveException;
import de.hswt.hrm.plant.model.Plant;
import de.hswt.hrm.place.model.Place;
import de.hswt.hrm.plant.dao.core.IPlantDao;
import de.hswt.hrm.plant.dao.jdbc.PlantDao;
import de.hswt.hrm.place.dao.core.IPlaceDao;
import de.hswt.hrm.place.dao.jdbc.PlaceDao;
import static com.google.common.base.Preconditions.checkNotNull;

@Creatable
public final class PlantService {

    private PlantService() {

    }

    private static IPlantDao plantDao = new PlantDao();
    private static IPlaceDao placeDao = new PlaceDao();

    /**
     * @return All plants from storage.
     * @throws DatabaseException
     */
    public static Collection<Plant> findAll() throws DatabaseException {
        return plantDao.findAll();
    }

    /**
     * @param id
     *            of the target Plant.
     * @return Plant with the given id.
     * @throws DatabaseException
     */
    public static Plant findById(int id) throws ElementNotFoundException, DatabaseException {
        return plantDao.findById(id);
    }

    /**
     * Add a new Plant to storage.
     * 
     * @param Plant
     *            Plant that should be stored.
     * @return The created Plant.
     * @throws SaveException
     *             If the Plant could not be inserted.
     */
    public static Plant insert(Plant plant) throws SaveException {
        checkNotNull(plant.getPlace().orNull(), "Place is mandatory.");
        Place place = plant.getPlace().get();
        if (place.getId() < 0) {
            Place inserted = placeDao.insert(place);
            plant.setPlace(inserted);
        }

        return plantDao.insert(plant);
    }

    /**
     * Update an existing Plant in storage.
     * 
     * @param Plant
     *            Plant that should be updated.
     * @throws ElementNotFoundException
     *             If the given Plant is not present in the database.
     * @throws SaveException
     *             If the Plant could not be updated.
     */
    public static void update(Plant Plant) throws ElementNotFoundException, SaveException {
        plantDao.update(Plant);
    }

    public static void refresh(Plant plant) throws ElementNotFoundException, DatabaseException {
        Plant fromDb = plantDao.findById(plant.getId());
        plant.setAirPerformance(fromDb.getAirPerformance().orNull());
        plant.setConstructionYear(fromDb.getConstructionYear().orNull());
        plant.setCurrent(fromDb.getCurrent().orNull());
        plant.setDescription(fromDb.getDescription());
        plant.setInspectionInterval(fromDb.getInspectionInterval());
        plant.setManufactor(fromDb.getManufactor().orNull());
        plant.setMotorPower(fromDb.getMotorPower().orNull());
        plant.setMotorPower(fromDb.getMotorPower().orNull());
        plant.setNote(fromDb.getNote().orNull());
        plant.setNumberOfElements(fromDb.getNumberOfElements());
        plant.setPlace(fromDb.getPlace().orNull());
        plant.setType(fromDb.getType().orNull());
        plant.setVentilatorPerformance(fromDb.getVentilatorPerformance().orNull());
        plant.setVoltage(fromDb.getVoltage().orNull());

    }

}
