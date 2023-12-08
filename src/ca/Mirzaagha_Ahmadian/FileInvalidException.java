package ca.Mirzaagha_Ahmadian;
/*

public class FileInvalidException extends Exception {
	private static final String DEFAULT_MESSAGE = "Error: Input file cannot be parsed due to missing information (i.e. month={}, title={}, etc.)";
	
	public FileInvalidException() {
		super(DEFAULT_MESSAGE);
	}
	
	public FileInvalidException(String message) {
		super(message);
	}
}*/
/**
 * Custom exception class for handling invalid file scenarios.
 * This exception is thrown when an input file cannot be parsed due to missing information.
 * For example, if required fields like month, title, etc., are missing in the file content.
 *
 * @author ZahraMirzaagha
 * @author AlirezaAhmadian
 * @version 2022-04-17
 */
public class FileInvalidException extends Exception {
	// Unique ID for serialization
	private static final long serialVersionUID = 1L;

	// Default message for the exception
	private static final String DEFAULT_MESSAGE = "Error: Input file cannot be parsed due to missing information (i.e. month={}, title={}, etc.)";

	/**
	 * Constructs a new FileInvalidException with the default message.
	 */
	public FileInvalidException() {
		super(DEFAULT_MESSAGE);
	}

	/**
	 * Constructs a new FileInvalidException with the specified detail message.
	 *
	 * @param message the detail message.
	 */
	public FileInvalidException(String message) {
		super(message);
	}

	/**
	 * Constructs a new FileInvalidException with the specified detail message and cause.
	 *
	 * @param message the detail message.
	 * @param cause the cause of the exception.
	 */
	public FileInvalidException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructs a new FileInvalidException with the specified cause.
	 *
	 * @param cause the cause of the exception.
	 */
	public FileInvalidException(Throwable cause) {
		super(cause);
	}

	/**
	 * Constructs a new FileInvalidException with the specified detail message, cause,
	 * suppression enabled or disabled, and writable stack trace enabled or disabled.
	 *
	 * @param message the detail message.
	 * @param cause the cause of the exception.
	 * @param enableSuppression whether or not suppression is enabled or disabled.
	 * @param writableStackTrace whether or not the stack trace should be writable.
	 */
	public FileInvalidException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
