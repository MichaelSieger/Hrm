package de.hswt.hrm.photo.dao.core;

import java.util.Collection;

import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.database.exception.ElementNotFoundException;
import de.hswt.hrm.common.database.exception.SaveException;
import de.hswt.hrm.photo.model.Photo;

/**
 * Defines all the public methods to interact with the storage system for activitys.
 */
public interface IPhotoDao {

    /**
     * @return All photos from storage.
     */
    Collection<Photo> findAll() throws DatabaseException;

    /**
     * @param id of the target photo.
     * @return Photo with the given id.
     * @throws ElementNotFoundException If the given id is not present in the database.
     */
    Photo findById(int id) throws DatabaseException, ElementNotFoundException;

    /**
     * Add a new photo to storage.
     * 
     * @param photo Photo that should be stored.
     * @return Newly generated photo (also holding the correct id).
     * @throws SaveException If the photo could not be inserted.
     */
    Photo insert(Photo photo) throws SaveException;

    /**
     * Update an existing photo in storage.
     * 
     * @param photo Photo that should be updated.
     * @throws ElementNotFoundException If the given Photo is not present in the database.
     * @throws SaveException If the photo could not be updated.
     */
    void update(Photo photo) throws ElementNotFoundException, SaveException;

    /**
     * Find photos for the given performance.
     * 
     * @param id
     * @return
     * @throws DatabaseException 
     */
	Collection<Photo> findByPerformance(int id) throws DatabaseException;

	/**
	 * Adds the photo to the performance.
	 * 
	 * @param performanceId
	 * @param photo
	 * @throws SaveException
	 * @throws DatabaseException
	 */
	void addPhoto(int performanceId, Photo photo) 
			throws SaveException, DatabaseException;
	
	/**
	 * Removes the photo from the performance.
	 * 
	 * @param performanceId
	 * @param photo
	 * @throws ElementNotFoundException If photo is not assigned to the performance.
	 * @throws DatabaseException
	 */
	void removePhoto(int performanceId, Photo photo)
			throws ElementNotFoundException, DatabaseException;
}
