package de.hswt.hrm.common.locking.jdbc;

import java.util.UUID;

import org.junit.Test;
import static org.junit.Assert.*;

import com.google.common.base.Optional;

import de.hswt.hrm.test.database.AbstractDatabaseTest;

public class LockTest extends AbstractDatabaseTest {
    private static final String SESSION = UUID.randomUUID().toString();
    private static final String PLANT = "Plant"; 
    
    @Test
    public void testGetLock() {
        LockService service = new LockService(SESSION);
        Optional<Lock> lock = service.getLock(service.TBL_PLANT, 1);
        
        assertTrue("Could not get lock!", lock.isPresent());
        assertEquals("Lock is not for the correct table.", PLANT, lock.get().getTable());
        assertEquals("Lock is not for the correct id.", 1, lock.get().getFk());
    }
    
    @Test
    public void testGetLockTwice() {
        LockService service = new LockService(SESSION);
        Optional<Lock> lock = service.getLock(service.TBL_PLANT, 1);
        assertTrue("Could not get lock!", lock.isPresent());
        
        lock = service.getLock(Service.TBL_PLANT, 1);
        assertFalse("Could get lock to already locked id.", lock.isPresent());
    }
    
    @Test
    public void testReleaseLock() {
        LockService service = new LockService(SESSION);
        Optional<Lock> lock = service.getLock(service.TBL_PLANT, 1);
        assertTrue("Could not get lock!", lock.isPresent());
        
        service.release(lock);
        
        lock = service.getLock(Service.TBL_PLANT, 1);
        assertTrue("Could not get lock for released id.", lock.isPresent());
    }
    
}
