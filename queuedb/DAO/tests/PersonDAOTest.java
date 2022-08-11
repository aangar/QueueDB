package queuedb.DAO.tests;

import java.util.List;
import java.util.stream.Collectors;
import queuedb.DAO.PersonDAO;
import queuedb.Objects.Person;

public class PersonDAOTest extends BaseTest{
    public final PersonDAO personDAO;

    public PersonDAOTest(String dir) {
        this.personDAO = new PersonDAO(dir);
        this.TEST_COLLECTION_DIR = dir;
        this.TestFileName = "PersonDAOTest";
    }

    public void runTests() {
        this.clearTestCollection();
        test_Save();
        this.removeTestCollection();
    }

    private void test_Save() {
        List<Person> docs = List.of("Riley", "Alpine", "Tetca", "Doyin", "Trilek")
            .stream()
            .map(Person::generatePersonFromName)
            .collect(Collectors.toList());
        this.logTestResult(this.personDAO.savePersons(docs).size() == docs.size(), "test_Save");
    }
}
