package de.hswt.hrm.inspection.model;

public class Inspection {
	private final int id;
	
	public Inspection() {
		this(-1);
	}
	
	public Inspection(final int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
}
