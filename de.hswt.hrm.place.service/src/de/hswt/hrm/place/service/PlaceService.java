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
	
	public static Collection<Place> findAll() throws DatabaseException {
		return dao.findAll();
	}
	
	public static Place findById(int id) throws ElementNotFoundException, DatabaseException {
		return dao.findById(id);
	}
	
	public static Place insert(Place place) throws SaveException {
		return dao.insert(place);
	}
	
	public static void update(Place place) throws ElementNotFoundException, SaveException {
		dao.update(place);
	}
}
