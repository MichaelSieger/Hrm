package de.hswt.hrm.contact.dao.core;

import java.util.Collection;

import de.hswt.hrm.contact.model.ContactRole;

/**
 * Defines all the public methods to interact with the storage system for contact roles.
 */
public interface IContactRoleDao {
    /**
     * @return All contact roles from storage.
     */
    public Collection<ContactRole> findAll();
    
    /**
     * @param id of the target role.
     * @return Contact role with the given id.
     */
    public ContactRole findById(long id);
    
    // TODO: specify exception that will be thrown when role already exists
    /**
     * Add a new role to storage.
     * 
     * @param role Contact role that should be stored.
     */
    public void insert(ContactRole role);
    
    // TODO: specify exception that will be thrown when role does not exist
    /**
     * Update an existing role in storage.
     * 
     * @param role Contact role that should be updated.
     */
    public void update(ContactRole role);
}
