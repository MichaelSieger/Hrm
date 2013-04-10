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

}
