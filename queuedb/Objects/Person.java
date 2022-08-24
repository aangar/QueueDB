package queuedb.Objects;

/**
 * The Person Class.
 */
public class Person extends DatabaseDocument {
    private String name;
    private int age;

    /**
     * Default Constructor.
     */
    public Person() {
        generateId();
    }

    /**{@inheritDoc}} */
    public Person(String name, int age) {
        generateId();
        this.name = name;
        this.age = age;
    }
    /**
     * setter
     * @param name name
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * setter
     * @param age age
     */
    public void setAge(int age) {
        this.age = age;
    }
    /**
     * getter
     * @return name
     */
    public String getName() {
        return this.name;
    }
    /**
     * getter
     * @return age
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

    public static Person generatePersonFromName(String name) {
        return new Person(name, (int) Math.floor(Math.random() * 80) + 3);
    }
}
