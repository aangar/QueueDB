package queuedb;

import java.util.List;
import java.time.Instant;

/**
 * Sample Document Class
 * @author aangar, 2022
 */
public class SampleDocument extends DatabaseDocument {
    private String name;
    private Instant generationDate;

    SampleDocument() {
        generateId();
    }

    SampleDocument(String name, Instant genDate) {
        generateId();
        this.name = name;
        this.generationDate = genDate;
    }

    public String getName() {
        return this.name;
    }

    public Instant getGenerationDate() {
        return this.generationDate;
    }

    public static List<String> getKeys() {
        return List.of("name", "id", "generationDate");
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGenerationDate(String genDate) {
        this.generationDate = Instant.parse(genDate);
    }

    /**
     * Makes a string into a SampleDocument
     * @param name the name, String.
     * @return a new SampleDocument.
     */
    public static SampleDocument convertToSampleDoc(String name) {
        return new SampleDocument(name, Instant.now());
    }
}
