package de.hswt.hrm.contact.dao.jdbc.test;

import static org.junit.Assert.*;

import java.sql.Connection;

import org.junit.Test;

import de.hswt.hrm.common.database.DatabaseFactory;
import de.hswt.hrm.common.database.exception.DatabaseException;

public class DaoTest {

    @Test
    public void test() throws DatabaseException {
        
        Connection con = DatabaseFactory.getConnection();
        
        fail("Not yet implemented");
    }

}
