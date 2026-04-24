package com.learn.validation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Structured JSON error response.
 * 'fieldErrors' is only included when validation fails (400).
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {

    private int status;
    private String error;
    private String message;
    private String path;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;

    // Only populated for validation errors (400)
    private List<FieldError> fieldErrors;

    public ErrorResponse(int status, String error, String message, String path) {
        this.status    = status;
        this.error     = error;
        this.message   = message;
        this.path      = path;
        this.timestamp = LocalDateTime.now();
    }

    // Nested class — one entry per invalid field
    public static class FieldError {
        private String field;
        private Object rejectedValue;
        private String message;

        public FieldError(String field, Object rejectedValue, String message) {
            this.field         = field;
            this.rejectedValue = rejectedValue;
            this.message       = message;
        }

        public String getField()          { return field; }
        public Object getRejectedValue()  { return rejectedValue; }
        public String getMessage()        { return message; }
    }

    // Getters
    public int getStatus()                      { return status; }
    public String getError()                    { return error; }
    public String getMessage()                  { return message; }
    public String getPath()                     { return path; }
    public LocalDateTime getTimestamp()         { return timestamp; }
    public List<FieldError> getFieldErrors()    { return fieldErrors; }
    public void setFieldErrors(List<FieldError> fieldErrors) { this.fieldErrors = fieldErrors; }
}
