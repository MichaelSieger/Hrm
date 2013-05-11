package de.hswt.hrm.common.locking.jdbc;

import com.google.common.base.Optional;

public interface ILockService {

    Optional<Lock> getLock(String table, int fk);

    boolean release(Lock lock);

    boolean release(Optional<Lock> lock);

    // List of possible tables
    final String TBL_PLANT = "Plant";

}