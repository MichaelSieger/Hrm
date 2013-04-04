package de.hswt.hrm.plant.model;

/**
 * Representation of a specific plant.
 * Contains an array of PlantParts and two arrays for the positions of the PlantParts.
 * 
 * @author Anton Schreck
 *
 */

public class PlantRep {
    
    private PlantPart[] parts;
    private int[] xPos, yPos;   //Position in the grid
    
    public PlantPart[] getParts() {
        return parts;
    }
    
    public void setParts(PlantPart[] parts) {
        this.parts = parts;
    }

    public int[] getxPos() {
        return xPos;
    }

    public void setxPos(int[] xPos) {
        this.xPos = xPos;
    }

    public int[] getyPos() {
        return yPos;
    }

    public void setyPos(int[] yPos) {
        this.yPos = yPos;
    }
    
}
