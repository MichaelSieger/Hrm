package de.hswt.hrm.contact.service;

import java.util.Collection;

import org.eclipse.e4.core.di.annotations.Creatable;

import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.database.exception.ElementNotFoundException;
import de.hswt.hrm.common.database.exception.SaveException;
import de.hswt.hrm.contact.dao.core.IContactDao;
import de.hswt.hrm.contact.dao.jdbc.ContactDao;
import de.hswt.hrm.contact.model.Contact;

/**
 * Service class which should be used to interact with the storage system for contacts.
 */
@Creatable
public class ContactService {

    private ContactService() {
    };

    // @Inject
    private static IContactDao dao = new ContactDao();

    /**
     * @return All contacts from storage.
     * @throws DatabaseException
     */
    public static Collection<Contact> findAll() throws DatabaseException {
        return dao.findAll();
    }

    /**
     * @param id
     *            of the target contact.
     * @return Contact with the given id.
     * @throws DatabaseException
     */
    public static Contact findById(int id) throws DatabaseException {
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
    public static Contact insert(Contact contact) throws SaveException {
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
    public static void update(Contact contact) throws ElementNotFoundException, SaveException {
        dao.update(contact);
    }

    /**
     * Refreshes a given contact with values from the database.
     * 
     * @param contact
     * @throws ElementNotFoundException
     * @throws DatabaseException
     */
    public static void refresh(Contact contact) throws ElementNotFoundException, DatabaseException {
        Contact fromDb = dao.findById(contact.getId());

        contact.setCity(fromDb.getCity());
        contact.setEmail(fromDb.getEmail().orNull());
        contact.setFax(fromDb.getFax().orNull());
        contact.setFirstName(fromDb.getFirstName());
        contact.setLastName(fromDb.getLastName());
        contact.setMobile(fromDb.getMobile().orNull());
        contact.setPhone(fromDb.getPhone().orNull());
        contact.setPostCode(fromDb.getPostCode());
        contact.setShortcut(fromDb.getShortcut().orNull());
        contact.setStreet(fromDb.getStreet());
        contact.setStreetNo(fromDb.getStreetNo());
    }
}
