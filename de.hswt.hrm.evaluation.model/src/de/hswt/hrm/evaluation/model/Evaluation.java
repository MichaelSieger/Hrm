package de.hswt.hrm.evaluation.model;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Strings.isNullOrEmpty;

/**
 * Represents a common used evaluation for various plants
 * 
 */
public class Evaluation {

    private int id;
    private String name;
    private String text;

    private static final String IS_MANDATORY = "Field is a mandatory.";

    public Evaluation(String name, String text) {
        this(-1, name, text);
    }

    public Evaluation(int id, String name, String text) {

        this.id = id;
        setName(name);
        setText(text);

    }

    public void setName(String name) {
        checkArgument(!isNullOrEmpty(name), IS_MANDATORY);
        this.name = name;
    }

    public void setText(String text) {
        checkArgument(!isNullOrEmpty(name), IS_MANDATORY);
        this.text = text;

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

    public String getText() {
        return text;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((text == null) ? 0 : text.hashCode());
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
        Evaluation other = (Evaluation) obj;
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
        if (text == null) {
            if (other.text != null) {
                return false;
            }
        }
        else if (!text.equals(other.text)) {
            return false;
        }
        return true;
    }

}
