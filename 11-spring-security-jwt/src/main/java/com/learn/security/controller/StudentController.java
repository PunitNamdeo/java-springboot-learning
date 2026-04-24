package com.learn.security.controller;

import com.learn.security.model.Student;
import com.learn.security.repository.StudentRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 🔒 ALL endpoints here require a valid JWT token.
 * Without "Authorization: Bearer <token>" header → 401 Unauthorized
 */
@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentRepository studentRepository;

    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // GET /api/students  🔒
    @GetMapping
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    // GET /api/students/{id}  🔒
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        return studentRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/students  🔒
    @PostMapping
    public ResponseEntity<Student> createStudent(@Valid @RequestBody Student student) {
        return ResponseEntity.status(HttpStatus.CREATED).body(studentRepository.save(student));
    }

    // DELETE /api/students/{id}  🔒
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        if (!studentRepository.existsById(id)) return ResponseEntity.notFound().build();
        studentRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // GET /api/students/me  🔒  — returns the currently logged-in user
    @GetMapping("/me")
    public ResponseEntity<String> whoAmI(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok("You are logged in as: " + userDetails.getUsername()
                + " | Roles: " + userDetails.getAuthorities());
    }
}
