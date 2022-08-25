package queuedb.Objects;

import java.io.Serializable;

/**
 * Document to extend, holds the ID and generation.
 * 
 * @author aangar, 2022
 */
public class DatabaseDocument implements Serializable {
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
        char[] letters = { 'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p', 'a', 's', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'z', 'x', 'c', 'v', 'b', 'n', 'm' };
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
     * For use with the old method of reading the database files.
     * Converting from JSON to a class using <b>Reflection</b>, the property was read as a string,
     * then passed to this method with its' value and then set in the reflected object. At the moment
     * this is deprecated and no longer used, but could be used for a file formatting change down the road.
     * @author aangar
     * @deprecated since QueueDB_DynamicWriting to leverage <b>ObjectInputStream</b> in the read method.
     * @param propName the String value of the property name to be set during the read process, 
     * used in a switch case to determine the according setter to be called.
     * @param val the String value read from the JSON document, passed to the according setter method.
     */
    public void setParsedProperty(String propName, String val) {
        System.err.println("No conversion body was set, please do so.");
    }
}
