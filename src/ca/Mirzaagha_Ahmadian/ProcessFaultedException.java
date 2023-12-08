package ca.Mirzaagha_Ahmadian;
/*public class ProcessFaultedException extends Exception {
	public ProcessFaultedException(String fileName, String faultReason) {
		super(getMessage(fileName, faultReason));
	}
	private static final String getMessage(String fileName, String faultReason) {
		String message = "Error detected!:";
		message += System.lineSeparator();
		message += "================";
		message += System.lineSeparator();
		message += System.lineSeparator();
		message += String.format("Problem detected with input file: %s", fileName);
		message += System.lineSeparator();
		message += faultReason;
		
		return message;
	}
}*/

/**
 * Custom exception thrown when a processing error occurs during file operations.
 * This exception is used to encapsulate errors related to the processing of files
 * such as parsing errors or validation issues.
 *
 * @author Zahra Mirzaagha
 * @author Alireza Ahmadian
 * @version 2022-04-17
 */
public class ProcessFaultedException extends Exception {

	// Unique ID for serialization
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new ProcessFaultedException with the specified file name and fault reason.
	 *
	 * @param fileName the name of the file being processed.
	 * @param faultReason the reason for the processing fault.
	 */
	public ProcessFaultedException(String fileName, String faultReason) {
		super(buildErrorMessage(fileName, faultReason));
	}

	/**
	 * Builds a detailed error message including the file name and fault reason.
	 *
	 * @param fileName the name of the file being processed.
	 * @param faultReason the reason for the processing fault.
	 * @return a detailed error message.
	 */
	private static String buildErrorMessage(String fileName, String faultReason) {
		return String.format("Error detected!:%n================%n%nProblem detected with input file: %s%n%s",
				fileName, faultReason);
	}
}
