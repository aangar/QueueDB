package queuedb;

import java.util.List;
import java.util.stream.Collectors;

import queuedb.DAO.PersonDAO;
import queuedb.DAO.SampleDocumentDAO;
import queuedb.Objects.Person;
import queuedb.Objects.SampleDocument;

/**
 * Class for test methods related to queues.
 * <br>
 * </br>
 * What this does as of now:
 * <ol>
 * <li>Takes in a list of names.</li>
 * <li>Coverts said names into a DynamicQueue (the queue with target type).</li>
 * <li>Ensures the DB folder exists. If not, creates it.</li>
 * <li>Ensures there are no documents saved. If there are, purges them./li>
 * <li>Converts documents to JSON and saved them locally./li>
 * <li>Reads all files, and converts them into the target type./li>
 * </ol>
 * 
 * @author aangar, 2022
 */
public class QueueDBTest {
    public static void runTests(String dir) {
        SampleDocumentDAO sampDocDAO = new SampleDocumentDAO(dir + "sample-documents/");
        List<SampleDocument> sampleDocuments = List.of("alpha", "beta", "charlie", "delta")
                .stream()
                .map(SampleDocument::convertToSampleDoc)
                .collect(Collectors.toList());
        List<SampleDocument> result = sampDocDAO.saveDocuments(sampleDocuments);
        System.out.println("Saved SampleDocuments: " + result.size());
        List<Person> personDocuments = List.of("Riley", "Alpine", "Tetca", "Doyin", "Trilek")
            .stream()
            .map(Person::generatePersonFromName)
            .collect(Collectors.toList());
        PersonDAO personDAO = new PersonDAO(dir + "persons/");
        List<Person> savedPersons = personDAO.savePersons(personDocuments);
        System.out.println("Saved Persons: " + savedPersons.size());
        List<SampleDocument> foundPersons = sampDocDAO.findAll();
        System.out.println("Samp Doc Size: " foundPersons.size());
        sampDocDAO.clearCollection("SampleDocument");
        personDAO.clearCollection("Person");
    }
}