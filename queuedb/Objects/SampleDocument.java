package queuedb.Objects;

import java.time.Instant;

/**
 * Sample Document Class
 * 
 * @author aangar, 2022
 */
public class SampleDocument extends DatabaseDocument {
    private String name;
    private Instant generationDate;

    /**
     * Default empty constructor.
     */
    public SampleDocument() {
        generateId();
    }

    /**
     * SampleDocument constructor with name and generation date.
     * @param name String, name for the document.
     * @param genDate Instant, generation date of the document.
     */
    public SampleDocument(String name, Instant genDate) {
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

    /**
     * {@inheritDoc}}
     */
    public void setParsedProperty(String propName, String val) {
        switch (propName) {
            case "id":
                this.setId(val);
                break;
            case "generationDate":
                this.setGenerationDate(val);
                break;
            case "name":
                this.setName(val);
                break;
            default:
                System.err.println("Property Name not Recognized. Verify cases in setParsedProperty.");
        }
    }

    /**
     * sets the name for the document
     * @param name String, name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * sets the generation date.
     * @param genDate Instant, generation date.
     */
    public void setGenerationDate(String genDate) {
        this.generationDate = Instant.parse(genDate);
    }

    /**
     * Makes a string into a SampleDocument
     * 
     * @param name the name, String.
     * @return a new SampleDocument.
     */
    public static SampleDocument convertToSampleDoc(String name) {
        return new SampleDocument(name, Instant.now());
    }
}
