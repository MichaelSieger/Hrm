package de.hswt.hrm.component.model;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Strings.isNullOrEmpty;

import com.google.common.base.Optional;

import de.hswt.hrm.catalog.model.Catalog;

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
    private int defaultQuantifier;
    private boolean defaultBoolRating;
    
    // Catalog is optional as we won't force its existence during category creation
    private Catalog catalog;

    private static final String IS_MANDATORY = "Field is a mandatory.";
    private static final String INVALID_NUMBER = "%d is an invalid number.%n Must be greater 0";

    public Category(int id, String name, int width, int height, int defaultQuantifier,
            boolean defaultBoolRating) {

        this.id = id;

        setName(name);
        setWidth(width);
        setHeight(height);
        setDefaultQuantifier(defaultQuantifier);
        setDefaultBoolRating(defaultBoolRating);
    }

    public Category(String name, int width, int height, int defaultQuantifier,
            boolean defaultBoolRating) {
        this(-1, name, width, height, defaultQuantifier, defaultBoolRating);
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

    public int getDefaultQuantifier() {
        return defaultQuantifier;
    }

    public void setDefaultQuantifier(int defaultQuantifier) {
        checkArgument(defaultQuantifier > 0, INVALID_NUMBER, defaultQuantifier);
        this.defaultQuantifier = defaultQuantifier;
    }

    public boolean getDefaultBoolRating() {
        return defaultBoolRating;
    }

    public void setDefaultBoolRating(boolean defaultBoolRating) {
        this.defaultBoolRating = defaultBoolRating;
    }

     public Optional<Catalog> getCatalog() {
         return Optional.fromNullable(catalog);
     }
    
     public void setCatalog(Catalog catalog) {
         this.catalog = catalog;
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
        result = prime * result + defaultQuantifier;
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
        if (defaultQuantifier != other.defaultQuantifier) {
            return false;
        }
        if (width != other.width) {
            return false;
        }
        return true;
    }

}
