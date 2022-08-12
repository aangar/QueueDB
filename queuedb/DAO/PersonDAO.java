package queuedb.DAO;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import queuedb.DatabaseParser;
import queuedb.Objects.Person;

/**
 * DAO for interaction with "Person" documents.
 * @author aangar, 2022.
 */
public class PersonDAO extends BaseDAO {
    /**The parser to write to the database. */
    private final DatabaseParser<Person> dbParser;

    /**
     * Default constructor.
     * @param DIR the directory to the collection as a string.
     */
    public PersonDAO(String DIR) {
        if (!DIR.isEmpty() || DIR != null) {
            new File(DIR).mkdirs();
            this.DIR_TO_COLLECTION = DIR;
        }
        this.dbParser = new DatabaseParser<Person>(Person.class, this.DIR_TO_COLLECTION);
    }

    /**
     * Finds all the Person documents in the collection.
     * @return a list of the found documents.
     */
    public List<Person> findAll() {
        return this.dbParser.findAll();
    }

    /**
     * Saves one person document to the collection.
     * @param doc the document to save.
     * @return <code>true</code> if it was saved, <code>false</code> otherwise.
     */
    public boolean saveOne(Person doc) {
        if (doc.getId() == null || doc.getId().isEmpty()) {
            doc.generateId();
        }
        try {
            FileWriter writer = new FileWriter(
                    this.DIR_TO_COLLECTION + "Person_" + doc.getName() + "_" + doc.getId() + ".json");
            writer.write("{\n");
            writer.write(String.format("   \"id\": \"%s\",\n", doc.getId()));
            writer.write(String.format("   \"name\": \"%s\",\n", doc.getName()));
            writer.write(String.format("   \"age\": \"%s\"\n", doc.getAge()));
            writer.write("}");
            writer.close();
        } catch (IOException e) {
            System.out.println("damn there was an error");
            System.out.println(e);
            return false;
        }
        return true;
    }

    /**
     * Saves multiple person documents.
     * @param docs the documents to save.
     * @return a list of the persons saved.
     */
    public List<Person> savePersons(List<Person> docs) {
        Queue<Person> queue = new LinkedList<>(docs);
        List<Person> saved = new ArrayList<>();
        while (queue.size() > 0) {
            Person person = queue.poll();
            if (person.getId() == null || person.getId().isEmpty()) {
                person.generateId();
            }
            try {
                FileWriter writer = new FileWriter(
                        this.DIR_TO_COLLECTION + "Person_" + person.getName() + "_" + person.getId() + ".json");
                writer.write("{\n");
                writer.write(String.format("   \"id\": \"%s\",\n", person.getId()));
                writer.write(String.format("   \"name\": \"%s\",\n", person.getName()));
                writer.write(String.format("   \"age\": \"%s\"\n", person.getAge()));
                writer.write("}");
                writer.close();
                saved.add(person);
            } catch (IOException e) {
                System.out.println("damn there was an error");
                System.out.println(e);
            }
        }
        return saved;
    }
}
