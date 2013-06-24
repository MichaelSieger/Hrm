package de.hswt.hrm.common.observer;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Preconditions;

public class Observable<T> {
    
    private T item; 
    private List<Observer<T>> observers = new ArrayList<>();

    public void addObserver(Observer<T> obs){
        obs.changed(item);
        observers.add(obs);
    }
    
    public T get(){
        return item;
    }
    
    public void set(T item){
        if(this.item != item){
            this.item = item;
            notifyDataChanged();
        }
    }
    
    public void notifyDataChanged(){
        for(Observer<T> o : observers){
            o.changed(item);
        }
    }

}
