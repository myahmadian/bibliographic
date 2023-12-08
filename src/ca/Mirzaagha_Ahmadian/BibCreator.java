package ca.Mirzaagha_Ahmadian;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * @author ZahraMirzaagha
 * @author AlirezaAhmadian
 * 2022-04-17
 *  Java Final Project
 */
public class BibCreator {
	
	private static final String CONTENTS_OF_JSON_FILE = "Here are the contents of the successfully created Json file: %s";
	private static final String UNABLE_TO_DISPLAY_FILE = "Sorry! I'm unable to display your desired files! Program will exit!";
	private static final String ANOTHER_CHANCE = "However you will be allowed another chance to enter another file name.";
	private static final String COULD_NOT_OPEN_INPUT_FILE = "Could not open input file. File does not exists; possibly it could not be created!";
	private static final String COULD_NOT_READ_INPUT_FILE = "Could not read input file.";
	private static final String ENTER_NAME_OF_FILE = "Please enter the name of one of the files that you need to review: ";
	private static final String TOTAL_OF_INVALID_FILES = "A total of %d files were invalid and could not be processed. All other %d \"valid\" files have been created!";
	private static final String PROBLEM_DETECTED = "Problem detected with input file: %s";
	private static final String GREETING_MESSAGE = "Welcome to BibCreator! Created by Zahra Mirzaagha And Alireza Ahmadian";
	
	private static final int MAX_FILES = 10;
	
	private static FileProcessor_v1[] fileProcessors = new FileProcessor_v1[MAX_FILES];
	private static int numberOfInvalidInputFiles = 0;
	private static final Pattern FILE_NAME_PATTERN = Pattern.compile("(ACM|IEEE|NJ)([1-9]|10)\\.json");
	
	public static void main(String[] args) {
		BibCreator bibCreator = new BibCreator();
		bibCreator.run();
	}

	private void run() {
		System.out.println(GREETING_MESSAGE);
		System.out.println(System.lineSeparator());
		openInputFiles();
		prepareOutputFiles();
		processFilesForValidation();
		queryUser();
	}

	private static void openInputFiles() {
		for (int i = 0; i < MAX_FILES; i++) {
			try {
				fileProcessors[i] = new FileProcessor_v1(i + 1);
				fileProcessors[i].openInputFile();
			}
			catch(NoFileException e) {
				System.out.println(e.getMessage());
				closeFileProcessors();
				//System.exit(1);
				return;
			}
		}
	}
	
	private static void prepareOutputFiles() {
		for (int i = 0; i < MAX_FILES; i++) {
			try {
				fileProcessors[i].prepareOutputFiles();
			} catch(FileCreationFailedException e) {
				System.out.println(e.getMessage());
				closeFileProcessors();
				System.exit(1);
			}
        }
	}

	private static void closeFileProcessors() {
		for (int i = 0; i < MAX_FILES; i++) {
			if (fileProcessors[i] != null) {
				fileProcessors[i].close();
			}
		}
	}
	
	public static void processFilesForValidation() {
		for (int i = 0; i < MAX_FILES; i++) {
			if (fileProcessors[i] != null) {
				try {
					fileProcessors[i].process();
				}
				catch (ProcessFaultedException e) {
					numberOfInvalidInputFiles++;
					System.out.println(e.getMessage());
					System.out.println(System.lineSeparator());
					fileProcessors[i].close();
				}
			}
		}
		
		System.out.println(String.format(TOTAL_OF_INVALID_FILES, numberOfInvalidInputFiles, MAX_FILES - numberOfInvalidInputFiles));
	}
	
	private static boolean isValidFileName(String fileName) {
		return FILE_NAME_PATTERN.matcher(fileName).matches();
	}
	
	/*private static void queryUser() {
		System.out.print(ENTER_NAME_OF_FILE);
		Scanner scanner = new Scanner(System.in);
		String fileName = scanner.next();
		System.out.println(System.lineSeparator());
		if (isValidFileName(fileName)) {
			showFileContent(fileName);
			return;
		}
		System.out.println(COULD_NOT_OPEN_INPUT_FILE);
		System.out.println(System.lineSeparator());
		System.out.println(ANOTHER_CHANCE);
		System.out.print(ENTER_NAME_OF_FILE);
		fileName = scanner.next();
		System.out.println(System.lineSeparator());
		if (!isValidFileName(fileName)) {
			couldNotOpenFile();
			return;
		}
		showFileContent(fileName);
	}*/

	private void queryUser() {
		try (Scanner scanner = new Scanner(System.in)) {
			String fileName = promptForFileName(scanner);
			if (isValidFileName(fileName) && fileExists(fileName)) {
				showFileContent(fileName);
			} else {
				System.out.println(COULD_NOT_OPEN_INPUT_FILE);
				System.out.println(ANOTHER_CHANCE);
				fileName = promptForFileName(scanner);
				if (isValidFileName(fileName) && fileExists(fileName)) {
					showFileContent(fileName);
				} else {
					couldNotOpenFile();
				}
			}
		}
	}
	private String promptForFileName(Scanner scanner) {
		System.out.print(ENTER_NAME_OF_FILE);
		return scanner.next();
	}

	private boolean fileExists(String fileName) {
		return Files.exists(Paths.get(fileName));
	}
	private static void showFileContent(String fileName) {
		System.out.println(String.format(CONTENTS_OF_JSON_FILE, fileName));
		
		/*try {
			BufferedReader fileReader = new BufferedReader(new FileReader(fileName));
			String line = fileReader.readLine();
			while (line != null) {
				System.out.println(line);
				line = fileReader.readLine();
			}
			fileReader.close();
		}*/
		try (BufferedReader fileReader = new BufferedReader(new FileReader(fileName))){
			fileReader.lines().forEach(System.out::println);
		}catch (FileNotFoundException e) {
			couldNotOpenFile();
		}
		catch (IOException e) {
			couldNotReadFile();
		}
	}
	
	private static void couldNotOpenFile() {
		System.out.println(COULD_NOT_OPEN_INPUT_FILE);
		System.out.println(UNABLE_TO_DISPLAY_FILE);
		//System.exit(1);
	}
	
	private static void couldNotReadFile() {
		System.out.println(COULD_NOT_READ_INPUT_FILE);
		System.out.println(UNABLE_TO_DISPLAY_FILE);
		//System.exit(1);
	}
}
