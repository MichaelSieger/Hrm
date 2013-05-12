package de.hswt.hrm.common.locking.jdbc;

public class LockException extends RuntimeException {

    public LockException() {
        super();
    }

    public LockException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public LockException(String message, Throwable cause) {
        super(message, cause);
    }

    public LockException(String message) {
        super(message);
    }

    public LockException(Throwable cause) {
        super(cause);
    }

}
