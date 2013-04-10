package de.hswt.hrm.plant.model;

/**
 * Data class for the technical data of a plant.
 * 
 * @author Anton Schreck
 *
 */

public class TechnicalData {
    
    private Double nominalCurrent;
    private Integer voltage;
    
    // optional:
    private String type;
    private Integer airPower, engineSpeed;
    private Double enginePower, ventilatorPower;
    
    // Getters and setters:
    public Double getNominalCurrent() {
        return nominalCurrent;
    }
    public void setNominalCurrent(Double nominalCurrent) {
        this.nominalCurrent = nominalCurrent;
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
    public Integer getAirPower() {
        return airPower;
    }
    public void setAirPower(Integer airPower) {
        this.airPower = airPower;
    }
    public Integer getEngineSpeed() {
        return engineSpeed;
    }
    public void setEngineSpeed(Integer engineSpeed) {
        this.engineSpeed = engineSpeed;
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
