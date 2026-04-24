package com.learn.jpa.dto;

import java.time.LocalDateTime;

/**
 * StudentResponseDTO — what the server returns to the client.
 * Includes id and timestamps. Never includes sensitive internal fields.
 */
public class StudentResponseDTO {
    private Long id;
    private String name;
    private String email;
    private int age;
    private String major;
    private LocalDateTime createdAt;

    public StudentResponseDTO() {}

    public StudentResponseDTO(Long id, String name, String email, int age, String major, LocalDateTime createdAt) {
        this.id = id; this.name = name; this.email = email;
        this.age = age; this.major = major; this.createdAt = createdAt;
    }

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
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
