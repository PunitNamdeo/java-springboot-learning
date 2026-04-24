/**
 * ClassAndObjectExample.java
 * Demonstrates class creation, constructors, and object usage
 */
public class ClassAndObjectExample {

    public static void main(String[] args) {
        // Creating objects using constructors
        Student s1 = new Student("Punit", 25, "Computer Science");
        Student s2 = new Student("Rahul", 22, "Mathematics");
        Student s3 = new Student("Priya");  // uses single-arg constructor

        s1.introduce();
        s2.introduce();
        s3.introduce();

        s1.study("Spring Boot");

        System.out.println("\nTotal students: " + Student.getTotalStudents());
    }
}

class Student {
    // Instance fields
    private String name;
    private int age;
    private String major;

    // Static field — shared across all objects
    private static int totalStudents = 0;

    // Constructor 1: full
    public Student(String name, int age, String major) {
        this.name = name;
        this.age = age;
        this.major = major;
        totalStudents++;
    }

    // Constructor 2: name only (chaining)
    public Student(String name) {
        this(name, 18, "Undeclared");
    }

    public void introduce() {
        System.out.println("Hi, I'm " + name + ", age " + age + ", studying " + major);
    }

    public void study(String topic) {
        System.out.println(name + " is studying " + topic + ".");
    }

    public static int getTotalStudents() { return totalStudents; }

    @Override
    public String toString() {
        return "Student{name='" + name + "', age=" + age + ", major='" + major + "'}";
    }
}
