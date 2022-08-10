package queuedb.DAO;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import queuedb.DatabaseParser;
import queuedb.Objects.SampleDocument;

/**
 * SampleDocumentDAO Class
 */
public class SampleDocumentDAO {
    private final DatabaseParser<SampleDocument> sampleDocumentParser;
    private String DIR_TO_COLLECTION;

    /**
     * Default constructor.
     * 
     * @param DIR the directory path to the collection
     */
    public SampleDocumentDAO(String DIR) {
        this.sampleDocumentParser = new DatabaseParser<SampleDocument>(SampleDocument.class);
        if (!DIR.isEmpty() || DIR != null) {
            new File(DIR).mkdir();
            this.DIR_TO_COLLECTION = DIR;
        }
    }

    /**
     * saves a list of SampleDocuments to the database.
     * 
     * @param docs a list of documents to save.
     * @return the list of saved SampleDocuments.
     */
    public List<SampleDocument> saveDocuments(List<SampleDocument> docs) {
        Queue<SampleDocument> queue = new LinkedList<SampleDocument>(docs);
        List<SampleDocument> saved = new ArrayList<>();
        while (queue.size() > 0) {
            SampleDocument sampDoc = queue.poll();
            if (sampDoc.getId() == null || sampDoc.getId().isEmpty()) {
                sampDoc.generateId();
            }
            try {
                FileWriter writer = new FileWriter(
                        DIR_TO_COLLECTION + "SampleDocument_" + sampDoc.getName() + "_" + sampDoc.getId() + ".json");
                writer.write("{\n");
                writer.write(String.format("   \"id\": \"%s\",\n", sampDoc.getId()));
                writer.write(String.format("   \"name\": \"%s\",\n", sampDoc.getName()));
                writer.write(String.format("   \"generationDate\": \"%s\"\n", sampDoc.getGenerationDate()));
                writer.write("}");
                writer.close();
                saved.add(sampDoc);
            } catch (IOException e) {
                System.out.println("damn there was an error");
                System.out.println(e);
            }
        }
        return saved;
    }

    /**
     * Clears the SampleDocument collection.
     */
    public void clearCollection() {
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
    }

}
