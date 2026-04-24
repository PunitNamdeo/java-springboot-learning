package com.learn.exception.exceptions;

/**
 * Thrown when a Student is not found by ID.
 * Extends RuntimeException — no forced try/catch needed.
 */
public class StudentNotFoundException extends RuntimeException {

    private final Long studentId;

    public StudentNotFoundException(Long studentId) {
        super("Student not found with ID: " + studentId);
        this.studentId = studentId;
    }

    public Long getStudentId() {
        return studentId;
    }
}
