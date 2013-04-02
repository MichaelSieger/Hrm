package de.hswt.hrm.contact.dao.core;

import java.util.Collection;
import de.hswt.hrm.contact.model.Contact;

/**
 * Defines all the public methods to interact with the storage system of the contact plugin.
 */
public interface IContactDao {
    
    /**
     * @return All contacts from storage.
     */
    public Collection<Contact> findAll();
    
    /**
     * @param id of the target contact.
     * @return Contact with the given id.
     */
    public Contact findById(long id);
    
    // TODO: specify exception that will be thrown when contact already exists
    /**
     * Add a new contact to storage.
     * 
     * @param contact Contact that should be stored.
     */
    public void insert(Contact contact);
    
    // TODO: specify exception that will be thrown when contact does not exist
    /**
     * Update an existing contact in storage.
     * 
     * @param contact Contact that should be updated.
     */
    public void update(Contact contact);
}
