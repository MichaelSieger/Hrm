package de.hswt.hrm.contact.service;

import java.util.Collection;

import org.junit.Test;

import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.contact.model.Contact;

public class ContactServiceTest {

    @Test
    public void testFindAll() throws DatabaseException {
        Collection<Contact> contacts = ContactService.findAll();
    }
}
