package de.hswt.hrm.place.service;

import java.util.Collection;

import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.database.exception.ElementNotFoundException;
import de.hswt.hrm.common.database.exception.SaveException;
import de.hswt.hrm.place.dao.core.IPlaceDao;
import de.hswt.hrm.place.dao.jdbc.PlaceDao;
import de.hswt.hrm.place.model.Place;

public class PlaceService {
	
	private PlaceService() { }
	
	private static IPlaceDao dao = new PlaceDao();
	
	/**
     * @return All places from storage.
     * @throws DatabaseException 
     */
	public static Collection<Place> findAll() throws DatabaseException {
		return dao.findAll();
	}
	
	/**
     * @param id of the target place.
     * @return Place with the given id.
     * @throws DatabaseException 
     */
	public static Place findById(int id) throws ElementNotFoundException, DatabaseException {
		return dao.findById(id);
	}
	
	/**
     * Add a new place to storage.
     * 
     * @param place Place that should be stored.
     * @return The created place.
     * @throws SaveException If the place could not be inserted.
     */
	public static Place insert(Place place) throws SaveException {
		return dao.insert(place);
	}
	
	/**
     * Update an existing place in storage.
     * 
     * @param place Place that should be updated.
     * @throws ElementNotFoundException If the given place is not present in the database.
     * @throws SaveException If the place could not be updated.
     */
	public static void update(Place place) throws ElementNotFoundException, SaveException {
		dao.update(place);
	}
	
	/**
     * Refreshes a given place with values from the database.
     * 
     * @param contact Which gets updated.
     * @throws ElementNotFoundException
     * @throws DatabaseException
     */
	public static void refresh(Place place) throws ElementNotFoundException, DatabaseException {
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
