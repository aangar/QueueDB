package queuedb;

import java.util.List;
import java.util.stream.Collectors;

import queuedb.DAO.PersonDAO;
import queuedb.DAO.SampleDocumentDAO;
import queuedb.DAO.tests.SampleDocumentDAOTest;
import queuedb.Objects.Person;
import queuedb.Objects.SampleDocument;

/**
 * Class for test methods related to queues.
 * <br>
 * </br>
 * 
 * @author aangar, 2022
 */
public class QueueDBTest {
    public static void runTests(String dir) {
        SampleDocumentDAOTest sampDocDAOTest = new SampleDocumentDAOTest(dir + "sample-documents/test/");
        sampDocDAOTest.runTests();
    }

    public static void runSampleDocumentTest(String dir) {
        SampleDocumentDAO sampDocDAO = new SampleDocumentDAO(dir + "sample-documents/");
        List<SampleDocument> sampleDocuments = List.of("alpha", "beta", "charlie", "delta")
                .stream()
                .map(SampleDocument::convertToSampleDoc)
                .collect(Collectors.toList());
        // sampDocDAO.saveDocuments(sampleDocuments);
        // sampDocDAO.findAll();
        // sampDocDAO.clearCollection("Sample Documents");
        List<SampleDocument> findAllResults = sampDocDAO.findAll();
        findAllResults.stream().forEach(x -> {
            String base = "Name %s with ID %s, created %s";
            String format = String.format(base, x.getName(), x.getId(), x.getGenerationDate());
            System.out.println(format);
        });
    }

    public void runPersonsDAOTest(String dir) {
        List<Person> personDocuments = List.of("Riley", "Alpine", "Tetca", "Doyin", "Trilek")
                .stream()
                .map(Person::generatePersonFromName)
                .collect(Collectors.toList());
        PersonDAO personDAO = new PersonDAO(dir + "persons/");
        List<Person> savedPersons = personDAO.savePersons(personDocuments);
        System.out.println("Saved Persons: " + savedPersons.size());
        personDAO.clearCollection("Person");
    }
}