package de.hswt.hrm.scheme.model;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

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
    private Category category;
    
    public Component(int id, String name, byte[] leftRightImage, byte[] rightLeftImage,
            byte[] upDownImage, byte[] downUpImage, int quantifier, boolean isRated,
            Category category) {
        super();
        this.id = id;
        setName(name);
        setLeftRightImage(leftRightImage);
        setRightLeftImage(rightLeftImage);
        setUpDownImage(upDownImage);
        setDownUpImage(downUpImage);
        setQuantifier(quantifier);
        setRated(isRated);
        setCategory(category);
    }
    
    public Component(String name, byte[] leftRightImage, byte[] rightLeftImage,
            byte[] upDownImage, byte[] downUpImage, int quantifier, boolean isRated,
            Category category) {
        this(-1, name, leftRightImage, rightLeftImage, 
                upDownImage, downUpImage, quantifier, isRated, category);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		checkNotNull(category);
		this.category = category;
	}

	public void setName(String name) {
        //The name must be a non empty string
		checkNotNull(name);
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
    	checkNotNull(rightLeftImage);
        this.rightLeftImage = rightLeftImage;
    }

    public byte[] getUpDownImage() {
        return upDownImage;
    }

    public void setUpDownImage(byte[] upDownImage) {
    	checkNotNull(upDownImage);
        this.upDownImage = upDownImage;
    }

    public byte[] getDownUpImage() {
        return downUpImage;
    }

    public void setDownUpImage(byte[] downUpImage) {
    	checkNotNull(downUpImage);
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
