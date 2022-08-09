package queuedb;

import java.time.Instant;

/**
 * Sample Document Class
 * @author aangar, 2022
 */
public class SampleDocument extends DatabaseDocument {
    private String name;
    private Instant generationDate;

    SampleDocument() {
        generateUUID();
    }

    SampleDocument(String name, Instant genDate) {
        generateUUID();
        this.name = name;
        this.generationDate = genDate;
    }

    public String getName() {
        return this.name;
    }

    public Instant getGenerationDate() {
        return this.generationDate;
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
