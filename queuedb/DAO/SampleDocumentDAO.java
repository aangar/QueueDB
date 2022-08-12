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
public class SampleDocumentDAO extends BaseDAO {
    private final DatabaseParser<SampleDocument> dbParser;

    /**
     * Default constructor.
     * 
     * @param DIR the directory path to the collection
     */
    public SampleDocumentDAO(String DIR) {
        if (!DIR.isEmpty() || DIR != null) {
            if (!DIR.substring(DIR.length() - 1).equals("/")) {
                DIR = DIR + "/";
            }
            new File(DIR).mkdirs();
            this.DIR_TO_COLLECTION = DIR;
        }
        this.dbParser = new DatabaseParser<SampleDocument>(SampleDocument.class, this.DIR_TO_COLLECTION);
    }

    /**
     * Finds all SampleDocuments
     * 
     * @return a list of found SampleDocuments
     */
    public List<SampleDocument> findAll() {
        return this.dbParser.findAll();
    }

    public boolean saveOne(SampleDocument doc) {
        if (doc == null) {
            System.err.println("SampleDocument cannot be null for saveOne!");
            return false;
        }
        if (doc.getId() == null || doc.getId().isEmpty()) {
            doc.generateId();
        }
        try {
            FileWriter writer = new FileWriter(
                    this.DIR_TO_COLLECTION + "SampleDocument_" + doc.getName() + "_" + doc.getId()
                            + ".json");
            writer.write("{\n");
            writer.write(String.format("   \"id\": \"%s\",\n", doc.getId()));
            writer.write(String.format("   \"name\": \"%s\",\n", doc.getName()));
            writer.write(String.format("   \"generationDate\": \"%s\"\n", doc.getGenerationDate()));
            writer.write("}");
            writer.close();
        } catch (IOException e) {
            System.out.println("damn there was an error");
            System.out.println(e);
            return false;
        }

        return true;
    }

    /**
     * saves a list of SampleDocuments to the database.
     * 
     * @param docs a list of documents to save.
     * @return the list of saved SampleDocuments.
     */
    public List<SampleDocument> saveDocuments(List<SampleDocument> docs) {
        if (docs == null || docs.isEmpty()) {
            System.err.println("List of SampleDocuments cannot be empty for saveDocuments!");
            return new ArrayList<>();
        }
        Queue<SampleDocument> queue = new LinkedList<SampleDocument>(docs);
        List<SampleDocument> saved = new ArrayList<>();
        while (queue.size() > 0) {
            SampleDocument sampDoc = queue.poll();
            if (sampDoc.getId() == null || sampDoc.getId().isEmpty()) {
                sampDoc.generateId();
            }
            try {
                FileWriter writer = new FileWriter(
                        this.DIR_TO_COLLECTION + "SampleDocument_" + sampDoc.getName() + "_" + sampDoc.getId()
                                + ".json");
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
}
