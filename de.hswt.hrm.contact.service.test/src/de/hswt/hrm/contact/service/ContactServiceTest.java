package de.hswt.hrm.contact.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.util.Collection;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.EclipseContextFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.junit.Test;

import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.contact.dao.core.IContactDao;
import de.hswt.hrm.contact.dao.jdbc.ContactDao;
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
        assertEquals("Phone not set correctly.", expected.getPhone().orNull(), actual.getPhone()
                .orNull());
        assertEquals("Fax not set correctly.", expected.getFax().orNull(), actual.getFax().orNull());
        assertEquals("Mobile not set correctly.", expected.getMobile().orNull(), actual.getMobile()
                .orNull());
        assertEquals("Email not set correctly.", expected.getEmail().orNull(), actual.getEmail()
                .orNull());
    }

    private ContactService createInjectedContactService() throws DatabaseException, SQLException {
        IContactDao dao = new ContactDao();

        IEclipseContext context = EclipseContextFactory.create();
        context.set(IContactDao.class, dao);
        ContactService service = ContextInjectionFactory.make(ContactService.class, context);

        return service;
    }

    @Test
    public void testFindAll() throws DatabaseException, SQLException {
        Contact con1 = new Contact("Sarpei", "Hans", "Some Street", "15", "81934", "Nowhere");
        Contact con2 = new Contact("Wiesel", "Rolf", "Something Avenue", "192", "TX-9921", "Texas");

        ContactService service = createInjectedContactService();
        service.insert(con1);
        service.insert(con2);

        Collection<Contact> contacts = service.findAll();
        assertEquals("Count of retrieved contacts does not match.", 2, contacts.size());
    }

    @Test
    public void testFindById() throws DatabaseException, SQLException {
        Contact expected = new Contact("Sarpei", "Hans", "Some Street", "15", "81934", "Nowhere");
        ContactService service = createInjectedContactService();
        Contact parsed = service.insert(expected);

        Contact requested = service.findById(parsed.getId());
        compareContactFields(expected, requested);
    }

    @Test
    public void testInsertComplete() throws DatabaseException, SQLException {
        Contact expected = new Contact("Sarpei", "Hans", "Some Street", "15", "81934", "Nowhere");
        expected.setPhone("+1 555 1234896");
        expected.setFax("+1 555 5646315");
        expected.setMobile("+1 555 6879464");
        expected.setEmail("hans.sarpei@sarpei.com");

        ContactService service = createInjectedContactService();

        // Check return value from insert
        Contact parsed = service.insert(expected);
        compareContactFields(expected, parsed);
        assertTrue("ID not set correctly.", parsed.getId() >= 0);

        // Request from database
        Contact requested = service.findById(parsed.getId());
        compareContactFields(expected, requested);
        assertEquals("Requested object does not equal parsed one.", parsed, requested);
    }

    @Test
    public void testUpdate() throws DatabaseException, SQLException {
        Contact expected = new Contact("Sarpei", "Hans", "Some Street", "15", "81934", "Nowhere");
        ContactService service = createInjectedContactService();

        Contact parsed = service.insert(expected);

        // We add another contact to ensure that the update affects just one row.
        Contact con2 = new Contact("Other", "Some", "Some Street", "15", "81934", "Nowhere");
        service.insert(con2);

        parsed.setCity("Some City");
        parsed.setEmail("someone@example.com");
        service.update(parsed);

        Contact requested = service.findById(parsed.getId());
        compareContactFields(parsed, requested);
        assertEquals("Requested object does not equal updated one.", parsed, requested);
    }
}
