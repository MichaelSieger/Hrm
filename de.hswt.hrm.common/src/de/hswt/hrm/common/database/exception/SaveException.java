package de.hswt.hrm.common.database.exception;

/**
 * Exception to show that an element could not be saved in the database.
 */
public class SaveException extends DatabaseException {

    /**
     * Auto generated.
     */
    private static final long serialVersionUID = -4319490208356503084L;

    public SaveException() {
        // TODO Auto-generated constructor stub
    }

    public SaveException(String message) {
        super(message);
        // TODO Auto-generated constructor stub
    }

    public SaveException(Throwable cause) {
        super(cause);
        // TODO Auto-generated constructor stub
    }

    public SaveException(String message, Throwable cause) {
        super(message, cause);
        // TODO Auto-generated constructor stub
    }

    public SaveException(String message, Throwable cause,
            boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

}
