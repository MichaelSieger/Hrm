package de.hswt.hrm.common.locking.jdbc;

import com.google.common.base.Optional;

public interface ILockService {

    boolean hasLockFor(String table, int fk);
    
    Optional<Lock> getLock(String table, int fk);

    boolean release(Lock lock);

    boolean release(Optional<Lock> lock);

    // List of possible tables
    String TBL_PLACE = "Place";
    String TBL_PLANT = "Plant";

}