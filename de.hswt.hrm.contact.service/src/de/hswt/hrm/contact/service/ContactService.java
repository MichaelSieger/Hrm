package de.hswt.hrm.contact.service;

import java.util.Collection;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Creatable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.database.exception.ElementNotFoundException;
import de.hswt.hrm.common.database.exception.SaveException;
import de.hswt.hrm.contact.dao.core.IContactDao;
import de.hswt.hrm.contact.model.Contact;

/**
 * Service class which should be used to interact with the storage system for contacts.
 */
@Creatable
public class ContactService {

    private final static Logger LOG = LoggerFactory.getLogger(ContactService.class);

    private final IContactDao dao;

    @Inject
    public ContactService(IContactDao dao) {
        this.dao = dao;

        if (dao == null) {
            LOG.error("ContactDao not injected to ContactService.");
        }
    }

    /**
     * @return All contacts from storage.
     * @throws DatabaseException
     */
    public Collection<Contact> findAll() throws DatabaseException {
        return dao.findAll();
    }

    /**
     * @param id
     *            of the target contact.
     * @return Contact with the given id.
     * @throws DatabaseException
     */
    public Contact findById(int id) throws DatabaseException {
        return dao.findById(id);
    }

    /**
     * Add a new contact to storage.
     * 
     * @param contact
     *            Contact that should be stored.
     * @return The created contact.
     * @throws SaveException
     *             If the contact could not be inserted.
     */
    public Contact insert(Contact contact) throws SaveException {
        return dao.insert(contact);
    }

    /**
     * Update an existing contact in storage.
     * 
     * @param contact
     *            Contact that should be updated.
     * @throws ElementNotFoundException
     *             If the given contact is not present in the database.
     * @throws SaveException
     *             If the contact could not be updated.
     */
    public void update(Contact contact) throws ElementNotFoundException, SaveException {
        dao.update(contact);
    }

    /**
     * Refreshes a given contact with values from the database.
     * 
     * @param contact
     * @throws ElementNotFoundException
     * @throws DatabaseException
     */
    public void refresh(Contact contact) throws ElementNotFoundException, DatabaseException {
        Contact fromDb = dao.findById(contact.getId());

        contact.setCity(fromDb.getCity());
        contact.setEmail(fromDb.getEmail().orNull());
        contact.setFax(fromDb.getFax().orNull());
        contact.setName(fromDb.getName());
        contact.setMobile(fromDb.getMobile().orNull());
        contact.setPhone(fromDb.getPhone().orNull());
        contact.setPostCode(fromDb.getPostCode());
        contact.setShortcut(fromDb.getShortcut().orNull());
        contact.setStreet(fromDb.getStreet());
        contact.setStreetNo(fromDb.getStreetNo());
    }
}
