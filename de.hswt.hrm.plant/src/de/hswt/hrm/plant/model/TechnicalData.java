package de.hswt.hrm.plant.model;

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
    private String type;
    private Integer airPerformance, engineRPM;
    private Double enginePower, ventilatorPower;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getAirPerformance() {
        return airPerformance;
    }

    public void setAirPerformance(Integer airPerformance) {
        this.airPerformance = airPerformance;
    }

    public Integer getEngineRPM() {
        return engineRPM;
    }

    public void setEngineRPM(Integer engineRPM) {
        this.engineRPM = engineRPM;
    }

    public Double getEnginePower() {
        return enginePower;
    }

    public void setEnginePower(Double enginePower) {
        this.enginePower = enginePower;
    }

    public Double getVentilatorPower() {
        return ventilatorPower;
    }

    public void setVentilatorPower(Double ventilatorPower) {
        this.ventilatorPower = ventilatorPower;
    }

}
