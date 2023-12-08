package ca.Mirzaagha_Ahmadian;
/*
 *@author Zahra Mirzaagha
 * @author Alireza Ahmadian
 * 2022-04-17
 *  Java Final Project
 *  This class is responsible for reading and parsing bibliographic information
 *  from a given input source to populate an Article object. It expects the input
 *  to follow a specific format where each field is enclosed in braces and ends with a comma.
 */

import java.util.Scanner;
import java.util.regex.Pattern;

public class ArticleReader {
	private static final String CLOSING_BRACE = "}";
	private static final String MONTH_PREFIX = "month={";
	private static final String ISSN_PREFIX = "ISSN={";
	private static final String DOI_PREFIX = "doi={";
	private static final String KEYWORDS_PREFIX = "keywords={";
	private static final String PAGES_PREFIX = "pages={";
	private static final String NUMBER_PREFIX = "number={";
	private static final String VOLUME_PREFIX = "volume={";
	private static final String YEAR_PREFIX = "year={";
	private static final String TITLE_PREFIX = "title={";
	private static final String JOURNAL_PREFIX = "journal={";
	private static final String AUTHOR_PREFIX = "author={";
	private static final String COMMA = ",";
	
	private static final String INVALID_FILE_FORMAT = "Invalid file format due to unrecognized string %s";
	private static final String COMMA_MISSING = "File is invalid. ',' missing at the end of '%s'.";
	private static final String CLOSING_BRACE_MISSING = "File is invalid. '}' missing at the end of '%s'.";
	
	private static final Pattern pattern = Pattern.compile("\\d+");
	
	private Scanner scanner;
	private Article article;

	/**
	 * Constructs an ArticleReader with the given Scanner object.
	 *
	 * @param scanner The Scanner object used to read the input source.
	 */
	ArticleReader(Scanner scanner) {
		this.scanner = scanner;
		this.article = new Article();
	}


