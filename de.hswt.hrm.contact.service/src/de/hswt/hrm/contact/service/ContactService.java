package de.hswt.hrm.contact.service;

import java.util.Collection;

import de.hswt.hrm.common.database.exception.ElementNotFoundException;
import de.hswt.hrm.common.database.exception.SaveException;
import de.hswt.hrm.common.exception.NotImplementedException;
import de.hswt.hrm.contact.model.Contact;

/**
 * Service class which should be used to interact with the storage system for contacts.
 */
public class ContactService {
    /**
     * @return All contacts from storage.
     */
    public Collection<Contact> findAll() {
        throw new NotImplementedException();
    }
    
    /**
     * @param id of the target contact.
     * @return Contact with the given id.
     * @throws ElementNotFoundException If the given id is not present in the database.
     */
    public Contact findById(long id) throws ElementNotFoundException {
        throw new NotImplementedException();
    }
    
    /**
     * Add a new contact to storage.
     * 
     * @param contact Contact that should be stored.
     * @throws SaveException If the contact could not be inserted.
     */
    public void insert(Contact contact) throws SaveException {
        throw new NotImplementedException();
    }
    
    /**
     * Update an existing contact in storage.
     * 
     * @param contact Contact that should be updated.
     * @throws ElementNotFoundException If the given contact is not present in the database.
     * @throws SaveException If the contact could not be updated.
     */
    public void update(Contact contact) throws ElementNotFoundException, SaveException {
        throw new NotImplementedException();
    }
}
