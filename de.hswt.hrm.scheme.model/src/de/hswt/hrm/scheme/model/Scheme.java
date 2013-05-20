package de.hswt.hrm.scheme.model;

import com.google.common.base.Optional;

import de.hswt.hrm.plant.model.Plant;

public class Scheme {
    private final int id;
    private Plant plant;
    
    public Scheme() {
        this(-1);
    }
    
    public Scheme(final int id) {
        this.id = id;
    }
    
    public int getId() {
        return id;
    }
    
    public Optional<Plant> getPlant() {
        return Optional.fromNullable(plant);
    }
    
    public void setPlant(final Plant plant) {
        this.plant = plant;
    }

}
