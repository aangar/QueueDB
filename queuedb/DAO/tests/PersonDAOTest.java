package queuedb.DAO.tests;

import java.util.List;
import java.util.Optional;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Collectors;
import queuedb.DatabaseParser;
import queuedb.DAO.PersonDAO;
import queuedb.Objects.Person;

/**
 * PersonDAOTest class.
 */
public class PersonDAOTest extends BaseTest {
    public final PersonDAO personDAO;

    public PersonDAOTest(String dir) {
        this.personDAO = new PersonDAO(dir);
        this.TEST_COLLECTION_DIR = dir;
        this.TestFileName = "PersonDAOTest";
    }

    /**
     * Tests the save one method.
     */
    public void test_saveOne() {
        Person person = new Person("GRAPHITE_TESTDOC", 22);
        this.logTestResult(this.personDAO.saveOne(person).isPresent(), "test_saveOne");
    }

    /**
     * ensures saving a null document will fail.
     */
    public void test_saveOne_null() {
        this.logTestResult(this.personDAO.saveOne(null).isEmpty(), "test_saveOne_null");
    }
    /**Tests the save method for multiple documents. */
    public void test_SaveMulti() {
        List<Person> docs = List.of("Riley", "Alpine", "Tetca", "Doyin", "Trilek")
                .stream()
                .map(Person::generatePersonFromName)
                .collect(Collectors.toList());
        this.logTestResult(this.personDAO.savePersons(docs).size() == docs.size(), "test_SaveMulti");
    }

    /**
     * ensures that trying to save an empty list will not work.
     */
    public void test_SaveMulti_Empty() {
        this.logTestResult(this.personDAO.savePersons(new ArrayList<>()).size() < 1, "test_SaveMulti_Empty");
    }

    /**
     * ensures that trying to save a null param will not work.
     */
    public void test_SaveMulti_Null() {
        this.logTestResult(this.personDAO.savePersons(null).size() < 1, "test_SaveMulti_Null");
    }
    
    /** Tests the findAll method. */
    public void test_findAll() {
        List<Person> docs = List.of("Riley", "Alpine", "Tetca", "Doyin", "Trilek")
                .stream()
                .map(Person::generatePersonFromName)
                .collect(Collectors.toList());
        Queue<Person> queue = new LinkedList<Person>(docs);
        List<Person> savedDocuments = new ArrayList<>();
        DatabaseParser<Person> db = new DatabaseParser<>(Person.class, this.TEST_COLLECTION_DIR);

        while (queue.size() > 0) {
            Person person = queue.poll();
            Optional<Person> result = db.writeFile(person);
            if (result.isPresent()) {
                savedDocuments.add(result.get());
            }
        }

        String[] filesInTest = new File(this.TEST_COLLECTION_DIR).list();
        List<Person> foundDocs = this.personDAO.findAll();
        boolean result = (foundDocs.size() == filesInTest.length) && (filesInTest.length == docs.size());
        this.logTestResult(result, "test_findAll");
    }
}
