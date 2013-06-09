package de.hswt.hrm.photo.model;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Strings.isNullOrEmpty;

import java.util.Arrays;

public class Photo {

    private final int id;

    private String name;
    private byte[] blob;
    private String label;

    private static final String NO_IMAGE_ERROR = "All Images are null";
    private static final String IS_MANDATORY = "Field is a mandatory.";

    public Photo(int id, byte[] blob, String name, String label) {
        this.id = id;
        setName(name);
        this.blob = blob;
        setLabel(label);
        checkArgument(!(blob == null), NO_IMAGE_ERROR);
    }
    public Photo(byte[] blob, String name, String label) {
        this(-1, blob, name, label);
    }
    
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        // The name must be a non empty string
        checkArgument(!isNullOrEmpty(name), IS_MANDATORY);
        this.name = name;
    }

    public byte[] getBlob() {
        return blob;
    }
    
    public void setBlob(byte[] blob) {
        if (blob == null) {
            checkArgument(blob != null,  NO_IMAGE_ERROR);
        }
        this.blob = blob;
    }
    
    public String getLabel() {
        return label;
    }
    
    public void setLabel(String label) {
        // The name must be a non empty string
        checkArgument(!isNullOrEmpty(label), IS_MANDATORY);
        this.label = label;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(blob);
        result = prime * result + id;
        result = prime * result + ((label == null) ? 0 : label.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
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
        Photo other = (Photo) obj;
        if (!Arrays.equals(blob, other.blob)) {
            return false;
        }
        if (id != other.id) {
            return false;
        }
        if (label == null) {
            if (other.label != null) {
                return false;
            }
        }
        else if (!label.equals(other.label)) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        }
        else if (!name.equals(other.name)) {
            return false;
        }
        return true;
    }

}
