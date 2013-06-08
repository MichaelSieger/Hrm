package de.hswt.hrm.inspection.model;

public class BiologicalRatingCategory {
	private final int id;
	private String name;
	
	public BiologicalRatingCategory(final String name) {
		this(-1, name);
	}
	
	public BiologicalRatingCategory(final int id, final String name) {
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
		this.name = name;
	}
}
