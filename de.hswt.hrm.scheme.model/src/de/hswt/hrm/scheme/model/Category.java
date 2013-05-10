package de.hswt.hrm.scheme.model;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Strings.isNullOrEmpty;

/**
 * Represents a Category
 * 
 * @author Michael Sieger
 * 
 */
public class Category {

    private final int id;
    private String name;
    private int width;
    private int height;
    private int defaultQualifier;
    private boolean defaultBoolRating;

    private static final String IS_MANDATORY = "Field is a mandatory.";
    private static final String INVALID_NUMBER = "%d is an invalid number.%n Must be greater 0";

    public Category(int id, String name, int width, int height, int defaultQualifier,
            boolean defaultBoolRating) {

        this.id = id;

        setName(name);
        setWidth(width);
        setHeight(height);
        setDefaultQualifier(defaultQualifier);
        setDefaultBoolRating(defaultBoolRating);
    }

    public Category(String name, int width, int height, int defaultQualifier,
            boolean defaultBoolRating) {
        this(-1, name, width, height, defaultQualifier, defaultBoolRating);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        checkArgument(!isNullOrEmpty(name), IS_MANDATORY);
        this.name = name;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        checkArgument(width > 0, INVALID_NUMBER, width);
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        checkArgument(height > 0, INVALID_NUMBER, height);
        this.height = height;
    }

    public int getDefaultQualifier() {
        return defaultQualifier;
    }

    public void setDefaultQualifier(int defaultQualifier) {
        checkArgument(defaultQualifier > 0, INVALID_NUMBER, defaultQualifier);
        this.defaultQualifier = defaultQualifier;
    }

    public boolean getDefaultBoolRating() {
        return defaultBoolRating;
    }

    public void setDefaultBoolRating(boolean defaultBoolRating) {
        this.defaultBoolRating = defaultBoolRating;
    }

    public int getId() {
        return id;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + height;
        result = prime * result + id;
        result = prime * result + (defaultBoolRating ? 1231 : 1237);
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + defaultQualifier;
        result = prime * result + width;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Category other = (Category) obj;
        if (height != other.height) {
            return false;
        }
        if (id != other.id) {
            return false;
        }
        if (defaultBoolRating != other.defaultBoolRating) {
            return false;
        }
        if (name == null) {
            if (other.name != null)
                return false;
        }
        else if (!name.equals(other.name)) {
            return false;
        }
        if (defaultQualifier != other.defaultQualifier) {
            return false;
        }
        if (width != other.width) {
            return false;
        }
        return true;
    }

}
