package de.hswt.hrm.inspection.model;

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.base.Optional;

import de.hswt.hrm.scheme.model.SchemeComponent;

public class PhysicalRating 
	implements Rating
{

    private final int id;

    private Optional<Integer> rating;
    private Optional<Integer> quantifier;
    private Optional<String> note;
    private SchemeComponent component;
    private Inspection inspection;
    private Optional<SamplingPointType> samplingPointType;

    // private static final String INVALID_NUMBER = "%d is an invalid number.%n Must be greater 0";
    // private static final String INVALID_LENGTH = "Empty String is not allowed !";

    public PhysicalRating(int id, Inspection inspection, SchemeComponent component, int rating,
            int quantifier) {

        this.id = id;
        samplingPointType = Optional.absent();
        setInspection(inspection);
        setComponent(component);
        setRating(rating);
        setQuantifier(quantifier);
    }

    public PhysicalRating(Inspection inspection, SchemeComponent schemeComponent) {
        this(inspection, schemeComponent, 0, -1);
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

    public Optional<SamplingPointType> getSamplingPointType() {
        return samplingPointType;
    }

    public void setSamplingPointType(SamplingPointType samplingPointType) {
        this.samplingPointType = Optional.fromNullable(samplingPointType);
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
        result = prime * result + ((note == null) ? 0 : note.hashCode());
        result = prime * result + ((quantifier == null) ? 0 : quantifier.hashCode());
        result = prime * result + ((rating == null) ? 0 : rating.hashCode());
        result = prime * result + ((samplingPointType == null) ? 0 : samplingPointType.hashCode());
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
        if (quantifier == null) {
            if (other.quantifier != null) {
                return false;
            }
        }
        else if (!quantifier.equals(other.quantifier)) {
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
        if (samplingPointType == null) {
            if (other.samplingPointType != null) {
                return false;
            }
        }
        else if (!samplingPointType.equals(other.samplingPointType)) {
            return false;
        }
        return true;
    }

}