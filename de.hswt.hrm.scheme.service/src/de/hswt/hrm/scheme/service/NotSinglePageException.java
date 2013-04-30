package de.hswt.hrm.scheme.service;

public class NotSinglePageException extends RuntimeException{

	private static final long serialVersionUID = 9062102747846104340L;

	public NotSinglePageException() {
		super();
	}

	public NotSinglePageException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public NotSinglePageException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotSinglePageException(String message) {
		super(message);
	}

	public NotSinglePageException(Throwable cause) {
		super(cause);
	}

	
}
