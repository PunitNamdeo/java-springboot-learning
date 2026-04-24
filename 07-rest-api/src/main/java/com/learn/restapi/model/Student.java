package com.learn.restapi.model;

/**
 * Student — the domain model (internal representation).
 * In later modules, this will become a JPA @Entity.
 * For now it's a plain Java class used as in-memory data.
 */
public class Student {

    private Long id;
    private String name;
    private String email;
    private int age;
    private String major;

    // Constructors
    public Student() {}

    public Student(Long id, String name, String email, int age, String major) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
        this.major = major;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getMajor() { return major; }
    public void setMajor(String major) { this.major = major; }

    @Override
    public String toString() {
        return "Student{id=" + id + ", name='" + name + "', email='" + email + "'}";
    }
}
