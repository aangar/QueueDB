package queuedb;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import queuedb.Objects.DatabaseDocument;

/**
 * A Class to read documents from a Collection,
 * then convert them into the target document.
 * 
 * @author aangar, 2022
 */
public class DatabaseParser<T extends DatabaseDocument> {

    /**
     * Target Class Type. This can be anything <b>that extends DatabaseDocument</b>
     */
    private Class<T> clazz;
    /**
     * The returnable class field. This will be set once <b>buildClazz</b> has been
     * called. <b>Do not modify this directly!</b>
     */
    private T returnable;
    /**
     * A list of Fields found in the target Class. Since any sort of extended fields
     * will be left out.
     */
    private List<String> fieldNames;

    /**
     * Name is self explanatory. Come on now.
     */
    private String DIR_TO_COLLECTION;

    /**
     * Default constructor for the DatabaseParser.
     * 
     * @param clazz the target class.
     */
    public DatabaseParser(Class<T> clazz, String dir) {
        this.clazz = clazz;
        this.DIR_TO_COLLECTION = dir;
    }

    /**
     * Builds a class for the target document to convert to. This must be called
     * before trying to set fields parsed.
     */
    private void buildClazz() {
        try {
            Constructor<?> s[] = this.clazz.getDeclaredConstructors();
            Field[] fields = this.clazz.getDeclaredFields();
            List<String> allowedFields = new ArrayList<>();
            allowedFields.add("id");
            for (Field f : fields) {
                String name = f.toString();
                int end = name.lastIndexOf(".") + 1;
                allowedFields.add(name.substring(end, name.length()));
            }
            this.fieldNames = allowedFields;
            this.returnable = (T) s[0].newInstance();
        } catch (InvocationTargetException nsm) {

        } catch (InstantiationException ie) {

        } catch (IllegalAccessException iae) {

        }

    }

    /**
     * Reads the file from the filename, and returns the parsed object.
     * 
     * @param fileId the ID of the document.
     * @return the parsed file into the target object.
     */
    public Optional<T> readFile(String fileId) {
        T object = null;
        try {
            FileInputStream FIS = new FileInputStream(new File(this.DIR_TO_COLLECTION + fileId));
            ObjectInputStream OIS = new ObjectInputStream(FIS);
            object = (T) OIS.readObject();
            OIS.close();
        } catch (FileNotFoundException FNFE) {
            System.out.println(FNFE);
        } catch (IOException IOE) {
            System.out.println(IOE);
        } catch (ClassNotFoundException CNFE) {
            System.out.println(CNFE);
        }
        return Optional.ofNullable(object);
    }

    /**
     * Writes a file to the Database collection.
     * 
     * @param file the Object to write.
     * @return an Optional of the result.
     */
    public Optional<T> writeFile(T file) {
        if (file.getId() == null || file.getId().isEmpty()) {
            file.generateId();
        }
        try {
            String id = file.getId();
            FileOutputStream FOS = new FileOutputStream(new File(this.DIR_TO_COLLECTION + id));
            ObjectOutputStream OOS = new ObjectOutputStream(FOS);
            OOS.writeObject(file);
            OOS.close();
        } catch (IOException IOE) {
            System.out.println(IOE);
            return Optional.empty();
        }
        return Optional.of(file);
    }

    /**
     * Finds all documents in the collection.
     * 
     * @return found documents.
     */
    public List<T> findAll() {
        List<String> documents = List.of(new File(this.DIR_TO_COLLECTION).list());
        if (documents.isEmpty()) {
            System.out.println("No documents in collection.");
            return new ArrayList<>();
        }
        return documents.stream().map(doc -> {
            Optional<T> read = this.readFile(doc);
            return read.get();
        })
        .filter(x -> x != null)
        .collect(Collectors.toList());
    }
}
