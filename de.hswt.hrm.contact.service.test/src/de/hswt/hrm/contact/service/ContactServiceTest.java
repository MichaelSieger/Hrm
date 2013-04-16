package de.hswt.hrm.contact.service;

import java.util.Collection;

import org.junit.Test;
import static org.junit.Assert.*;

import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.contact.model.Contact;
import de.hswt.hrm.test.database.AbstractDatabaseTest;

public class ContactServiceTest extends AbstractDatabaseTest {

    private void compareContactFields(final Contact expected, final Contact actual) {
        assertEquals("LastName not set correctly.", expected.getLastName(), actual.getLastName());
        assertEquals("FirstName not set correctly.", expected.getFirstName(), actual.getFirstName());
        assertEquals("Street not set correctly.", expected.getStreet(), actual.getStreet());
        assertEquals("StreetNo not set correctly.", expected.getStreetNo(), actual.getStreetNo());
        assertEquals("PostCode not set correctly.", expected.getPostCode(), actual.getPostCode());
        assertEquals("City not set correctly.", expected.getCity(), actual.getCity());
        assertEquals("Phone not set correctly.", expected.getPhone().orNull(), actual.getPhone().orNull());
        assertEquals("Fax not set correctly.", expected.getFax().orNull(), actual.getFax().orNull());
        assertEquals("Mobile not set correctly.", expected.getMobile().orNull(), actual.getMobile().orNull());
        assertEquals("Email not set correctly.", expected.getEmail().orNull(), actual.getEmail().orNull());
    }
    
    @Test
    public void testFindAll() throws DatabaseException {
        Contact con1 = new Contact("Sarpei", "Hans", "Some Street", "15", "81934", "Nowhere");
        Contact con2 = new Contact("Wiesel", "Rolf", "Something Avenue", "192", "TX-9921", "Texas");
        ContactService.insert(con1);
        ContactService.insert(con2);
        
        Collection<Contact> contacts = ContactService.findAll();
        assertEquals("Count of retrieved contacts does not match.", 2, contacts.size());
    }
    
    @Test
    public void testFindById() throws DatabaseException {
        Contact expected = new Contact("Sarpei", "Hans", "Some Street", "15", "81934", "Nowhere");
        Contact parsed = ContactService.insert(expected);
        
        Contact requested = ContactService.findById(parsed.getId());
        compareContactFields(expected, requested);
    }
    
    @Test
    public void testInsertComplete() throws DatabaseException {
        Contact expected = new Contact("Sarpei", "Hans", "Some Street", "15", "81934", "Nowhere");
        expected.setPhone("+1 555 1234896");
        expected.setFax("+1 555 5646315");
        expected.setMobile("+1 555 6879464");
        expected.setEmail("hans.sarpei@sarpei.com");
        
        // Check return value from insert
        Contact parsed = ContactService.insert(expected);
        compareContactFields(expected, parsed);
        assertTrue("ID not set correctly.", parsed.getId() >= 0);
        
        // Request from database
        Contact requested = ContactService.findById(parsed.getId());
        compareContactFields(expected, requested);
        assertEquals("Requested object does not equal parsed one." ,parsed, requested);
    }
    
    @Test
    public void testUpdate() throws DatabaseException {
        Contact expected = new Contact("Sarpei", "Hans", "Some Street", "15", "81934", "Nowhere");
        Contact parsed = ContactService.insert(expected);
        
        parsed.setCity("Some City");
        parsed.setEmail("someone@example.com");
        ContactService.update(parsed);
        
        Contact requested = ContactService.findById(parsed.getId());
        compareContactFields(parsed, requested);
        assertEquals("Requested object does not equal updated one.", parsed, requested);
    }
}
