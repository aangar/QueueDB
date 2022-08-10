package queuedb.Objects;

public class Person extends DatabaseDocument {
    private String name;
    private int age;

    public Person() {
        generateId();
    }

    public Person(String name, int age) {
        generateId();
        this.name = name;
        this.age = age;
    }

    public void setName(String name){ 
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return this.name;
    }

    public int getAge() {
        return this.age;
    }

    public void setParsedProperty(String property, String val) {
        switch (property) {
            case "name": this.setName(val);
                break;
            case "age": this.setAge(Integer.parseInt(val));
                break;
            default: System.err.println("Property name not recognized for Person.");
        }
    }

    public static Person generatePersonFromName(String name) {
        return new Person(name, (int) Math.floor(Math.random() * 80) + 3);
    }
}
