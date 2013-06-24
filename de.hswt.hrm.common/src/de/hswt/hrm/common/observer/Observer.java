package de.hswt.hrm.common.observer;

public interface Observer<T> {

    void changed(T item);
    
}
