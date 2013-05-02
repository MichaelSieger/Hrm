package de.hswt.hrm.plant.model;

import com.google.common.base.Optional;
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Strings.isNullOrEmpty;

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

    public int getId() {
        return id;
    }

    public int getInspectionInterval() {
        return inspectionInterval;
    }

    public void setInspectionInterval(int inspectionInterval) {
        checkArgument(inspectionInterval > 0,
                "%d is not a valid Interval% Interval must greater than zero", inspectionInterval);
        this.inspectionInterval = inspectionInterval;
    }

    public int getNumberOfElements() {
        return numberOfElements;
    }

    public void setNumberOfElements(int numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public int getConstructionYear() {
        return constructionYear;
    }

    public void setConstructionYear(int constructionYear) {
        this.constructionYear = constructionYear;
    }

    public String getManufactor() {
        return manufactor;
    }

    public void setManufactor(String manufactor) {
        this.manufactor = manufactor;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAirPerformance() {
        return airPerformance;
    }

    public void setAirPerformance(String airPerformance) {
        this.airPerformance = airPerformance;
    }

    public String getMotorPower() {
        return motorPower;
    }

    public void setMotorPower(String motorPower) {
        this.motorPower = motorPower;
    }

    public String getMotorRpm() {
        return motorRpm;
    }

    public void setMotorRpm(String motorRpm) {
        this.motorRpm = motorRpm;
    }

    public String getVentilatorPerformance() {
        return ventilatorPerformance;
    }

    public void setVentilatorPerformance(String ventilatorPerformance) {
        this.ventilatorPerformance = ventilatorPerformance;
    }

    public String getCurrent() {
        return current;
    }

    public void setCurrent(String current) {
        this.current = current;
    }

    public String getVoltage() {
        return voltage;
    }

    public void setVoltage(String voltage) {
        this.voltage = voltage;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public static String getIsMandatory() {
        return IS_MANDATORY;
    }

    private String note;

    private static final String IS_MANDATORY = "Field is a mandatory.";

}
