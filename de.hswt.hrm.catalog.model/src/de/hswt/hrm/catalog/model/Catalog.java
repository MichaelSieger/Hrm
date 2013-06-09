package de.hswt.hrm.catalog.model;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Strings.isNullOrEmpty;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Represents a catalog.
 */
public final class Catalog {
    private int id;
    private String name;
    private Collection<Target> targets;

    private static final String IS_MANDATORY = "Field is a mandatory.";

    public Catalog(final String name) {

        this(-1, name);
    }

    public Catalog(int id, final String name) {

        this.id = id;
        setName(name);
        Collection<Target> targets = new ArrayList<>();
        setTargets(targets);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        checkArgument(!isNullOrEmpty(name), IS_MANDATORY);
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public Collection<Target> getTargets() {
        return targets;
    }

    public void setTargets(Collection<Target> targets) {
        this.targets = targets;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((targets == null) ? 0 : targets.hashCode());
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
        Catalog other = (Catalog) obj;
        if (id != other.id) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        }
        else if (!name.equals(other.name)) {
            return false;
        }
        if (targets == null) {
            if (other.targets != null) {
                return false;
            }
        }
        else if (!targets.equals(other.targets)) {
            return false;
        }
        return true;
    }

}
