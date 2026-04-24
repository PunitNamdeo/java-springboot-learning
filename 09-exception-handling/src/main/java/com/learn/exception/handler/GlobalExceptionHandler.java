package com.learn.exception.handler;

import com.learn.exception.dto.ErrorResponse;
import com.learn.exception.exceptions.DuplicateEmailException;
import com.learn.exception.exceptions.StudentNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * ============================================================
 *  GLOBAL EXCEPTION HANDLER  — @RestControllerAdvice
 * ============================================================
 *
 *  @RestControllerAdvice = @ControllerAdvice + @ResponseBody
 *  It intercepts exceptions thrown from ANY controller in the
 *  application and converts them into clean JSON responses.
 *
 *  Without this: Spring returns its ugly white-label HTML page.
 *  With this:    Your client gets a structured JSON error body.
 * ============================================================
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    // ----------------------------------------------------------------
    // 1. Student Not Found  →  404 NOT FOUND
    // ----------------------------------------------------------------
    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleStudentNotFound(
            StudentNotFoundException ex,
            HttpServletRequest request) {

        ErrorResponse body = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "Not Found",
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    // ----------------------------------------------------------------
    // 2. Duplicate Email  →  409 CONFLICT
    // ----------------------------------------------------------------
    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateEmail(
            DuplicateEmailException ex,
            HttpServletRequest request) {

        ErrorResponse body = new ErrorResponse(
                HttpStatus.CONFLICT.value(),
                "Conflict",
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
    }

    // ----------------------------------------------------------------
    // 3. Bad Path Variable type (e.g. /students/abc instead of /students/1)
    //    →  400 BAD REQUEST
    // ----------------------------------------------------------------
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatch(
            MethodArgumentTypeMismatchException ex,
            HttpServletRequest request) {

        String message = String.format(
                "Parameter '%s' must be of type %s",
                ex.getName(),
                ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "unknown"
        );

        ErrorResponse body = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                message,
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    // ----------------------------------------------------------------
    // 4. Catch-all fallback  →  500 INTERNAL SERVER ERROR
    //    (Handles any unexpected exception we haven't planned for)
    // ----------------------------------------------------------------
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(
            Exception ex,
            HttpServletRequest request) {

        ErrorResponse body = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                "An unexpected error occurred. Please try again later.",
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
}
