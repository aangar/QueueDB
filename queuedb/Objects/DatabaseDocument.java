package queuedb.Objects;

/**
 * Document to extend, holds the ID and generation.
 * 
 * @author aangar, 2022
 */
public class DatabaseDocument {
    public String id;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /**
     * Method to generate the UUID of the document.
     */
    public void generateId() {
        char[] letters = { 'q', 'e', 't', 'u', 'o', 'a' };
        String UUID = "";
        for (int i = 0; i < 4 * 4; i++) {
            if (i % 4 == 0 && i != 0) {
                UUID = UUID + "-";
            }
            if (i % 2 == 0) {
                int val = (int) Math.floor(Math.random() * letters.length);
                UUID = UUID + letters[val];
            } else {
                UUID = UUID + (int) Math.floor(Math.random() * 9);
            }
        }
        this.id = UUID;
    }

    /**
     * Method that sets the properties parsed from the database document. 
     * Will use a switch-case for the property name, and then calls a setter method. 
     * There is no casting by default, so make sure to do that in the called setter.
     * @implNote This must be an override method to work.
     * If the error "No conversion body was set, please do so.",
     * this means the override was never set and must be done for proper conversion.
     * @param property String, the property name.
     * @param val String, the value of the property, insensitive of cast type.
     */
    public void setParsedProperty(String propName, String val) {
        System.err.println("No conversion body was set, please do so.");
    }
}
