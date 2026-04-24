package com.learn.capstone.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

/**
 * Student JPA Entity — combines Module 08 (JPA) + Module 10 (Validation)
 */
@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Must be a valid email address")
    @Column(unique = true)
    private String email;

    @NotBlank(message = "Course is required")
    @Size(min = 2, max = 50, message = "Course must be between 2 and 50 characters")
    private String course;

    @Min(value = 18, message = "Age must be at least 18")
    @Max(value = 100, message = "Age must be at most 100")
    private int age;

    @NotBlank(message = "Grade is required")
    @Pattern(regexp = "[A-F]", message = "Grade must be A, B, C, D, E or F")
    private String grade;

    public Student() {}

    public Student(String name, String email, String course, int age, String grade) {
        this.name   = name;
        this.email  = email;
        this.course = course;
        this.age    = age;
        this.grade  = grade;
    }

    public Long getId()              { return id; }
    public void setId(Long id)       { this.id = id; }
    public String getName()          { return name; }
    public void setName(String n)    { this.name = n; }
    public String getEmail()         { return email; }
    public void setEmail(String e)   { this.email = e; }
    public String getCourse()        { return course; }
    public void setCourse(String c)  { this.course = c; }
    public int getAge()              { return age; }
    public void setAge(int a)        { this.age = a; }
    public String getGrade()         { return grade; }
    public void setGrade(String g)   { this.grade = g; }
}