	/**
	 * Reads and parses the article information from the input source.
	 *
	 * @return The populated Article object.
	 * @throws FileInvalidException If any parsing error occurs.
	 */
	public Article readArticle() throws FileInvalidException {
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine().trim();
			
			if (line.isBlank()) {
				continue;
			}
			
			if (line.equals(CLOSING_BRACE)) {
				break;
			}
			
			parseLine(line);
		}
		article.validate(); // Ensure all fields are populated
		return this.article;
	}
	
	private void parseLine(String line) throws FileInvalidException {
		//if (!line.substring(line.length() - 1).equals(COMMA)) {
		if (!line.endsWith(COMMA)){
			throw new FileInvalidException(String.format(COMMA_MISSING, line));
		}

		line = line.substring(0, line.length() - 1); // Remove the trailing comma
		
		if (isNumeric(line)) {
			parseId(line);
			return;
		}
		
		if (line.startsWith(AUTHOR_PREFIX)) {
			//parseAuthor(line);
			article.setAuthor(extractValue(line, AUTHOR_PREFIX));
			return;
		}
		
		if (line.startsWith(JOURNAL_PREFIX)) {
			parseJournal(line);
			return;
		}
		
		if (line.startsWith(TITLE_PREFIX)) {
			parseTitle(line);
			return;
		}
		
		if (line.startsWith(YEAR_PREFIX)) {
			parseYear(line);
			return;
		}
		
		if (line.startsWith(VOLUME_PREFIX)) {
			parseVolume(line);
			return;
		}
		
		if (line.startsWith(NUMBER_PREFIX)) {
			parseNumber(line);
			return;
		}
		
		if (line.startsWith(PAGES_PREFIX)) {
			parsePages(line);
			return;
		}
		
		if (line.startsWith(KEYWORDS_PREFIX)) {
			parseKeywords(line);
			return;
		}
		
		if (line.startsWith(DOI_PREFIX)) {
			parseDoi(line);
			return;
		}
		
		if (line.startsWith(ISSN_PREFIX)) {
			parseIssn(line);
			return;
		}
		
		if (line.startsWith(MONTH_PREFIX)) {
			parseMonth(line);
			return;
		}
		
		//if (line == CLOSING_BRACE) {
		if (line.equals(CLOSING_BRACE)) {
			return;
		}
		
		throw new FileInvalidException(String.format(INVALID_FILE_FORMAT, line));
	}

	/**
	 * Checks if the given string is numeric.
	 *
	 * @param line The string to check.
	 * @return true if the string is numeric, false otherwise.
	 */
	public boolean isNumeric(String line) {
	    if (line == null) {
	        return false; 
	    }
	    return pattern.matcher(line).matches();
	}

	/**
	 * Extracts the value from a line by removing the given prefix and the closing brace.
	 *
	 * @param line   The input line from which to extract the value.
	 * @param prefix The prefix to remove from the line.
	 * @return The extracted value without the prefix and closing brace.
	 * @throws FileInvalidException If the line does not end with a closing brace.
	 */
	private String extractValue(String line, String prefix) throws FileInvalidException {
		if (!line.endsWith(CLOSING_BRACE)) {
			throw new FileInvalidException(String.format(CLOSING_BRACE_MISSING, line));
		}
		return line.substring(prefix.length(), line.length() - 1);
	}

	private void checkClosingBraces(String line) throws FileInvalidException {
		if (!line.substring(line.length() - 1).equals(CLOSING_BRACE)) {
			throw new FileInvalidException(String.format(CLOSING_BRACE_MISSING, line));
		}
	}
	
	private void parseId(String line) throws FileInvalidException {
		article.setId(line);
	}

	private void parseAuthor(String line) throws FileInvalidException {
		checkClosingBraces(line);
		
		line = line.replace(AUTHOR_PREFIX, "");
		line = line.replace(CLOSING_BRACE, "");
		
		article.setAuthor(line);
	}
	
	private void parseJournal(String line) throws FileInvalidException {
		checkClosingBraces(line);
		
		line = line.replace(JOURNAL_PREFIX, "");
		line = line.replace(CLOSING_BRACE, "");
		
		article.setJournal(line);
	}
	
	private void parseTitle(String line) throws FileInvalidException {
		checkClosingBraces(line);
		
		line = line.replace(TITLE_PREFIX, "");
		line = line.replace(CLOSING_BRACE, "");
		
		article.setTitle(line);
	}
	
	private void parseYear(String line) throws FileInvalidException {
		checkClosingBraces(line);
		
		line = line.replace(YEAR_PREFIX, "");
		line = line.replace(CLOSING_BRACE, "");
		
		article.setYear(line);
	}
	
	private void parseVolume(String line) throws FileInvalidException {
		checkClosingBraces(line);
		
		line = line.replace(VOLUME_PREFIX, "");
		line = line.replace(CLOSING_BRACE, "");
		
		article.setVolume(line);
	}
	
	private void parseNumber(String line) throws FileInvalidException {
		checkClosingBraces(line);
		
		line = line.replace(NUMBER_PREFIX, "");
		line = line.replace(CLOSING_BRACE, "");
		
		article.setNumber(line);
	}
	
	private void parsePages(String line) throws FileInvalidException {
		checkClosingBraces(line);
		
		line = line.replace(PAGES_PREFIX, "");
		line = line.replace(CLOSING_BRACE, "");
		
		article.setPages(line);
	}
	
	private void parseKeywords(String line) throws FileInvalidException {
		checkClosingBraces(line);
		
		line = line.replace(KEYWORDS_PREFIX, "");
		line = line.replace(CLOSING_BRACE, "");
		
		article.setKeywords(line);
	}
	
	private void parseDoi(String line) throws FileInvalidException {
		checkClosingBraces(line);
		
		line = line.replace(DOI_PREFIX, "");
		line = line.replace(CLOSING_BRACE, "");
		
		article.setDoi(line);
	}
	
	private void parseIssn(String line) throws FileInvalidException {
		checkClosingBraces(line);
		
		line = line.replace(ISSN_PREFIX, "");
		line = line.replace(CLOSING_BRACE, "");
		
		article.setIssn(line);
	}
	
	private void parseMonth(String line) throws FileInvalidException {
		checkClosingBraces(line);
		
		line = line.replace(MONTH_PREFIX, "");
		line = line.replace(CLOSING_BRACE, "");
		
		article.setMonth(line);
	}
}
