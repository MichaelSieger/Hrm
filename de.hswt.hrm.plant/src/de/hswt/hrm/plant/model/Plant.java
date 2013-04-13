package de.hswt.hrm.plant.model;

import java.util.Date;

/**
 * Data class for a specific plant. Contains the representation of the plant and other data, like
 * name, location, next inspection etc.
 * 
 * @author Anton Schreck
 * 
 */

public class Plant {

    private SchemePart[] composition;
    private String name, place, comments;
    private Date nextInspection;
    private Integer numElem;
    private TechnicalData techData;
    // missing: inspections, which have been in the past

    // optional:
    private String manufacturer;
    private Integer constYear; // year of construction

    // Getters and setters:
    public SchemePart[] getComposition() {
        return composition;
    }

    public void setComposition(SchemePart[] composition) {
        this.composition = composition;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Date getNextInspection() {
        return nextInspection;
    }

    public void setNextInspection(Date nextInspection) {
        this.nextInspection = nextInspection;
    }

    public Integer getNumElem() {
        return numElem;
    }

    public void setNumElem(Integer numElem) {
        this.numElem = numElem;
    }

    public TechnicalData getTechData() {
        return techData;
    }

    public void setTechData(TechnicalData techData) {
        this.techData = techData;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Integer getConstYear() {
        return constYear;
    }

    public void setConstYear(Integer constYear) {
        this.constYear = constYear;
    }

}
