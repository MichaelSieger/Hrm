package de.hswt.hrm.plant.model;

/**
 * Represents a part of a plant with its PlantImage,
 * and the position in the grid.
 * 
 * @author Anton Schreck
 *
 */

public class PlantPart {
    
    private int xPos, yPos;
    private PlantImage imag;
    private Category category;
    
    public int getxPos() {
        return xPos;
    }
    
    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
    }

    public PlantImage getImag() {
        return imag;
    }

    public void setImag(PlantImage imag) {
        this.imag = imag;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

        

}
