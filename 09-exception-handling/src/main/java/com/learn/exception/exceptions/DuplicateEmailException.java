package com.learn.exception.exceptions;

/**
 * Thrown when a Student with the same email already exists.
 */
public class DuplicateEmailException extends RuntimeException {

    private final String email;

    public DuplicateEmailException(String email) {
        super("A student with email '" + email + "' already exists.");
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
