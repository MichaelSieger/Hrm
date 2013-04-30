//package de.hswt.hrm.plant.model;
//
//import java.util.Date;
//
//import com.google.common.base.Optional;
//
///**
// * Data class for a specific plant. Contains the representation of the plant and
// * other data, like name, location, next inspection etc.
// * 
// * @author Anton Schreck
// * 
// */
//
//public class Plant {
//
//	private String name, place, comments;
//	private Date nextInspection;
//	private Integer numElem;
//	// private TechnicalData techData;
//	// missing: inspections, which have been in the past
//
//	// optional:
//	private Optional<String> manufacturer;
//	private Optional<Integer> constYear; // year of construction
//
//	// Getters and setters:
//	public SchemePart[] getComposition() {
//		return composition;
//	}
//
//	public void setComposition(SchemePart[] composition) {
//		this.composition = composition;
//	}
//
//	public String getName() {
//		return name;
//	}
//
//	public void setName(String name) {
//		this.name = name;
//	}
//
//	public String getPlace() {
//		return place;
//	}
//
//	public void setPlace(String place) {
//		this.place = place;
//	}
//
//	public String getComments() {
//		return comments;
//	}
//
//	public void setComments(String comments) {
//		this.comments = comments;
//	}
//
//	public Date getNextInspection() {
//		return nextInspection;
//	}
//
//	public void setNextInspection(Date nextInspection) {
//		this.nextInspection = nextInspection;
//	}
//
//	public Integer getNumElem() {
//		return numElem;
//	}
//
//	public void setNumElem(Integer numElem) {
//		this.numElem = numElem;
//	}
//
//	public TechnicalData getTechData() {
//		return techData;
//	}
//
//	public void setTechData(TechnicalData techData) {
//		this.techData = techData;
//	}
//
//	public Optional<String> getManufacturer() {
//		return manufacturer;
//	}
//
//	public void setManufacturer(Optional<String> manufacturer) {
//		this.manufacturer = manufacturer;
//	}
//
//	public Optional<Integer> getConstYear() {
//		return constYear;
//	}
//
//	public void setConstYear(Optional<Integer> constYear) {
//		this.constYear = constYear;
//	}
//
//}
