package de.hswt.hrm.contact.dao.jdbc.test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import de.hswt.hrm.common.database.exception.SaveException;
import de.hswt.hrm.contact.dao.jdbc.ContactDao;
import de.hswt.hrm.contact.model.Contact;
import de.hswt.hrm.test.database.AbstractDatabaseTest;

public class DaoTest extends AbstractDatabaseTest {

    @Test
    public void testInsertContact() throws SaveException {
        final String lastName = "lastname";
        final String firstName = "firstname";
        final String street = "street";
        final String streetNo = "116a";
        final String postCode = "81272";
        final String city = "city";
        
        Contact c = new Contact(lastName, firstName, street, streetNo, postCode, city);
        
        ContactDao dao = new ContactDao();
        dao.insert(c);
        
        // TODO retrieve and compare contact
    }

}
