package ca.Mirzaagha_Ahmadian;
import java.util.Objects;
/**
 * Represents a scholarly article with various bibliographic details.
 * This class encapsulates the properties of a scholarly article and provides
 * functionality to output the article's details in several citation formats.
 *
 * The class supports formatting for the following citation styles:
 * - ACM (Association for Computing Machinery) which is commonly used in computing and information technology publications.
 * - IEEE (Institute of Electrical and Electronics Engineers) which is widely used in engineering and electronics.
 * - NJ (Name/Year) which is a common format in various scientific disciplines.
 *
 * Each citation format method constructs a string representation of the article's
 * bibliographic citation according to the rules of the respective citation style.
 *
 * The class also includes a validation method to ensure that all necessary fields
 * are present and non-empty before attempting to generate the formatted citation strings.
 *
 * Example usage:
 * Article article = new Article();
 * article.setId("12345");
 * article.setAuthor("Doe, John and Smith, Jane");
 * // ... set other fields
 * article.validate();
 * String acmCitation = article.toAcmString(1);
 * String ieeeCitation = article.toIeeeString();
 * String njCitation = article.toNjString();
 *
 * Note: The class assumes that all fields are properly set before calling the citation format methods.
 * If any field is missing, the validate method will throw a FileInvalidException.
 *
 * @author Zahra Mirzaagha
 * @author Alireza Ahmadian
 * @version 2022-04-17
 */


public class Article {
	private static final String AMPERSAND = "&";
	private static final String COMMA = ",";
	private static final String SPACE_AND = " and";
	private static final String ET_AL = "et al";
	private static final String AND = "and";
	private static final String FIELD_IS_EMPTY = "File is invalid. Field '%s' is empty. Processing stoped at this point. Other empty fields may be present as well!";
	private static final String ACM_FORMAT = "[%d]	%s. %s. %s. %s. %s, %s (%s), %s. DOI:%s";
	private static final String IEEE_FORMAT = "%s. \"%s\", %s, vol. %s, no. %s, p. %s, %s %s.";
	private static final String NJ_FORMAT = "%s. %s. %s. %s, %s(%s).";
	
	private String id;
	private String author;
	private String journal;
	private String title;
	private String year;
	private String volume;
	private String number;
	private String pages;
	private String keywords;
	private String doi;
	private String issn;
	private String month;
	
	public void setId(String id) {
		this.id = id;
	}
	
	/*private String getAcmAuthor() {
		if (author.indexOf(AND) == -1) {
			return author;
		}
		String firstAuthor = author.substring(0, author.indexOf(AND));
		return firstAuthor.concat(ET_AL);
	}*/

	// Private helper methods for formatting authors according to citation styles
	private String getAcmAuthor() {
		if (!author.contains(AND)) {
			return author;
		}
		String firstAuthor = author.substring(0, author.indexOf(AND));
		return firstAuthor.concat(ET_AL);
	}
	
	private String getIeeeAuthor() {
		return author.replace(SPACE_AND, COMMA);
	}
	
	private String getNjAuthor() {
		return author.replace(AND, AMPERSAND);
	}
	
	public void setAuthor(String author) {
		this.author = author;
	}
	
	public void setJournal(String journal) {
		this.journal = journal;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public void setPages(String pages) {
		this.pages = pages;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public void setDoi(String doi) {
		this.doi = doi;
	}

	public void setIssn(String issn) {
		this.issn = issn;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	/*
	private void checkIfEmpty(String line, String field) throws FileInvalidException {
		if (line.isBlank()) {
			throw new FileInvalidException(String.format(FIELD_IS_EMPTY, field));
		}
	}*/


	// Private helper method to check if a field is empty
	private void checkIfEmpty(String field, String fieldName) throws FileInvalidException {
		if (Objects.requireNonNull(field).isBlank()) {
			throw new FileInvalidException(String.format(FIELD_IS_EMPTY, fieldName));
		}
	}

	// Validation method to ensure all fields are set
	public void validate() throws FileInvalidException {
		checkIfEmpty(id, "id");
		checkIfEmpty(author, "author");
		checkIfEmpty(journal, "journal");
		checkIfEmpty(title, "title");
		checkIfEmpty(year, "year");
		checkIfEmpty(volume, "volume");
		checkIfEmpty(number, "number");
		checkIfEmpty(pages, "pages");
		checkIfEmpty(keywords, "keywords");
		checkIfEmpty(doi, "doi");
		checkIfEmpty(issn, "issn");
		checkIfEmpty(month, "month");
	}
	
	public String toAcmString(int index) {
		return String.format(ACM_FORMAT, index, getAcmAuthor(), year,
				title, journal, volume, number, year, pages, doi);
	}
	
	public String toIeeeString() {
		return String.format(IEEE_FORMAT, getIeeeAuthor(), title,
				journal, volume, number, pages, month, year);
	}
	
	public String toNjString() {

		return String.format(NJ_FORMAT, getNjAuthor(), title,
				journal, volume, pages, year);
	}
}
