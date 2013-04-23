package de.hswt.hrm.plant.model;

import com.google.common.base.Optional;

/**
 * Data class for the technical data of a plant.
 * 
 * @author Anton Schreck
 * 
 */

public class TechnicalData {

    private Double current;
    private Integer voltage;

    // optional:
    private Optional<String> type;
    private Optional<Integer> airPerformance, engineRPM;
    private Optional<Double> enginePower, ventilatorPower;

    // Getters and setters:
    public Double getCurrent() {
        return current;
    }

    public void setCurrent(Double current) {
        this.current = current;
    }

    public Integer getVoltage() {
        return voltage;
    }

    public void setVoltage(Integer voltage) {
        this.voltage = voltage;
    }

    public Optional<String> getType() {
        return type;
    }

    public void setType(Optional<String> type) {
        this.type = type;
    }

    public Optional<Integer> getAirPerformance() {
        return airPerformance;
    }

    public void setAirPerformance(Optional<Integer> airPerformance) {
        this.airPerformance = airPerformance;
    }

    public Optional<Integer> getEngineRPM() {
        return engineRPM;
    }

    public void setEngineRPM(Optional<Integer> engineRPM) {
        this.engineRPM = engineRPM;
    }

    public Optional<Double> getEnginePower() {
        return enginePower;
    }

    public void setEnginePower(Optional<Double> enginePower) {
        this.enginePower = enginePower;
    }

    public Optional<Double> getVentilatorPower() {
        return ventilatorPower;
    }

    public void setVentilatorPower(Optional<Double> ventilatorPower) {
        this.ventilatorPower = ventilatorPower;
    }

}
