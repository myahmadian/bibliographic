package ca.Mirzaagha_Ahmadian;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

public class FileProcessor {
    private static final String INPUT_FILE_PATTERN = "Latex%d.bib";
    private static final String ACM_OUTPUT_FILE_PATTERN = "ACM%d.json";
    private static final String IEEE_OUTPUT_FILE_PATTERN = "IEEE%d.json";
    private static final String NJ_OUTPUT_FILE_PATTERN = "NJ%d.json";

    private static final String COULD_NOT_CREATE_ERROR_MESSAGE = "Could not create file %s.";
    private static final String NO_FILE_ERROR_MESSAGE = "File %s not found.";
    private static final String ARTICLE_MISSING = "File is invalid. Article missing.";
    private static final String FILE_EMPTY = "File is invalid. No article found.";

    private static final String ARTICLE = "@ARTICLE{";

    private final Path inputFilePath;
    private final Path acmFilePath;
    private final Path ieeeFilePath;
    private final Path njFilePath;
    private final List<Article> articles;

    public FileProcessor(int index) {
        inputFilePath = Paths.get(String.format(INPUT_FILE_PATTERN, index));
        acmFilePath = Paths.get(String.format(ACM_OUTPUT_FILE_PATTERN, index));
        ieeeFilePath = Paths.get(String.format(IEEE_OUTPUT_FILE_PATTERN, index));
        njFilePath = Paths.get(String.format(NJ_OUTPUT_FILE_PATTERN, index));
        articles = new ArrayList<>();
    }

    public void openInputFile() throws NoFileException {
        if (!Files.exists(inputFilePath)) {
            throw new NoFileException(String.format(NO_FILE_ERROR_MESSAGE, inputFilePath));
        }
    }

    public void prepareOutputFiles() throws FileCreationFailedException {
        try {
            Files.createFile(acmFilePath);
            Files.createFile(ieeeFilePath);
            Files.createFile(njFilePath);
        } catch (IOException e) {
            throw new FileCreationFailedException(String.format(COULD_NOT_CREATE_ERROR_MESSAGE, e.getMessage()));
        }
    }

    public void close() {
        deleteOutputFiles();
    }

    private void deleteOutputFiles() {
        try {
            Files.deleteIfExists(acmFilePath);
            Files.deleteIfExists(ieeeFilePath);
            Files.deleteIfExists(njFilePath);
        } catch (IOException e) {
            // Log the exception using a logging framework
        }
    }

    public void process() throws ProcessFaultedException {
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
                Article article = articleReader.readArticle();
                article.validate(); // Validate immediately after reading
                articles.add(article);
            }
            if (articles.isEmpty()) {
                throw new FileInvalidException(FILE_EMPTY);
            }
        } catch (IOException | FileInvalidException e) {
            throw new ProcessFaultedException(inputFilePath.toString(), e.getMessage());
        }

        populateFiles();
    }

    private void populateFiles() {
        populateFile(acmFilePath, (article) -> article.toAcmString());
        populateFile(ieeeFilePath, (article) -> article.toIeeeString());
        populateFile(njFilePath, (article) -> article.toNjString());
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
}