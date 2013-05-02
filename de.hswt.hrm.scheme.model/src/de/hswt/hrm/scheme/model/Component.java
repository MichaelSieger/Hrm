package de.hswt.hrm.scheme.model;

import static com.google.common.base.Preconditions.*;

/**
 * Represents a Component
 * 
 * @author Michael Sieger
 *
 */
public class Component {
    
    private final int id;

    private String name;
    private byte[] leftRightImage;
    private byte[] rightLeftImage;
    private byte[] upDownImage;
    private byte[] downUpImage;
    private int quantifier;
    private boolean isRated;
    
    public Component(int id, String name, byte[] leftRightImage, byte[] rightLeftImage,
            byte[] upDownImage, byte[] downUpImage, int quantifier, boolean isRated) {
        super();
        this.id = id;
        setName(name);
        setLeftRightImage(leftRightImage);
        setRightLeftImage(rightLeftImage);
        setUpDownImage(upDownImage);
        setDownUpImage(downUpImage);
        setQuantifier(quantifier);
        setRated(isRated);
    }
    
    public Component(String name, byte[] leftRightImage, byte[] rightLeftImage,
            byte[] upDownImage, byte[] downUpImage, int quantifier, boolean isRated) {
        this(-1, name, leftRightImage, rightLeftImage, 
                upDownImage, downUpImage, quantifier, isRated);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        //The name must be a non empty string
        checkArgument(name != null);
        checkArgument(!name.trim().isEmpty());
        this.name = name;
    }

    public byte[] getLeftRightImage() {
        return leftRightImage;
    }

    public void setLeftRightImage(byte[] leftRightImage) {
        checkArgument(leftRightImage != null);
        this.leftRightImage = leftRightImage;
    }

    public byte[] getRightLeftImage() {
        return rightLeftImage;
    }

    public void setRightLeftImage(byte[] rightLeftImage) {
        checkArgument(rightLeftImage != null);
        this.rightLeftImage = rightLeftImage;
    }

    public byte[] getUpDownImage() {
        return upDownImage;
    }

    public void setUpDownImage(byte[] upDownImage) {
        checkArgument(upDownImage != null);
        this.upDownImage = upDownImage;
    }

    public byte[] getDownUpImage() {
        return downUpImage;
    }

    public void setDownUpImage(byte[] downUpImage) {
        checkArgument(downUpImage != null);
        this.downUpImage = downUpImage;
    }

    public int getQuantifier() {
        return quantifier;
    }

    public void setQuantifier(int quantifier) {
        checkArgument(quantifier >= 0);
        this.quantifier = quantifier;
    }

    public boolean isRated() {
        return isRated;
    }

    public void setRated(boolean isRated) {
        this.isRated = isRated;
    }
    
    
}
