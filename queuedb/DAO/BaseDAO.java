package queuedb.DAO;

import java.io.File;
import queuedb.DatabaseParser;

public class BaseDAO {
    public String DIR_TO_COLLECTION;

    public void clearCollection(String collectionName) {
        if (this.DIR_TO_COLLECTION == null || this.DIR_TO_COLLECTION.isEmpty()) {
            System.err.println("Collection Directory not specified, unable to clear.");
            return;
        }
        File baseDir = new File(DIR_TO_COLLECTION);
        String[] existingFiles = baseDir.list();
        if (existingFiles.length < 1) {
            return;
        }
        if (existingFiles.length >= 1) {
            for (String file : existingFiles) {
                new File(DIR_TO_COLLECTION + file).delete();
            }
        }

        baseDir.delete();
        System.out.println(String.format("Collection %s was cleared and removed.", collectionName));
    }
}