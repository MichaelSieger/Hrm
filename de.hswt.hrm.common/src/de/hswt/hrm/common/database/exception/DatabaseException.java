package de.hswt.hrm.common.database.exception;

/**
 * Base class for all database exceptions.
 */
public class DatabaseException extends Exception {

    /**
     * Auto generated.
     */
    private static final long serialVersionUID = -8559558625927280105L;

    public DatabaseException() {
    }

    public DatabaseException(String message) {
        super(message);
    }

    public DatabaseException(Throwable cause) {
        super(cause);
    }

    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public DatabaseException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
