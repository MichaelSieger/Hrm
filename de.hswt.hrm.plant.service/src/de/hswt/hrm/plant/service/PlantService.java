package de.hswt.hrm.plant.service;

import java.util.Collection;

import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.database.exception.ElementNotFoundException;
import de.hswt.hrm.common.database.exception.SaveException;
import de.hswt.hrm.plant.model.Plant;
import de.hswt.hrm.plant.dao.core.IPlantDao;
import de.hswt.hrm.plant.dao.jdbc.PlantDao;

public final class PlantService {

    private PlantService() {

    }

    private static IPlantDao dao = new PlantDao();

    /**
     * @return All plants from storage.
     * @throws DatabaseException
     */
    public static Collection<Plant> findAll() throws DatabaseException {
        return dao.findAll();
    }

    /**
     * @param id
     *            of the target Plant.
     * @return Plant with the given id.
     * @throws DatabaseException
     */
    public static Plant findById(int id) throws ElementNotFoundException, DatabaseException {
        return dao.findById(id);
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
    public static Plant insert(Plant Plant) throws SaveException {
        return dao.insert(Plant);
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
        dao.update(Plant);
    }

    public static void refresh(Plant plant) throws ElementNotFoundException, DatabaseException {
        Plant fromDb = dao.findById(plant.getId());
        plant.setAirPerformance(fromDb.getAirPerformance().orNull());
        plant.setConstructionYear(fromDb.getConstructionYear().orNull());
        plant.setCurrent(fromDb.getCurrent().orNull());
    }

}
