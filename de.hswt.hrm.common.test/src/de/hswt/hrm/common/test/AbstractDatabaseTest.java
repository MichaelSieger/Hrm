package de.hswt.hrm.common.test;

import org.junit.AfterClass;
import org.junit.BeforeClass;

/**
 * Tests that need a database can inherit from this class which automatically creates a
 * test database and fills in some test data.
 * The test database is automatically deleted after all tests have run.
 * 
 * <b>Attention:</b>
 * The same database is used for all tests within this class. If you want to reset the
 * database after or before executing a specific test, use the "resetDatabase" method.
 * But don't forget that this will result in a delete and complete recreation of the
 * test database. 
 */
public abstract class AbstractDatabaseTest {

    @BeforeClass
    public void createDatabase() {
        
    }
    
    @AfterClass
    public void dropDatabase() {
        
    }
    
    public void resetDatabase() {
        dropDatabase();
        createDatabase();
    }
}
