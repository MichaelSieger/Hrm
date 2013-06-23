package de.hswt.hrm.inspection.model;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Strings.isNullOrEmpty;

import com.google.common.base.Optional;

import de.hswt.hrm.component.model.Component;

public class PhysicalRating {

    private final int id;

    private int rating;
    private int quantifier;
    private String note;
    private Component component;
    private Inspection inspection;

    private static final String IS_MANDATORY = "Field is a mandatory.";
    private static final String INVALID_NUMBER = "%d is an invalid number.%n Must be greater 0";

    public PhysicalRating(int id, int rating, String note, int quantifier) {
        this.id = id;
        setRating(rating);
        setNote(note);
    }

    public PhysicalRating(int rating, String note, int quantifier) {
        this(-1, rating, note, quantifier);
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        checkArgument(rating > 0, INVALID_NUMBER, rating);
        this.rating = rating;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        checkArgument(!isNullOrEmpty(note), IS_MANDATORY);
        this.note = note;
    }

    public Optional<Component> getComponent() {
        return Optional.fromNullable(component);
    }

    public void setComponent(Component component) {
        checkNotNull(component);
        this.component = component;
    }

    public Optional<Inspection> getInspection() {
        return Optional.fromNullable(inspection);
    }

    public void setInspection(Inspection inspection) {
        checkNotNull(inspection);
        this.inspection = inspection;
    }

    public int getId() {
        return id;
    }

    public int getQuantifier() {
        return quantifier;
    }

    public void setQuantifier(int quantifier) {
        checkArgument(quantifier > 0, INVALID_NUMBER, quantifier);
        this.quantifier = quantifier;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((component == null) ? 0 : component.hashCode());
        result = prime * result + id;
        result = prime * result + ((inspection == null) ? 0 : inspection.hashCode());
        result = prime * result + ((note == null) ? 0 : note.hashCode());
        result = prime * result + rating;
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
        if (inspection == null) {
            if (other.inspection != null) {
                return false;
            }
        }
        else if (!inspection.equals(other.inspection)) {
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
        if (rating != other.rating) {
            return false;
        }
        return true;
    }

}