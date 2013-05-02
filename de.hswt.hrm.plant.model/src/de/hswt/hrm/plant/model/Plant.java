package de.hswt.hrm.plant.model;

import com.google.common.base.Optional;
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Strings.isNullOrEmpty;

import de.hswt.hrm.place.model.Place;

/**
 * Represents a plant
 */

public class Plant {

    // mandatory fields
    private int id;
    private int inspectionInterval;
    // Laut Anforderung: Anzahl der Elemente (ergibt sich aus der schematischen Bezeichnung) Wie ist
    // das gemeint?
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

    private static final String IS_MANDATORY = "Field is a mandatory.";
    private static final String INVALID_NUMBER = "%d is an invalid number.%n Must be greater 0";

    public Plant(int inspectionInterval, int numberOfElements, final String description,
            final Place place) {

        this(-1, inspectionInterval, numberOfElements, description, place);

    }

    public Plant(int id, int inspectionInterval, int numberOfElements, final String description,
            final Place place) {

        this.id = id;
        setInspectionInterval(inspectionInterval);
        setNumberOfElements(numberOfElements);
        setDescription(description);
        setPlace(place);
    }

    public int getId() {
        return id;
    }

    public int getInspectionInterval() {
        return inspectionInterval;
    }

    public void setInspectionInterval(int inspectionInterval) {
        checkArgument(inspectionInterval > 0, INVALID_NUMBER, inspectionInterval);
        this.inspectionInterval = inspectionInterval;
    }

    // TODO abklären siehe oben
    public int getNumberOfElements() {
        return numberOfElements;
    }

    // TODO abklären siehe oben
    public void setNumberOfElements(int numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        checkArgument(isNullOrEmpty(description), IS_MANDATORY);
        this.description = description;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        checkNotNull(place, IS_MANDATORY);
        this.place = place;
    }

    public Optional<Integer> getConstructionYear() {
        return Optional.fromNullable(constructionYear);
    }

    public void setConstructionYear(int constructionYear) {
        this.constructionYear = constructionYear;
    }

    public Optional<String> getManufactor() {
        return Optional.fromNullable(manufactor);
    }

    public void setManufactor(String manufactor) {
        this.manufactor = manufactor;
    }

    public Optional<String> getType() {
        return Optional.fromNullable(type);
    }

    public void setType(String type) {
        this.type = type;
    }

    public Optional<String> getAirPerformance() {
        return Optional.fromNullable(airPerformance);
    }

    public void setAirPerformance(String airPerformance) {
        this.airPerformance = airPerformance;
    }

    public Optional<String> getMotorPower() {
        return Optional.fromNullable(motorPower);
    }

    public void setMotorPower(String motorPower) {
        this.motorPower = motorPower;
    }

    public Optional<String> getMotorRpm() {
        return Optional.fromNullable(motorRpm);
    }

    public void setMotorRpm(String motorRpm) {
        this.motorRpm = motorRpm;
    }

    public Optional<String> getVentilatorPerformance() {
        return Optional.fromNullable(ventilatorPerformance);
    }

    public void setVentilatorPerformance(String ventilatorPerformance) {
        this.ventilatorPerformance = ventilatorPerformance;
    }

    public Optional<String> getCurrent() {
        return Optional.fromNullable(current);
    }

    public void setCurrent(String current) {
        this.current = current;
    }

    public Optional<String> getVoltage() {
        return Optional.fromNullable(voltage);
    }

    public void setVoltage(String voltage) {
        this.voltage = voltage;
    }

    public Optional<String> getNote() {
        return Optional.fromNullable(note);
    }

    public void setNote(String note) {
        this.note = note;
    }

}
