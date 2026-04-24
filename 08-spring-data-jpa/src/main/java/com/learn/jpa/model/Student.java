package com.learn.jpa.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * Student @Entity — maps this Java class to the "students" DB table.
 *
 * Hibernate will auto-generate the table based on these annotations.
 */
@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // AUTO_INCREMENT
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column
    private int age;

    @Column(length = 100)
    private String major;

    @CreationTimestamp                      // Hibernate sets on INSERT
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp                        // Hibernate sets on UPDATE
    private LocalDateTime updatedAt;

    // ── Constructors ──────────────────────────────────
    public Student() {}

    public Student(String name, String email, int age, String major) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.major = major;
    }

    // ── Getters & Setters ─────────────────────────────
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
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    @Override
    public String toString() {
        return "Student{id=" + id + ", name='" + name + "', email='" + email + "', major='" + major + "'}";
    }
}
