package de.hswt.hrm.inspection.model;

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.base.Optional;

import de.hswt.hrm.scheme.model.SchemeComponent;

public class PhysicalRating {

    private final int id;

    private Optional<Integer> rating;
    private Optional<Integer> quantifier;
    private Optional<String> note;
    private SchemeComponent component;
    private Inspection inspection;

    // private static final String INVALID_NUMBER = "%d is an invalid number.%n Must be greater 0";
    // private static final String INVALID_LENGTH = "Empty String is not allowed !";

    public PhysicalRating(int id, Inspection inspection, SchemeComponent component, int rating,
            int quantifier) {

        this.id = id;
        setInspection(inspection);
        setComponent(component);
        setRating(rating);
        setQuantifier(quantifier);
    }

    public PhysicalRating(Inspection inspection, SchemeComponent schemeComponent) {
        this(inspection, schemeComponent, -1, -1);
    }

    public PhysicalRating(Inspection inspection, SchemeComponent component, int rating,
            int quantifier) {
        this(-1, inspection, component, rating, quantifier);
    }

    public int getRating() {
        return rating.get();
    }

    public void setRating(int rating) {
        this.rating = Optional.of(rating);
    }

    public Optional<String> getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = Optional.fromNullable(note);
    }

    public SchemeComponent getComponent() {
        return component;
    }

    public void setComponent(SchemeComponent component) {
        checkNotNull(component);
        this.component = component;
    }

    public Inspection getInspection() {
        return inspection;
    }

    public void setInspection(Inspection inspection) {
        checkNotNull(inspection);
        this.inspection = inspection;
    }

    public int getId() {
        return id;
    }

    public int getQuantifier() {
        return quantifier.get();
    }

    public void setQuantifier(int quantifier) {
        this.quantifier = Optional.of(quantifier);
    }

    public boolean isValid() {
        return rating.isPresent() && note.isPresent() && quantifier.isPresent()
                && quantifier.get() > 0 && note.get().length() > 0 && rating.get() > 0;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((component == null) ? 0 : component.hashCode());
        result = prime * result + id;
        result = prime * result + ((inspection == null) ? 0 : inspection.hashCode());
        result = prime * result + ((!note.isPresent()) ? 0 : note.get().hashCode());
        result = prime * result + rating.get();
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
        if (!note.isPresent()) {
            if (other.note.isPresent()) {
                return false;
            }
        }
        else if (!note.get().equals(other.note.get())) {
            return false;
        }
        if (rating.get() != other.rating.get()) {
            return false;
        }
        return true;
    }

}