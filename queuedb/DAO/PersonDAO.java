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

public class PersonDAO extends BaseDAO{
    private final DatabaseParser<Person> dbParser;

    public PersonDAO(String DIR) {
        if (!DIR.isEmpty() || DIR != null) {
            new File(DIR).mkdirs();
            this.DIR_TO_COLLECTION = DIR;
        }
        this.dbParser = new DatabaseParser<Person>(Person.class, this.DIR_TO_COLLECTION);
    }

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
