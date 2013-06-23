package de.hswt.hrm.misc.priority.model;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Strings.isNullOrEmpty;

/**
 * Represents a common used priority
 * 
 */
public class Priority {

    private int id;
    private String name;
    private String text;
    private int priority;

    private static final String IS_MANDATORY = "Field is a mandatory.";
    private static final String INVALID_NUMBER = "%d is an invalid number.%n Must be greater 0";

    public Priority(String name, String text, int priority) {
        this(-1, name, text, priority);
    }

    public Priority(int id, String name, String text, int priority) {

        this.id = id;
        setName(name);
        setText(text);
        setPriority(priority);

    }

    public void setName(String name) {
        checkArgument(!isNullOrEmpty(name), IS_MANDATORY);
        this.name = name;
    }

    public void setText(String text) {
        checkArgument(!isNullOrEmpty(name), IS_MANDATORY);
        this.text = text;

    }

    public void setPriority(int priority) {
        checkArgument(priority > 0, INVALID_NUMBER, priority);
        this.priority = priority;

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

    public int getPriority() {
        return priority;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((text == null) ? 0 : text.hashCode());
        result = prime * result + priority;
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
        Priority other = (Priority) obj;
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

        if (priority != other.priority) {
            return false;
        }
        return true;
    }

}
