package com.learn.jpa.dto;

/**
 * StudentRequestDTO — what the client sends to create/update a student.
 */
public class StudentRequestDTO {
    private String name;
    private String email;
    private int age;
    private String major;

    public StudentRequestDTO() {}

    public StudentRequestDTO(String name, String email, int age, String major) {
        this.name = name; this.email = email; this.age = age; this.major = major;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    public String getMajor() { return major; }
    public void setMajor(String major) { this.major = major; }
}
