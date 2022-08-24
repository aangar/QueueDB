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
     * Default Constructor
     */
    public SampleDocument() {
        generateId();
    }

    /**
     * Constructor with name and generation date.
     * @param name String, name.
     * @param genDate Instant, Generation Date.
     */
    public SampleDocument(String name, Instant genDate) {
        generateId();
        this.name = name;
        this.generationDate = genDate;
    }

    /**
     * Getter for the name.
     * @return String, name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Getter for the generation date.
     * @return Instant, the generation date.
     */
    public Instant getGenerationDate() {
        return this.generationDate;
    }

    /**
     * Override method to set parsed properties.
     * 
     * @param propName the parsed property name.
     * @param val      the string value from the JSON file.
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
     * {@inheritDoc}
     */
    public Object getProperty(String name) {
        switch (name) {
            case "id":
                return this.getId();
            case "generationDate":
                return this.getGenerationDate();
            case "name":
                return this.getName();
            default:
                System.err.println("Property Name not Recognized. Verify cases in setParsedProperty.");
        }
        return null;
    }

    /**
     * Name Setter
     * @param name String, name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Generation Setter.
     * @param genDate String, generationDate.
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
