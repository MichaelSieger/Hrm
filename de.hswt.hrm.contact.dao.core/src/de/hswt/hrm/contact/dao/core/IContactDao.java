package de.hswt.hrm.contact.dao.core;

import java.util.Collection;
import de.hswt.hrm.contact.model.Contact;

public interface IContactDao {
    
    /**
     * @return All contacts from the database.
     */
    public Collection<Contact> findAll();
}
