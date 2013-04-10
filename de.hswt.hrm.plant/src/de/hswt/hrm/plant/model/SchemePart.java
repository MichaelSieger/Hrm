package de.hswt.hrm.plant.model;

/**
 * Represents a part of a scheme,
 * and the position in the grid.
 * 
 * @author Anton Schreck
 *
 */

public class SchemePart {
    
    private int xPos, yPos;
    private PSGridImage imag;
    private SchemePartCategory category;
    
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

    public PSGridImage getImag() {
        return imag;
    }

    public void setImag(PSGridImage imag) {
        this.imag = imag;
    }

    public SchemePartCategory getCategory() {
        return category;
    }

    public void setCategory(SchemePartCategory category) {
        this.category = category;
    }

        

}
