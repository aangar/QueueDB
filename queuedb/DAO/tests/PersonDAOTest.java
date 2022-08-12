package queuedb.DAO.tests;

import java.util.List;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Collectors;
import queuedb.DAO.PersonDAO;
import queuedb.Objects.Person;

public class PersonDAOTest extends BaseTest {
    public final PersonDAO personDAO;

    public PersonDAOTest(String dir) {
        this.personDAO = new PersonDAO(dir);
        this.TEST_COLLECTION_DIR = dir;
        this.TestFileName = "PersonDAOTest";
    }

    public void test_saveOne() {
        Person person = new Person("GRAPHITE_TESTDOC", 22);
        this.logTestResult(this.personDAO.saveOne(person), "test_saveOne");
    }

    /**
     * ensures saving a null document will fail.
     */
    public void test_saveOne_null() {
        this.logTestResult(!this.personDAO.saveOne(null), "test_saveOne_null");
    }

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
