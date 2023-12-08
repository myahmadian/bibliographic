package ca.Mirzaagha_Ahmadian;

/*public class FileCreationFailedException extends Exception {
	public FileCreationFailedException(String message) {
		super(message);
	}
}*/

/**
 * Custom exception class for handling file creation failures.
 * This class extends the Exception class, making it a checked exception that
 * must be declared or handled where it can be thrown.
 *
 * @author ZahraMirzaagha
 * @author AlirezaAhmadian
 * @version 2022-04-17
 */
public class FileCreationFailedException extends Exception {

	/**
	 * Constructs a new FileCreationFailedException with the specified detail message.
	 * The cause is not initialized, and may subsequently be initialized by a call to initCause.
	 *
	 * @param message the detail message. The detail message is saved for later retrieval by the getMessage() method.
	 */
	public FileCreationFailedException(String message) {
		super(message);
	}

	/**
	 * Constructs a new FileCreationFailedException with the specified detail message and cause.
	 * Note that the detail message associated with cause is not automatically incorporated in this exception's detail message.
	 *
	 * @param message the detail message (which is saved for later retrieval by the getMessage() method).
	 * @param cause the cause (which is saved for later retrieval by the getCause() method). (A null value is permitted,
	 *              and indicates that the cause is nonexistent or unknown.)
	 */
	public FileCreationFailedException(String message, Throwable cause) {
		super(message, cause);
	}
}
