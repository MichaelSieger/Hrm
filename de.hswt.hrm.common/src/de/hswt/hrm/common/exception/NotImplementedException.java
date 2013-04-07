package de.hswt.hrm.common.exception;

/**
 * Could be used during development to show that a method is not implemented right now.
 */
public class NotImplementedException extends RuntimeException {

    /**
     * Auto generated.
     */
    private static final long serialVersionUID = 6887934099564951633L;

    public NotImplementedException() {
        super();
    }

    public NotImplementedException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public NotImplementedException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotImplementedException(String message) {
        super(message);
    }

    public NotImplementedException(Throwable cause) {
        super(cause);
    }
}
