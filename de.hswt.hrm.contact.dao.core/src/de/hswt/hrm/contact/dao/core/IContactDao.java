package de.hswt.hrm.contact.dao.core;

import java.util.Collection;
import de.hswt.hrm.contact.model.Contact;

/**
 * Defines all the public methods to interact with the storage system of the contact plugin.
 */
public interface IContactDao {
    
    /**
     * @return All contacts from the database.
     */
    public Collection<Contact> findAll();
    
    /**
     * @param id of the target contact.
     * @return Contact with the given id.
     */
    public Contact findById(long id);
}
