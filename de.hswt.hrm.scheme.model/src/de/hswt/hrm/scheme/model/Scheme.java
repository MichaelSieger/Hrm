package de.hswt.hrm.scheme.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

import com.google.common.base.Optional;

import de.hswt.hrm.plant.model.Plant;

public class Scheme {
    private final int id;
    private Plant plant;
	private Timestamp timestamp;
    private Collection<SchemeComponent> schemeComponents;
    
    public Scheme(Plant plant) {
        this(-1, plant, null);
	}
    
    public Scheme(final int id, final Plant plant, final Timestamp timestamp){
        this(id, plant, timestamp, new ArrayList<SchemeComponent>());
    }
    
    public Scheme(final int id, final Plant plant, final Timestamp timestamp, final Collection<SchemeComponent> schemeComponents) {
        this.id = id;
        setPlant(plant);
        setTimestamp(timestamp);
        setSchemeComponents(schemeComponents);
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

    public Optional<Timestamp> getTimestamp() {
        return Optional.fromNullable(timestamp);
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + ((plant == null) ? 0 : plant.hashCode());
        result = prime * result + ((timestamp == null) ? 0 : timestamp.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Scheme other = (Scheme) obj;
        if (id != other.id) {
            return false;
        }
        if (plant == null) {
            if (other.plant != null) {
                return false;
            }
        }
        else if (!plant.equals(other.plant)) {
            return false;
        }
        if (timestamp == null) {
            if (other.timestamp != null) {
                return false;
            }
        }
        else if (!timestamp.equals(other.timestamp)) {
            return false;
        }
        return true;
    }
}
