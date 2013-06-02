package de.hswt.hrm.common.database;

import org.junit.Test;

public class DatabaseFactory {
    
    @Test
    public void testTransactionHandling() {
        // Sadly this is a bit tricky to test as our implementation relies on
        // the static method ConnectionManager.getConnection()
        // so, we have to find a way to get rid of it somehow, or refactor the 
        // code to get a better testable implementation...
        
        // FIXME: Implement transaction test!
    }
}
