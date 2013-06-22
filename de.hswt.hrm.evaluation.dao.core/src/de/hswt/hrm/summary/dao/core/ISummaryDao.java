package de.hswt.hrm.summary.dao.core;

import java.util.Collection;

import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.database.exception.ElementNotFoundException;
import de.hswt.hrm.common.database.exception.SaveException;
import de.hswt.hrm.summary.model.Summary;

/**
 * Defines all the public methods to interact with the storage system for activitys.
 */
public interface ISummaryDao {

    /**
     * @return All evaluations from storage.
     */
    Collection<Summary> findAll() throws DatabaseException;

    /**
     * @param id
     *            of the target evaluation.
     * @return Summary with the given id.
     * @throws ElementNotFoundException
     *             If the given id is not present in the database.
     */
    Summary findById(int id) throws DatabaseException, ElementNotFoundException;

    /**
     * Add a new summaryto storage.
     * 
     * @param evaluation
     *            Summary that should be stored.
     * @return Newly generated summary(also holding the correct id).
     * @throws SaveException
     *             If the summarycould not be inserted.
     */
    Summary insert(Summary summary) throws SaveException;

    /**
     * Update an existing summaryin storage.
     * 
     * @param evaluation
     *            Summary that should be updated.
     * @throws ElementNotFoundException
     *             If the given summaryis not present in the database.
     * @throws SaveException
     *             If the summarycould not be updated.
     */
    void update(Summary summary) throws ElementNotFoundException, SaveException;
}
