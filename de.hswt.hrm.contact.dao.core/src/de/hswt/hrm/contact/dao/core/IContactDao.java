package de.hswt.hrm.contact.dao.core;

import java.util.Collection;

import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.database.exception.ElementNotFoundException;
import de.hswt.hrm.common.database.exception.SaveException;
import de.hswt.hrm.contact.model.Contact;

/**
 * Defines all the public methods to interact with the storage system for contacts.
 */
public interface IContactDao {
    
    /**
     * @return All contacts from storage.
     */
    public Collection<Contact> findAll() throws DatabaseException;
    
    /**
     * @param id of the target contact.
     * @return Contact with the given id.
     * @throws ElementNotFoundException If the given id is not present in the database.
     */
    public Contact findById(int id) throws DatabaseException, ElementNotFoundException;
    
    /**
     * Add a new contact to storage.
     * 
     * @param contact Contact that should be stored.
     * @return Newly generated contact (also holding the correct id).
     * @throws SaveException If the contact could not be inserted.
     */
    public Contact insert(Contact contact) throws SaveException;
    
    /**
     * Update an existing contact in storage.
     * 
     * @param contact Contact that should be updated.
     * @throws ElementNotFoundException If the given contact is not present in the database.
     * @throws SaveException If the contact could not be updated.
     */
    public void update(Contact contact) throws ElementNotFoundException, SaveException;
}
