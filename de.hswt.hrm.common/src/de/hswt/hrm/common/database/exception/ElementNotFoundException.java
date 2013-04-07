package de.hswt.hrm.common.database.exception;

/**
 * Exception to show that a element could not be found in the database.
 */
public class ElementNotFoundException extends DatabaseException {

    /**
     * Auto generated.
     */
    private static final long serialVersionUID = -2033204561736315540L;

    public ElementNotFoundException() { }

    public ElementNotFoundException(String message) {
        super(message);
    }

    public ElementNotFoundException(Throwable cause) {
        super(cause);
    }

    public ElementNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ElementNotFoundException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
