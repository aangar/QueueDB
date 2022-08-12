package queuedb.Objects;

/**
 * Person class.
 */
public class Person extends DatabaseDocument {
    /**name of the document. */
    private String name;
    /** age of the document. */
    private int age;

    /**
     * Empty constructor, generates ID.
     */
    public Person() {
        generateId();
    }

    /**
     *  Constructor with the name and age as params.
     * @param name String, name.
     * @param age int, age.
     */
    public Person(String name, int age) {
        generateId();
        this.name = name;
        this.age = age;
    }

    /**
     * Sets the name.
     * @param name String, name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the age.
     * @param age int, age.
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Gets the name.
     * @return String, the documents' name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets the age.
     * @return int, the documents' age.
     */
    public int getAge() {
        return this.age;
    }

    /**{@inheritDoc}} */
    public void setParsedProperty(String property, String val) {
        switch (property) {
            case "id": {
                this.setId(val);
            }
            case "name":
                this.setName(val);
                break;
            case "age":
                this.setAge(Integer.parseInt(val));
                break;
            default:
                System.err.println("Property name not recognized for Person: " + property);
        }
    }

    /**
     * Generates a person when passed a name.
     * @param name String, name.
     * @return Person, the generated Person object.
     */
    public static Person generatePersonFromName(String name) {
        return new Person(name, (int) Math.floor(Math.random() * 80) + 3);
    }
}
