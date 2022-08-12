package queuedb.DAO.tests;

import java.util.List;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Collectors;
import queuedb.DAO.PersonDAO;
import queuedb.Objects.Person;

/**
 * PersonDAOTest class.
 */
public class PersonDAOTest extends BaseTest {
    /**The testing PersonDAO */
    public final PersonDAO personDAO;

    /**
     * Default constructor.
     * @param dir the path to the testing directory.
     */
    public PersonDAOTest(String dir) {
        this.personDAO = new PersonDAO(dir);
        this.TEST_COLLECTION_DIR = dir;
        this.TestFileName = "PersonDAOTest";
    }

    /**
     * tests the saveOne method.
     */
    public void test_saveOne() {
        Person person = new Person("GRAPHITE_TESTDOC", 22);
        this.logTestResult(this.personDAO.saveOne(person), "test_saveOne");
    }

    /**
     * tests the savePersons method.
     */
    public void test_SaveMulti() {
        List<Person> docs = List.of("Riley", "Alpine", "Tetca", "Doyin", "Trilek")
                .stream()
                .map(Person::generatePersonFromName)
                .collect(Collectors.toList());
        this.logTestResult(this.personDAO.savePersons(docs).size() == docs.size(), "test_SaveMulti");
    }

    /**
     * tests the findAll method.
     */
    public void test_findAll() {
        List<Person> docs = List.of("Riley", "Alpine", "Tetca", "Doyin", "Trilek")
                .stream()
                .map(Person::generatePersonFromName)
                .collect(Collectors.toList());
        Queue<Person> queue = new LinkedList<Person>(docs);

        while (queue.size() > 0) {
            Person person = queue.poll();
            if (person.getId() == null || person.getId().isEmpty()) {
                person.generateId();
            }
            try {
                FileWriter writer = new FileWriter(
                        this.TEST_COLLECTION_DIR + "Person_" + person.getName() + "_" + person.getId() + ".json");
                writer.write("{\n");
                writer.write(String.format("   \"id\": \"%s\",\n", person.getId()));
                writer.write(String.format("   \"name\": \"%s\",\n", person.getName()));
                writer.write(String.format("   \"age\": \"%s\"\n", person.getAge()));
                writer.write("}");
                writer.close();
            } catch (IOException e) {
                System.out.println("damn there was an error");
                System.out.println(e);
            }
        }

        String[] filesInTest = new File(this.TEST_COLLECTION_DIR).list();
        List<Person> foundDocs = this.personDAO.findAll();
        boolean result = (foundDocs.size() == filesInTest.length) && (filesInTest.length == docs.size());
        this.logTestResult(result, "test_findAll");
    }
}
