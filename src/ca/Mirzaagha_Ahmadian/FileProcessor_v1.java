package ca.Mirzaagha_Ahmadian;

/**
 * @author ZahraMirzaagha
 * @author AlirezaAhmadian
 * 2022-04-17
 *  Java Final Project
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

public class FileProcessor_v1 {
	private static final String INPUT_FILE_PATTERN = "Latex%d.bib";
	private static final String ACM_OUTPUT_FILE_PATTERN = "ACM%d.json";
	private static final String IEEE_OUTPUT_FILE_PATTERN = "IEEE%d.json";
	private static final String NJ_OUTPUT_FILE_PATTERN = "NJ%d.json";
	
	private static final String COULD_NOT_OPEN_ERROR_MESSAGE = "Could not open input file %s for reading. Please check if file exists! Program will terminate after closing any opened files.";
	private static final String COULD_NOT_CREATE_ERROR_MESSAGE = "Could not create file %s.";
	private static final String NO_FILE_ERROR_MESSAGE = "File %s not found.";
	private static final String ARTICLE_MISSING = "File is invalid. Article missing.";
	private static final String FILE_EMPTY = "File is invalid. No article found.";
	
	private static final String ARTICLE = "@ARTICLE{";
	
	private String inputFileName;
	private String acmFileName;
	private String ieeeFileName;
	private String njFileName;
	
	private Scanner inputFileScanner;
	
	private File acmFile;
	private File ieeeFile;
	private File njFile;
	
	private PrintWriter acmPrintWriter;
	private PrintWriter ieeePrintWriter;
	private PrintWriter njPrintWriter;
	private final Path inputFilePath;
	private final Path acmFilePath;
	private final Path ieeeFilePath;
	private final Path njFilePath;
	private final List<Article> articles;
	
	public FileProcessor_v1(int index) {
		/*inputFileName = String.format(INPUT_FILE_PATTERN, index);
		acmFileName = String.format(ACM_OUTPUT_FILE_PATTERN, index);
		ieeeFileName = String.format(IEEE_OUTPUT_FILE_PATTERN, index);
		njFileName = String.format(NJ_OUTPUT_FILE_PATTERN, index);
		articles = new ArrayList<Article>();*/
		inputFilePath = Paths.get(String.format(INPUT_FILE_PATTERN, index));
		acmFilePath = Paths.get(String.format(ACM_OUTPUT_FILE_PATTERN, index));
		ieeeFilePath = Paths.get(String.format(IEEE_OUTPUT_FILE_PATTERN, index));
		njFilePath = Paths.get(String.format(NJ_OUTPUT_FILE_PATTERN, index));
		articles = new ArrayList<>();
	}
	
	public void openInputFile() throws NoFileException {
		/*try {
			this.inputFileScanner = new Scanner(new FileInputStream(inputFileName));
		}
		catch(FileNotFoundException e) {
			throw new NoFileException(String.format(COULD_NOT_OPEN_ERROR_MESSAGE, inputFileName));
		}*/
		if (!Files.exists(inputFilePath)) {
			throw new NoFileException(String.format(NO_FILE_ERROR_MESSAGE, inputFilePath));
		}
	}
	
