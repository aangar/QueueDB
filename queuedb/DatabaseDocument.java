package queuedb;

/**
 * Document to extend, holds the ID and generation.
 * @author aangar, 2022
 */
public class DatabaseDocument {
    public String UUID;

    public String getUUID() {
        return this.UUID;
    }
    /**
     * Method to generate the UUID of the document.
     */
    public void generateUUID() {
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
        this.UUID = UUID;
    }
}
