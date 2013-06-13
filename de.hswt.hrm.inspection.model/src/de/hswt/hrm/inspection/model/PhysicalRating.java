package de.hswt.hrm.inspection.model;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.base.Optional;

import de.hswt.hrm.component.model.Component;

public class PhysicalRating {

    private final int id;

    private String rating;
    private String note;
    private Component component;
    private Inspection report;

    private static final String NO_IMAGE_ERROR = "All Images are null";
    private static final String IS_MANDATORY = "Field is a mandatory.";
    private static final String INVALID_NUMBER = "%d is an invalid number.%n Must be greater 0";

    public PhysicalRating(int id, String rating, String note) {
        this.id = id;
        setRating(rating);
        setNote(note);
    }

    public PhysicalRating(String rating, String note) {
        this(-1, rating, note);
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Optional<Component> getComponent() {
        return Optional.fromNullable(component);
    }

    public void setComponent(Component component) {
        checkNotNull(component);
        this.component = component;
    }

    public Optional<Inspection> getReport() {
        return Optional.fromNullable(report);
    }

    public void setReport(Inspection report) {
        checkNotNull(report);
        this.report = report;
    }

    public int getId() {
        return id;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((component == null) ? 0 : component.hashCode());
        result = prime * result + id;
        result = prime * result + ((note == null) ? 0 : note.hashCode());
        result = prime * result + ((rating == null) ? 0 : rating.hashCode());
        result = prime * result + ((report == null) ? 0 : report.hashCode());
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
        PhysicalRating other = (PhysicalRating) obj;
        if (component == null) {
            if (other.component != null) {
                return false;
            }
        }
        else if (!component.equals(other.component)) {
            return false;
        }
        if (id != other.id) {
            return false;
        }
        if (note == null) {
            if (other.note != null) {
                return false;
            }
        }
        else if (!note.equals(other.note)) {
            return false;
        }
        if (rating == null) {
            if (other.rating != null) {
                return false;
            }
        }
        else if (!rating.equals(other.rating)) {
            return false;
        }
        if (report == null) {
            if (other.report != null) {
                return false;
            }
        }
        else if (!report.equals(other.report)) {
            return false;
        }
        return true;
    }

}
