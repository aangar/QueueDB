package queuedb;

import java.time.Instant;

/**
 * Sample Document Class
 * 
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

    public void setName(String name) {
        this.name = name;
    }

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
