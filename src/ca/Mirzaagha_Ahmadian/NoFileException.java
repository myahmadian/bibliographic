package ca.Mirzaagha_Ahmadian;

/*public class NoFileException extends Exception {
	public NoFileException(String message) {
		super(message);
	}
}*/

/**
 * Custom exception thrown when an expected file cannot be found or opened.
 * This exception is used to indicate that a file operation failed because
 * the file was not present where expected.
 *
 * @author Zahra Mirzaagha
 * @author Alireza Ahmadian
 * @version 2022-04-17
 */
public class NoFileException extends Exception {

	// Unique ID for serialization
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new NoFileException with the specified detail message.
	 *
	 * @param message the detail message.
	 */
	public NoFileException(String message) {
		super(message);
	}

	/**
	 * Constructs a new NoFileException with the specified detail message and cause.
	 *
	 * @param message the detail message.
	 * @param cause the cause of the exception.
	 */
	public NoFileException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructs a new NoFileException with the specified cause.
	 *
	 * @param cause the cause of the exception.
	 */
	public NoFileException(Throwable cause) {
		super(cause);
	}
}
