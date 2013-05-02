package de.hswt.hrm.plant.model;

import de.hswt.hrm.place.model.Place;

/**
 * Represents a plant
 */

public class Plant {

    // mandatory fields
    private int id;
    private int inspectionInterval;

    /*
     * Laut Anforderung: Anzahl der Elemente (ergibt sich aus der schematischen Bezeichnung) Wie ist
     * das gemeint?
     */
    private int numberOfElements;
    private String description;
    // eager loading
    private Place place;

    // optional
    private int constructionYear;

    private String manufactor;
    private String type;
    private String airPerformance;
    private String motorPower;
    private String motorRpm;
    private String ventilatorPerformance;
    private String current;
    private String voltage;
    private String note;

}
