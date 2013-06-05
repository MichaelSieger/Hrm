package de.hswt.hrm.scheme.ui;

/**
 * This Exception is thrown if a place in the SchemeGrid is already occupied by another image.
 * 
 * @author Michael Sieger
 *
 */
public class PlaceOccupiedException extends RuntimeException{
    private static final long serialVersionUID = 1783878305645670087L;

    public PlaceOccupiedException() {
        super();
    }

    public PlaceOccupiedException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public PlaceOccupiedException(String message, Throwable cause) {
        super(message, cause);
    }

    public PlaceOccupiedException(String message) {
        super(message);
    }

    public PlaceOccupiedException(Throwable cause) {
        super(cause);
    }

    
}
