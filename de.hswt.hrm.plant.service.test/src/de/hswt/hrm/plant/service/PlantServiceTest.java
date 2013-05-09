package de.hswt.hrm.plant.service;

import org.junit.Test;
import static org.junit.Assert.*;

import de.hswt.hrm.plant.model.Plant;

public class PlantServiceTest {
    
    private void comparePlantFields(final Plant expected, final Plant actual) {
        assertEquals("AirPerformance not set correclty.", 
                expected.getAirPerformance().orNull(), expected.getAirPerformance().orNull());
        assertEquals("ConstructionYear not set correctly.",
                expected.getConstructionYear().orNull(), expected.getConstructionYear().orNull());
        assertEquals("Current not set correclty.",
                expected.getCurrent().orNull(), expected.getCurrent().orNull());
        assertEquals("Description not set correctly.",
                expected.getDescription(), expected.getDescription());
        assertEquals("InspectionInterval not set correctly.",
                expected.getInspectionInterval(), expected.getInspectionInterval());
        assertEquals("Manufactor not set correctly.",
                expected.getManufactor().orNull(), expected.getManufactor().orNull());
        assertEquals("MotorPower not set correctly.",
                expected.getMotorPower().orNull(), expected.getMotorPower().orNull());
        assertEquals("MotorRpm not set correctly.",
                expected.getMotorRpm().orNull(), expected.getMotorRpm().orNull());
        assertEquals("Note not set correctly.", 
                expected.getNote().orNull(), expected.getNote().orNull());
        assertEquals("NumberOfElements not set correctly.", 
                expected.getNumberOfElements(), expected.getNumberOfElements());
        assertEquals("Place not set correctly.", 
                expected.getPlace().orNull(), expected.getPlace().orNull());
        assertEquals("Type not set correctly.", 
                expected.getType().orNull(), expected.getType().orNull());
        assertEquals("VentilatorPerformance not set correctly.", 
                expected.getVentilatorPerformance().orNull(), expected.getVentilatorPerformance().orNull());
        assertEquals("Voltage not set correctly.", 
                expected.getVoltage().orNull(), expected.getVoltage().orNull());
    }
    
    @Test
    public void testFindAll() {
        Plant plant1 = new Plant(6, "Test plant");
        Plant plant2 = new Plant(12, "Another test plant");
    }
    
    @Test
    public void testFindById() {
        
    }
    
    @Test
    public void testUpdate() {
        
    }
}
