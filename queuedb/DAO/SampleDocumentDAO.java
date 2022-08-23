package queuedb.DAO;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.stream.Collectors;

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

    public Optional<SampleDocument> findById(String id) {
        if (id == null || id.isEmpty()) {
            System.err.println("Id cannot be empty.");
            return Optional.empty();
        }
        return this.dbParser.findAll()
            .stream()
            .filter(x -> x.getId().equals(id))
            .findFirst();
    }

    public List<SampleDocument> findByName(String name) {
        return this.dbParser.findAll().stream()
            .filter(doc -> {
                Optional<String> docName = Optional.ofNullable(doc.getName());
                if (docName.isPresent()) {
                    return docName.get().equals(name);
                }
                return false;
            }).collect(Collectors.toList());
    }

    /**
     * Save one method.
     * 
     * @param doc SampleDocument, the object to save.
     * @return <code>true</code> if written, <code>false</code> otherwise.
     */
    public Optional<SampleDocument> saveOne(SampleDocument doc) {
        if (doc == null) {
            System.err.println("Sampledocument cannot be null.");
            return Optional.empty();
        }
        return this.dbParser.writeFile(doc);
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
            Optional<SampleDocument> didSave = this.dbParser.writeFile(sampDoc);
            if (didSave.isPresent()) {
                saved.add(didSave.get());
            }
        }
        return saved;
    }
}
