package queuedb.DAO.tests;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;
import queuedb.DAO.SampleDocumentDAO;
import queuedb.Objects.SampleDocument;

public class SampleDocumentDAOTest extends BaseTest{
    public final SampleDocumentDAO sampDocDAO;


    public SampleDocumentDAOTest(String dir) {
        this.sampDocDAO = new SampleDocumentDAO(dir);
        this.TEST_COLLECTION_DIR = dir;
        this.TestFileName = "SampleDocumentDAOTest";
    }

    public void runTests() {
        this.clearTestCollection();
        test_save();
        test_saveOne();
        test_findAll();
        this.removeTestCollection();
    }

    /**
     * tests the save method of the DAO.
     */
    private void test_save() {
        List<SampleDocument> toSaveDocuments = List
                .of("ALPINE_TESTDOC", "BRECA_TESTDOC", "CACHE_TESTDOC", "DESPIAN_TESTDOC")
                .stream()
                .map(SampleDocument::convertToSampleDoc)
                .collect(Collectors.toList());
        List<SampleDocument> savedDocuments = this.sampDocDAO.saveDocuments(toSaveDocuments);
        List<SampleDocument> foundDocs = this.sampDocDAO.findAll();
        this.logTestResult(savedDocuments.size() == foundDocs.size(), "test_save");
        this.clearTestCollection();
    }

    private void test_saveOne() {
        SampleDocument document = new SampleDocument("TETRACORE_TESTDOC", Instant.now());
        boolean saved = this.sampDocDAO.saveOne(document);

        List<SampleDocument> foundDocs = this.sampDocDAO.findAll();
        this.logTestResult(saved && (foundDocs.size() == 1), "test_SaveOne");
        this.clearTestCollection();
    }

    private void test_findAll() {
        List<SampleDocument> toSaveDocuments = List
                .of("ALPINE_TESTDOC", "BRECA_TESTDOC", "CACHE_TESTDOC", "DESPIAN_TESTDOC")
                .stream()
                .map(SampleDocument::convertToSampleDoc)
                .collect(Collectors.toList());
        Queue<SampleDocument> queue = new LinkedList<SampleDocument>(toSaveDocuments);

        while (queue.size() > 0) {
            SampleDocument sampDoc = queue.poll();
            if (sampDoc.getId() == null || sampDoc.getId().isEmpty()) {
                sampDoc.generateId();
            }
            try {
                FileWriter writer = new FileWriter(
                        this.TEST_COLLECTION_DIR + "SampleDocument_" + sampDoc.getName() + "_" + sampDoc.getId()
                                + ".json");
                writer.write("{\n");
                writer.write(String.format("   \"id\": \"%s\",\n", sampDoc.getId()));
                writer.write(String.format("   \"name\": \"%s\",\n", sampDoc.getName()));
                writer.write(String.format("   \"generationDate\": \"%s\"\n", sampDoc.getGenerationDate()));
                writer.write("}");
                writer.close();
            } catch (IOException e) {
                System.out.println("damn there was an error");
                System.out.println(e);
            }
        }

        String[] filesInTest = new File(this.TEST_COLLECTION_DIR).list();
        List<SampleDocument> foundDocs = this.sampDocDAO.findAll();

        boolean result = (foundDocs.size() == filesInTest.length) && (filesInTest.length == toSaveDocuments.size());
        this.logTestResult(result, "test_findAll");
        this.clearTestCollection();
    }
}
