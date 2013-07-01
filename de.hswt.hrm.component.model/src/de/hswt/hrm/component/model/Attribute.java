package de.hswt.hrm.component.model;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.io.Serializable;

public class Attribute implements Serializable {
	private static final long serialVersionUID = -730240565044775148L;
	
	private final int id;
    private final Component component;
    private String name;

    public Attribute(final String name, final Component component) {
        this(-1, name, component);
    }

    public Attribute(final int id, final String name, final Component component) {
        checkNotNull(name, "Name is mandatory.");
        checkArgument(name.length() > 0, "Name must not be of length 0.");

        this.id = id;
        this.name = name;
        this.component = component;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        checkNotNull(name, "Name is mandatory.");
        checkArgument(name.length() > 0, "Name must not be of length 0.");
        this.name = name;
    }

    public Component getComponent() {
        return component;
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((component == null) ? 0 : component.hashCode());
        result = prime * result + id;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
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
        Attribute other = (Attribute) obj;
        if (component == null) {
            if (other.component != null) {
                return false;
            }
        }
        else if (!component.equals(other.component)) {
            return false;
        }
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
        return true;
    }

}