/*	public void prepareOutputFiles() throws FileCreationFailedException, NoFileException {
		createOutputFiles();
		createPrintWriters();
	}*/

	public void prepareOutputFiles() throws FileCreationFailedException {
		try {
			Files.createFile(acmFilePath);
			Files.createFile(ieeeFilePath);
			Files.createFile(njFilePath);
		} catch (IOException e) {
			throw new FileCreationFailedException(String.format(COULD_NOT_CREATE_ERROR_MESSAGE, e.getMessage()));
		}
	}
	private void createPrintWriters() throws NoFileException {
		try {
			this.acmPrintWriter = new PrintWriter(new FileOutputStream(acmFileName));
		}
		catch (FileNotFoundException e) {
			throw new NoFileException(String.format(NO_FILE_ERROR_MESSAGE, acmFileName));
		}
		
		try {
			this.ieeePrintWriter = new PrintWriter(new FileOutputStream(ieeeFileName));
		}
		catch (FileNotFoundException e) {
			throw new NoFileException(String.format(NO_FILE_ERROR_MESSAGE, ieeeFileName));
		}
		
		try {
			this.njPrintWriter = new PrintWriter(new FileOutputStream(njFileName));
		}
		catch (FileNotFoundException e) {
			throw new NoFileException(String.format(NO_FILE_ERROR_MESSAGE, ieeeFileName));
		}
	}

	private void createOutputFiles()
			throws FileCreationFailedException {
		try {
			acmFile = new File(acmFileName);
			acmFile.createNewFile();
		}
		catch(IOException e) {
			throw new FileCreationFailedException(String.format(COULD_NOT_CREATE_ERROR_MESSAGE, acmFileName));
		}
		
		try {
			ieeeFile = new File(ieeeFileName);
			ieeeFile.createNewFile();
		}
		catch(IOException e) {
			throw new FileCreationFailedException(String.format(COULD_NOT_CREATE_ERROR_MESSAGE, ieeeFileName));
		}
		
		try {
			njFile = new File(njFileName);
			njFile.createNewFile();
		}
		catch(IOException e) {
			throw new FileCreationFailedException(String.format(COULD_NOT_CREATE_ERROR_MESSAGE, njFileName));
		}
	}

	public void close() {
		//closeInputFileScanner();
		//closeOutputFilePrintWriters();
		deleteOutputFiles();
	}

	private void closeInputFileScanner() {
		if (inputFileScanner == null) {
			return;
		}
		inputFileScanner.close();
	}
	
	private void closeOutputFilePrintWriters() {
		if (acmPrintWriter != null) {
			acmPrintWriter.close();
		}
		
		if (ieeePrintWriter != null) {
			ieeePrintWriter.close();
		}
		
		if (njPrintWriter != null) {
			njPrintWriter.close();
		}
	}
	
	private void deleteOutputFiles() {
		/*if (acmFile != null && acmFile.exists()) {
			acmFile.delete();
		}
		
		if (ieeeFile != null && ieeeFile.exists()) {
			ieeeFile.delete();
		}
		
		if (njFile != null && njFile.exists()) {
			njFile.delete();
		}*/
		try {
			Files.deleteIfExists(acmFilePath);
			Files.deleteIfExists(ieeeFilePath);
			Files.deleteIfExists(njFilePath);
		} catch (IOException e) {
			// Log the exception using a logging framework
		}
	}

	public void process() throws ProcessFaultedException {
		/*processArticles();
		validateArticles();
		populateAcmFile();
		populateIeeeFile();
		populateNjFile();*/

		try (Scanner scanner = new Scanner(Files.newInputStream(inputFilePath))) {
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine().trim();
				if (line.isBlank()) {
					continue;
				}
				if (!line.startsWith(ARTICLE)) {
					throw new FileInvalidException(ARTICLE_MISSING);
				}
				ArticleReader articleReader = new ArticleReader(scanner);
				articles.add(articleReader.readArticle());
			}
			if (articles.isEmpty()) {
				throw new FileInvalidException(FILE_EMPTY);
			}
		} catch (IOException | FileInvalidException e) {
			throw new ProcessFaultedException(inputFilePath.toString(), e.getMessage());
		}

		validateArticles();
		populateFiles();
	}

	private void populateFiles() {
		populateFile(acmFilePath, this::toAcmString);
		populateFile(ieeeFilePath, this::toIeeeString);
		populateFile(njFilePath, this::toNjString);
	}
	private void populateFile(Path filePath, Function<Article, String> formatter) {
		try {
			Files.write(filePath, new byte[0], StandardOpenOption.TRUNCATE_EXISTING);
			for (Article article : articles) {
				String content = formatter.apply(article) + System.lineSeparator() + System.lineSeparator();
				Files.write(filePath, content.getBytes(), StandardOpenOption.APPEND);
			}
		} catch (IOException e) {
			// Log the exception using a logging framework
		}
	}

	private void processArticles() throws ProcessFaultedException {
		try {
			while (inputFileScanner.hasNextLine()) {
				String line = inputFileScanner.nextLine().trim();
				
				if (line.isBlank()) {
					continue;
				}
				
				if (!line.equals(ARTICLE)) {
					throw new FileInvalidException(ARTICLE_MISSING);
				}
				
				ArticleReader articleReader = new ArticleReader(inputFileScanner);
				articles.add(articleReader.readArticle());
			}
			
			if (articles.size() == 0) {
				throw new FileInvalidException(FILE_EMPTY);
			}
		}
		catch (FileInvalidException e) {
			throw new ProcessFaultedException(inputFileName, e.getMessage());
		}
		
		inputFileScanner.close();
	}
	
	private void validateArticles() throws ProcessFaultedException {
		try {
			for (Article article : articles) {
				article.validate();
			}
		}
		catch (FileInvalidException e) {
			throw new ProcessFaultedException(acmFileName, e.getMessage());
		}
	}
	
	private void populateAcmFile() {
		for (Article article : articles) {
			String acmString = article.toAcmString(articles.indexOf(article));
			acmPrintWriter.write(acmString);
			acmPrintWriter.write(System.lineSeparator());
			acmPrintWriter.write(System.lineSeparator());
		}
		
		acmPrintWriter.close();
	}
	
	private void populateIeeeFile() {
		for (Article article : articles) {
			String ieeeString = article.toIeeeString();
			ieeePrintWriter.write(ieeeString);
			ieeePrintWriter.write(System.lineSeparator());
			ieeePrintWriter.write(System.lineSeparator());
		}
		
		ieeePrintWriter.close();
	}
	
	private void populateNjFile() {
		for (Article article : articles) {
			String njString = article.toNjString();
			njPrintWriter.write(njString);
			njPrintWriter.write(System.lineSeparator());
			njPrintWriter.write(System.lineSeparator());
		}
		
		njPrintWriter.close();
	}

	public Object getInputFileName() {
		return inputFileName;
	}
}
