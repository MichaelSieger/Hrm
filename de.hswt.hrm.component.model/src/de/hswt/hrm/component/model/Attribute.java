package de.hswt.hrm.component.model;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

public class Attribute {
	private final int id;
	private String name;
	
	public Attribute(final String name) {
		this(-1, name);
	}
	
	public Attribute(final int id, final String name) {
		checkNotNull(name, "Name is mandatory.");
		checkArgument(name.length() > 0, "Name must not be of length 0.");
		
		this.id = id;
		this.name = name;
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
}
