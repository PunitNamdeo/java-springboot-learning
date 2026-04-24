package com.learn.validation.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

/**
 * ============================================================
 *  BEAN VALIDATION ANNOTATIONS
 * ============================================================
 *
 *  These annotations live on the model/DTO fields.
 *  When @Valid is placed on the controller method parameter,
 *  Spring automatically runs validation BEFORE the method body
 *  executes. If any constraint fails, it throws
 *  MethodArgumentNotValidException which our GlobalExceptionHandler
 *  catches and converts to a clean 400 JSON response.
 * ============================================================
 */
@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // @NotBlank  = not null AND not empty AND not only whitespace
    // @Size      = enforce min/max character length
    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    // @NotBlank = field must be present and non-empty
    // @Email    = must match a valid email format
    @NotBlank(message = "Email is required")
    @Email(message = "Email must be a valid email address")
    private String email;

    // @NotBlank = course must be provided
    // @Size     = reasonable length limit
    @NotBlank(message = "Course is required")
    @Size(min = 2, max = 50, message = "Course must be between 2 and 50 characters")
    private String course;

    // @Min / @Max = numeric range validation
    @Min(value = 18, message = "Age must be at least 18")
    @Max(value = 100, message = "Age must be at most 100")
    private int age;

    public Student() {}

    public Student(String name, String email, String course, int age) {
        this.name   = name;
        this.email  = email;
        this.course = course;
        this.age    = age;
    }

    // Getters & Setters
    public Long getId()              { return id; }
    public void setId(Long id)       { this.id = id; }

    public String getName()              { return name; }
    public void setName(String name)     { this.name = name; }

    public String getEmail()             { return email; }
    public void setEmail(String email)   { this.email = email; }

    public String getCourse()            { return course; }
    public void setCourse(String course) { this.course = course; }

    public int getAge()              { return age; }
    public void setAge(int age)      { this.age = age; }
}
