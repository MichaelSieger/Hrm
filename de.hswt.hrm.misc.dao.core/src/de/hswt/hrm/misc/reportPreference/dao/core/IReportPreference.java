package de.hswt.hrm.misc.reportPreference.dao.core;

import java.util.Collection;

import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.database.exception.ElementNotFoundException;
import de.hswt.hrm.common.database.exception.SaveException;
import de.hswt.hrm.misc.reportPreference.model.ReportPreference;

/**
 * Defines all the public methods to interact with the storage system for reportPreferences.
 */
public interface IReportPreference {

    /**
     * @return All reportPreferences from storage.
     */
    Collection<ReportPreference> findAll() throws DatabaseException;

    /**
     * @param id
     *            of the reportPreference.
     * @return ReportPreference with the given id.
     * @throws ElementNotFoundException
     *             If the given id is not present in the database.
     */
    ReportPreference findById(int id) throws DatabaseException, ElementNotFoundException;

    /**
     * Add a new reportPreference to storage.
     * 
     * @param ReportPreference
     *            that should be stored.
     * @return Newly generated reportPreference(also holding the correct id).
     * @throws SaveException
     *             If the reportPreference could not be inserted.
     */
    ReportPreference insert(ReportPreference reportPreference) throws SaveException;

    /**
     * Update an existing reportPreference in storage.
     * 
     * @param ReportPreference
     *            that should be updated.
     * @throws ElementNotFoundException
     *             If the given reportPreference is not present in the database.
     * @throws SaveException
     *             If the reportPreference could not be updated.
     */
    void update(ReportPreference reportPreference) throws ElementNotFoundException, SaveException;
}
