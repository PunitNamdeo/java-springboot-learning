package com.learn.capstone.dto;

/**
 * A lightweight DTO — only exposes name, course, grade.
 * Demonstrates the DTO pattern: never expose raw entities to the API.
 */
public class StudentSummary {
    private Long id;
    private String name;
    private String course;
    private String grade;

    public StudentSummary(Long id, String name, String course, String grade) {
        this.id     = id;
        this.name   = name;
        this.course = course;
        this.grade  = grade;
    }

    public Long getId()       { return id; }
    public String getName()   { return name; }
    public String getCourse() { return course; }
    public String getGrade()  { return grade; }
}
