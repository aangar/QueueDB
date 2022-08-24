package queuedb.DAO;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;

import queuedb.DatabaseParser;
import queuedb.Objects.Person;

public class PersonDAO extends BaseDAO {
    private final DatabaseParser<Person> dbParser;

    public PersonDAO(String DIR) {
        if (!DIR.isEmpty() || DIR != null) {
            new File(DIR).mkdirs();
            this.DIR_TO_COLLECTION = DIR;
        }
        this.dbParser = new DatabaseParser<Person>(Person.class, this.DIR_TO_COLLECTION);
    }

    /**
     * FindAll method
     * @return a list of all the documents.
     */
    public List<Person> findAll() {
        return this.dbParser.findAll();
    }

    /**
     * save one method
     * @param doc Person to save.
     * @return the saved result.
     */
    public Optional<Person> saveOne(Person doc) {
        if (doc == null) {
            System.err.println("Person cannot be null for saveOne!");
            return Optional.empty();
        }
        return this.dbParser.writeFile(doc);
    }

    /**
     * saves a list of persons.
     * @param docs list of persons to save.
     * @return the saved documents.
     */
    public List<Person> savePersons(List<Person> docs) {
        if (docs == null || docs.isEmpty()) {
            System.err.println("List of Persons cannot be empty for savePersons!");
            return new ArrayList<>();
        }
        Queue<Person> queue = new LinkedList<>(docs);
        List<Person> saved = new ArrayList<>();
        while (queue.size() > 0) {
            Person person = queue.poll();
            if (person.getId() == null || person.getId().isEmpty()) {
                person.generateId();
            }
            Optional<Person> didSave = this.dbParser.writeFile(person);
            if (didSave.isPresent()) {
                saved.add(didSave.get());
            }
        }
        return saved;
    }
}
