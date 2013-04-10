package de.hswt.hrm.plant.model;

import java.util.Date;

/**
 * Data class for a specific plant.
 * Contains the representation of the plant and other data, like
 * name, location, next inspection etc.
 * 
 * @author Anton Schreck
 *
 */

public class Plant {
    
    private SchemePart[] composition;
    private String name, location, comments;
    private Date nextInspection;
    private Integer numElem;    // number of plant parts
    private TechnicalData techData;
    // missing: inspections, which have been in the past
    
    // optional:
    private String producer;
    private Integer constYear;  // year of construction
    
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
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
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
    public String getProducer() {
        return producer;
    }
    public void setProducer(String producer) {
        this.producer = producer;
    }
    public Integer getConstYear() {
        return constYear;
    }
    public void setConstYear(Integer constYear) {
        this.constYear = constYear;
    }
    
}
