package de.hswt.hrm.plant.service;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Collection;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Creatable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.database.exception.ElementNotFoundException;
import de.hswt.hrm.common.database.exception.SaveException;
import de.hswt.hrm.plant.model.Plant;
import de.hswt.hrm.plant.dao.core.IPlantDao;

@Creatable
public final class PlantService {
    private final static Logger LOG = LoggerFactory.getLogger(PlantService.class);
    private final IPlantDao plantDao;
    
    @Inject
    public PlantService(final IPlantDao plantDao) {
        checkNotNull(plantDao, "PlantDao not injected properly.");
        
        this.plantDao = plantDao;
        LOG.debug("PlantDao injected into PlantService.");
    }

    /**
     * @return All plants from storage.
     * @throws DatabaseException
     */
    public Collection<Plant> findAll() throws DatabaseException {
        return plantDao.findAll();
    }

    /**
     * @param id
     *            of the target Plant.
     * @return Plant with the given id.
     * @throws DatabaseException
     */
    public Plant findById(int id) throws ElementNotFoundException, DatabaseException {
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
    public Plant insert(Plant plant) throws SaveException {
        checkNotNull(plant.getPlace().orNull(), "Place is mandatory.");
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
    public void update(Plant Plant) throws ElementNotFoundException, SaveException {
        plantDao.update(Plant);
    }

    public void refresh(Plant plant) throws ElementNotFoundException, DatabaseException {
        Plant fromDb = plantDao.findById(plant.getId());
        plant.setAirPerformance(fromDb.getAirPerformance().orNull());
        plant.setConstructionYear(fromDb.getConstructionYear().orNull());
        plant.setCurrent(fromDb.getCurrent().orNull());
        plant.setDescription(fromDb.getDescription());
        plant.setManufactor(fromDb.getManufactor().orNull());
        plant.setMotorPower(fromDb.getMotorPower().orNull());
        plant.setMotorPower(fromDb.getMotorPower().orNull());
        plant.setNote(fromDb.getNote().orNull());
        plant.setPlace(fromDb.getPlace().orNull());
        plant.setType(fromDb.getType().orNull());
        plant.setVentilatorPerformance(fromDb.getVentilatorPerformance().orNull());
        plant.setVoltage(fromDb.getVoltage().orNull());
    }

}
