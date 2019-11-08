package net.ronymesquita.tictactoe.application;

public class ApplicationFxmlLoadException extends RuntimeException {

	private static final long serialVersionUID = 3380764969690185279L;

	public ApplicationFxmlLoadException() {
		super();
	}

	public ApplicationFxmlLoadException(Throwable throwable) {
		super(throwable);
	}

	public ApplicationFxmlLoadException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ApplicationFxmlLoadException(String message, Throwable cause) {
		super(message, cause);
	}

	public ApplicationFxmlLoadException(String message) {
		super(message);
	}

}
