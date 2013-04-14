package de.hswt.hrm.contact.dao.jdbc.test;

import static org.junit.Assert.*;

import org.junit.Test;

import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.database.exception.ElementNotFoundException;
import de.hswt.hrm.contact.dao.jdbc.ContactDao;
import de.hswt.hrm.contact.model.Contact;
import de.hswt.hrm.test.database.AbstractDatabaseTest;

public class DaoTest extends AbstractDatabaseTest {

    @Test
    public void testInsertContact() throws ElementNotFoundException, DatabaseException {
        final String lastName = "lastname";
        final String firstName = "firstname";
        final String street = "street";
        final String streetNo = "116a";
        final String postCode = "81272";
        final String city = "city";
        
        final String shortcut = "shortcut";
        final String phone = "+49 89 123456";
        final String fax = "+49 89 123457";
        final String mobile = "+49 171 9999999";
        final String email = "name@example.com";
        
        Contact c = new Contact(lastName, firstName, street, streetNo, postCode, city);
        c.setShortcut(shortcut);
        c.setPhone(phone);
        c.setFax(fax);
        c.setMobile(mobile);
        c.setEmail(email);
        
        ContactDao dao = new ContactDao();
        Contact inserted = dao.insert(c);
        assertTrue("ID is invalid.", inserted.getId() >= 0);
        assertEquals("Last name set wrong.", lastName, inserted.getLastName());
        assertEquals("First name set wrong.", firstName, inserted.getFirstName());
        assertEquals("Street set wrong.", street, inserted.getStreet());
        assertEquals("Street number set wrong.", streetNo, inserted.getStreetNo());
        assertEquals("PostCode set wrong.", postCode, inserted.getPostCode());
        assertEquals("City set wrong.", city, inserted.getCity());
        assertEquals("Shortcut set wrong.", shortcut, inserted.getShortcut().orNull());
        assertEquals("Phone set wrong.", phone, inserted.getPhone().orNull());
        assertEquals("Fax set wrong.", fax, inserted.getFax().orNull());
        assertEquals("Mobile set wrong.", mobile, inserted.getMobile().orNull());
        assertEquals("Email set wrong.", email, inserted.getEmail().orNull());
        
        // Freshly retrieve vom DB
        Contact requested = dao.findById(inserted.getId());
        
        // Check fields
        assertEquals("Last name not saved correctly.", lastName, requested.getLastName());
        assertEquals("First name not saved correctly.", firstName, requested.getFirstName());
        assertEquals("Street not saved correctly.", street, requested.getStreet());
        assertEquals("Street number not saved correctly.", streetNo, requested.getStreetNo());
        assertEquals("PostCode not saved correctly.", postCode, requested.getPostCode());
        assertEquals("City not saved correctly.", city, requested.getCity());
        assertEquals("Shortcut set wrong.", shortcut, requested.getShortcut().orNull());
        assertEquals("Phone set wrong.", phone, requested.getPhone().orNull());
        assertEquals("Fax set wrong.", fax, requested.getFax().orNull());
        assertEquals("Mobile set wrong.", mobile, requested.getMobile().orNull());
        assertEquals("Email set wrong.", email, requested.getEmail().orNull());
        
        // Check equals
        assertTrue("Queried object does not equal inserted one.", requested.equals(inserted));
    }

}
