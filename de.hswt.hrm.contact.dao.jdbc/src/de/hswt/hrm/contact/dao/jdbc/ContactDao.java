package de.hswt.hrm.contact.dao.jdbc;

import java.util.Collection;

import de.hswt.hrm.common.database.exception.ElementNotFoundException;
import de.hswt.hrm.common.database.exception.SaveException;
import de.hswt.hrm.contact.model.Contact;
import de.hswt.hrm.contact.dao.core.IContactDao;

public class ContactDao implements IContactDao {

    @Override
    public Collection<Contact> findAll() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Contact findById(long id) throws ElementNotFoundException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void insert(Contact contact) throws SaveException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void update(Contact contact) throws ElementNotFoundException, SaveException {
        // TODO Auto-generated method stub
        
    }

}
