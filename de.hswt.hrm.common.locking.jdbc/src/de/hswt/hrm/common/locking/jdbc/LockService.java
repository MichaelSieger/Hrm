package de.hswt.hrm.common.locking.jdbc;

import com.google.common.base.Optional;
import static com.google.common.base.Preconditions.checkArgument;


public class LockService {
    private final String session;
    
    public LockService(final String session) {
        this.session = session;
    }
    
    public Optional<Lock> getLock(final String table, final int id) {
        return Optional.absent();
    }
    
    public void release(Lock lock) {
        
    }
    
    public void release(Optional<Lock> lock) {
        checkArgument(lock.isPresent(), "Cannot release non existing lock.");
        release(lock.get());
    }
    
    // List of possible tables
    public static final String TBL_PLANT = "Plant"; 
}
