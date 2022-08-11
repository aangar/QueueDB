package queuedb;

import queuedb.DAO.tests.PersonDAOTest;
import queuedb.DAO.tests.SampleDocumentDAOTest;

/**
 * Class for test methods related to QueueDB
 * 
 * @author aangar, 2022
 */
public class QueueDBTest {
    public static void runTests(String dir) {
        SampleDocumentDAOTest sampDocDAOTest = new SampleDocumentDAOTest(dir + "sample-documents/test/");
        sampDocDAOTest.runTests();
        System.out.println(" ");
        PersonDAOTest personDAOTest = new PersonDAOTest(dir + "persons/test/");
        personDAOTest.runTests();
    }
}