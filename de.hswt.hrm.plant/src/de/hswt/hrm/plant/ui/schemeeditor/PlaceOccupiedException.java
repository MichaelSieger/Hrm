package de.hswt.hrm.plant.ui.schemeeditor;

public class PlaceOccupiedException extends Exception{

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
