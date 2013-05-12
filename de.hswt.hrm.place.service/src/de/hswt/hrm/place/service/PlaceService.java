package de.hswt.hrm.place.service;

import java.util.Collection;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.e4.core.di.annotations.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.database.exception.ElementNotFoundException;
import de.hswt.hrm.common.database.exception.SaveException;
import de.hswt.hrm.common.locking.jdbc.ILockService;
import de.hswt.hrm.common.locking.jdbc.LockService;
import de.hswt.hrm.place.dao.core.IPlaceDao;
import de.hswt.hrm.place.dao.jdbc.PlaceDao;
import de.hswt.hrm.place.model.Place;

@Creatable
public class PlaceService {
    private final static Logger LOG = LoggerFactory.getLogger(PlaceService.class);
    
    @Inject
    @Optional
	private ILockService lockService;
    
    private IPlaceDao dao;
    
    @Inject
	public PlaceService(IPlaceDao placeDao) {
	    this.dao = placeDao;
	}
	
	/**
     * @return All places from storage.
     * @throws DatabaseException 
     */
	public Collection<Place> findAll() throws DatabaseException {
	    if (lockService != null) {
	        LOG.debug("Lock service injected.");
	    }
	    else {
	        LOG.debug("Lock service NOT injected.");
	    }
	    if (dao != null) {
	        LOG.debug("place dao injected.");
	    }
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
