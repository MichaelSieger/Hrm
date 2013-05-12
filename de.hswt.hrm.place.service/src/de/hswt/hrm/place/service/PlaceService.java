package de.hswt.hrm.place.service;

import java.util.Collection;

import javax.inject.Inject;
import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.e4.core.di.annotations.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hswt.hrm.common.Config;
import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.database.exception.ElementNotFoundException;
import de.hswt.hrm.common.database.exception.SaveException;
import de.hswt.hrm.common.locking.jdbc.ILockService;
import de.hswt.hrm.common.locking.jdbc.LockException;
import de.hswt.hrm.place.dao.core.IPlaceDao;
import de.hswt.hrm.place.model.Place;

@Creatable
public class PlaceService {
    private final static Logger LOG = LoggerFactory.getLogger(PlaceService.class);

    private final IPlaceDao dao;
	private final ILockService lockService;
	private final boolean lockingEnabled;
    
    @Inject
	public PlaceService(IPlaceDao placeDao, @Optional ILockService lockService) {
	    this.dao = placeDao;
	    this.lockService = lockService;
	    
	    if (dao == null) {
	        LOG.error("PlaceDao not injected to PlaceService.");
	    }

	    Config cfg = Config.getInstance();
	    lockingEnabled = cfg.getBoolean(Config.Keys.DB_LOCKING);
	    
	    if (lockingEnabled && lockService == null) {
	        LOG.error("Locking is enabled but the LockService was not injected to PlaceService.");
	    }
	    else if (lockService != null) {
	        LOG.info("LockService injected to PlaceService");
	    }
	}
	
	/**
     * @return All places from storage.
     * @throws DatabaseException 
     */
	public Collection<Place> findAll() throws DatabaseException {
		return dao.findAll();
	}
	
	/**
     * @param id of the target place.
     * @return Place with the given id.
     * @throws DatabaseException 
     */
	public Place findById(int id) throws ElementNotFoundException, DatabaseException {
		return dao.findById(id);
	}
	
	/**
     * Add a new place to storage.
     * 
     * @param place Place that should be stored.
     * @return The created place.
     * @throws SaveException If the place could not be inserted.
     */
	public Place insert(Place place) throws SaveException {
		return dao.insert(place);
	}
	
	/**
     * Update an existing place in storage.
     * 
     * @param place Place that should be updated.
     * @throws ElementNotFoundException If the given place is not present in the database.
     * @throws SaveException If the place could not be updated.
     */
	public void update(Place place) throws ElementNotFoundException, SaveException {
	    if (lockingEnabled) {
	        // Check if user has lock for the place
	        if (!lockService.hasLockFor(ILockService.TBL_PLACE, place.getId())) {
	            LOG.error("Current session has no lock for ID + " + place.getId());
	            throw new LockException("Current session has no lock for the given place.");
	        }
	    }
	    
		dao.update(place);
	}
	
	/**
     * Refreshes a given place with values from the database.
     * 
     * @param contact Which gets updated.
     * @throws ElementNotFoundException
     * @throws DatabaseException
     */
	public void refresh(Place place) throws ElementNotFoundException, DatabaseException {
	    Place fromDb = dao.findById(place.getId());
	    
	    place.setArea(fromDb.getArea());
	    place.setCity(fromDb.getCity());
	    place.setLocation(fromDb.getLocation());
	    place.setPlaceName(fromDb.getPlaceName());
	    place.setPostCode(fromDb.getPostCode());
	    place.setStreet(fromDb.getStreet());
	    place.setStreetNo(fromDb.getStreetNo());
	}
}
