package com.learn.restapi.dto;

/**
 * StudentResponseDTO — what the SERVER returns to the CLIENT.
 *
 * Only expose fields that the client needs.
 * Never expose sensitive fields like passwords.
 */
public class StudentResponseDTO {

    private Long id;
    private String name;
    private String email;
    private int age;
    private String major;

    // Constructors
    public StudentResponseDTO() {}

    public StudentResponseDTO(Long id, String name, String email, int age, String major) {
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
}
