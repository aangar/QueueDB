package queuedb.DAO.tests;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

import queuedb.DatabaseParser;
import queuedb.DAO.SampleDocumentDAO;
import queuedb.Objects.SampleDocument;

public class SampleDocumentDAOTest {
    private final SampleDocumentDAO sampDocDAO;
    private static final String SUCCESS_COLOR = "\u001B[32m";
    private static final String FAIL_COLOR = "\u001B[31m";
    private static final String ANSI_RESET = "\u001B[0m";
    private String TEST_COLLECTION_DIR;

    public SampleDocumentDAOTest(String dir) {
        this.sampDocDAO = new SampleDocumentDAO(dir);
        this.TEST_COLLECTION_DIR = dir;
    }

    public void runTests() {
        this.clearTestCollection();
        test_save();
        test_saveOne();
        test_findAll();
        new File(this.TEST_COLLECTION_DIR).delete();
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
        boolean result = savedDocuments.size() == foundDocs.size();
        String value = result ? "PASSED" : "FAILED";
        String color = result ? SUCCESS_COLOR : FAIL_COLOR;
        System.out.println("SampleDocumentDAOTest:test_save: " + color + value + ANSI_RESET);
        this.clearTestCollection();
    }

    private void test_saveOne() {
        SampleDocument document = new SampleDocument("TETRACORE_TESTDOC", Instant.now());
        boolean saved = this.sampDocDAO.saveOne(document);

        List<SampleDocument> foundDocs = this.sampDocDAO.findAll();

        boolean result = saved && (foundDocs.size() == 1);
        String value = result ? "PASSED" : "FAILED";
        String color = result ? SUCCESS_COLOR : FAIL_COLOR;
        System.out.println("SampleDocumentDAOTest:test_saveOne: " + color + value +
                ANSI_RESET);
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
        String value = result ? "PASSED" : "FAILED";
        String color = result ? SUCCESS_COLOR : FAIL_COLOR;
        System.out.println("SampleDocumentDAOTest:test_findAll: " + color + value + ANSI_RESET);
        this.clearTestCollection();
    }

    private void clearTestCollection() {
        if (this.TEST_COLLECTION_DIR == null || this.TEST_COLLECTION_DIR.isEmpty()) {
            System.err.println("Collection Directory not specified, unable to clear.");
            return;
        }
        File baseDir = new File(TEST_COLLECTION_DIR);
        String[] existingFiles = baseDir.list();
        if (existingFiles.length < 1) {
            return;
        }
        if (existingFiles.length >= 1) {
            for (String file : existingFiles) {
                if (file.equals("test")) {
                    continue;
                }
                new File(TEST_COLLECTION_DIR + file).delete();
            }
        }
    }

}
