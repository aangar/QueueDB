package queuedb.DAO.tests;

import java.io.File;
import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.stream.Collectors;

import queuedb.DatabaseParser;
import queuedb.DAO.SampleDocumentDAO;
import queuedb.Objects.SampleDocument;

public class SampleDocumentDAOTest extends BaseTest {
    public final SampleDocumentDAO sampDocDAO;

    public SampleDocumentDAOTest(String dir) {
        this.sampDocDAO = new SampleDocumentDAO(dir);
        this.TEST_COLLECTION_DIR = dir;
        this.TestFileName = "SampleDocumentDAOTest";
    }

    /**
     * tests the save method of the DAO.
     */
    public void test_save() {
        List<SampleDocument> toSaveDocuments = List
                .of("ALPINE_TESTDOC", "BRECA_TESTDOC", "CACHE_TESTDOC", "DESPIAN_TESTDOC")
                .stream()
                .map(SampleDocument::convertToSampleDoc)
                .collect(Collectors.toList());
        List<SampleDocument> savedDocuments = this.sampDocDAO.saveDocuments(toSaveDocuments);
        List<SampleDocument> foundDocs = this.sampDocDAO.findAll();
        this.logTestResult(savedDocuments.size() == foundDocs.size(), "test_save");
    }

    /**
     * ensures the passing in an empty list will return an empty list.
     */
    public void test_save_empty() {
        List<SampleDocument> docs = this.sampDocDAO.saveDocuments(new ArrayList<>());
        this.logTestResult(docs.size() < 1, "test_save_null");
    }

    /**
     * ensures that passing a null will not save.
     */
    public void test_save_null() {
        List<SampleDocument> docs = this.sampDocDAO.saveDocuments(null);
        this.logTestResult(docs.size() < 1, "test_save_null");
    }

    public void test_saveOne() {
        SampleDocument document = new SampleDocument("TETRACORE_TESTDOC", Instant.now());
        Optional<SampleDocument> saved = this.sampDocDAO.saveOne(document);

        List<SampleDocument> foundDocs = this.sampDocDAO.findAll();
        this.logTestResult(saved.isPresent() && (foundDocs.size() == 1), "test_SaveOne");
    }

    /**
     * ensures that trying to save a null document will not work.
     */
    public void test_saveOne_null() {
        this.logTestResult(this.sampDocDAO.saveOne(null).isEmpty(), "test_saveOne_null");
    }

    public void test_findAll() {
        List<SampleDocument> toSaveDocuments = List
                .of("ALPINE_TESTDOC", "BRECA_TESTDOC", "CACHE_TESTDOC", "DESPIAN_TESTDOC")
                .stream()
                .map(SampleDocument::convertToSampleDoc)
                .collect(Collectors.toList());
        Queue<SampleDocument> queue = new LinkedList<SampleDocument>(toSaveDocuments);
        DatabaseParser<SampleDocument> db = new DatabaseParser<>(SampleDocument.class, this.TEST_COLLECTION_DIR);
        List<SampleDocument> saved = new ArrayList<>();

        while (queue.size() > 0) {
            SampleDocument sampDoc = queue.poll();
            if (sampDoc.getId() == null || sampDoc.getId().isEmpty()) {
                sampDoc.generateId();
            }
            Optional<SampleDocument> didSave = db.writeFile(sampDoc);
            if (didSave.isPresent()) {
                saved.add(sampDoc);
            }
        }

        String[] filesInTest = new File(this.TEST_COLLECTION_DIR).list();
        List<SampleDocument> foundDocs = this.sampDocDAO.findAll();

        boolean result = (foundDocs.size() == saved.size()) && (filesInTest.length == saved.size());
        this.logTestResult(result, "test_findAll");
    }

    public void test_FindById_Normal() {
        DatabaseParser<SampleDocument> db = new DatabaseParser<>(SampleDocument.class, this.TEST_COLLECTION_DIR);
        SampleDocument d1 = SampleDocument.convertToSampleDoc("ALPINE");
        SampleDocument d2 = SampleDocument.convertToSampleDoc("BETANINE");
        db.writeFile(d1);
        String target = db.writeFile(d2).get().getId();
        Optional<SampleDocument> result = this.sampDocDAO.findById(target);
        if (result.isPresent()) {
            this.logTestResult(result.get().getId().equals(target), "test_FindById_Normal");
        } else {
            this.logTestResult(false, "test_FindById_Normal");
        }
    }
}
