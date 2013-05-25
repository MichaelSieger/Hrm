package de.hswt.hrm.scheme.model;

import java.util.Collection;

import com.google.common.base.Optional;

import de.hswt.hrm.plant.model.Plant;

public class Scheme {
    private final int id;
    private Plant plant;
    private Collection<SchemeComponent> schemeComponents;
    
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

	public Collection<SchemeComponent> getSchemeComponents() {
		return schemeComponents;
	}

	public void setSchemeComponents(Collection<SchemeComponent> schemeComponents) {
		this.schemeComponents = schemeComponents;
	}

    
}
