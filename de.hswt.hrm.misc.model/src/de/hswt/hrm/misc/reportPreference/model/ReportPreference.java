package de.hswt.hrm.misc.reportPreference.model;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Strings.isNullOrEmpty;

/**
 * Represents a common used report preferences * 
 */
public class ReportPreference {

    private int id;
    private String name;
    private String fileName;

    private static final String IS_MANDATORY = "Field is a mandatory.";

    public ReportPreference(String name, String fileName) {
        this(-1, name, fileName);
    }

    public ReportPreference(int id, String name, String fileName) {

        this.id = id;
        setName(name);
        setFileName(fileName);

    }

    public void setName(String name) {
        checkArgument(!isNullOrEmpty(name), IS_MANDATORY);
        this.name = name;
    }

    public void setFileName(String fileName) {
        checkArgument(!isNullOrEmpty(fileName), IS_MANDATORY);
        this.fileName = fileName;

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getFileName() {
        return fileName;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((fileName == null) ? 0 : fileName.hashCode());
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
        ReportPreference other = (ReportPreference) obj;
        if (id != other.id) {
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
        if (fileName == null) {
            if (other.fileName != null) {
                return false;
            }
        }
        else if (!fileName.equals(other.fileName)) {
            return false;
        }
        return true;
    }

}
