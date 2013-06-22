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
     * @param id of the target evaluation.
     * @return Evaluation with the given id.
     * @throws ElementNotFoundException If the given id is not present in the database.
     */
    Summary findById(int id) throws DatabaseException, ElementNotFoundException;
    
    /**
     * Add a new evaluation to storage.
     * 
     * @param evaluation Evaluation that should be stored.
     * @return Newly generated evaluation (also holding the correct id).
     * @throws SaveException If the evaluation could not be inserted.
     */
    Summary insert(Summary evaluation) throws SaveException;
    
    /**
     * Update an existing evaluation in storage.
     * 
     * @param evaluation Evaluation that should be updated.
     * @throws ElementNotFoundException If the given evaluation is not present in the database.
     * @throws SaveException If the evaluation could not be updated.
     */
    void update(Summary evaluation) throws ElementNotFoundException, SaveException;
}
