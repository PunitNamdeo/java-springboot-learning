package com.learn.capstone.controller;

import com.learn.capstone.dto.StudentSummary;
import com.learn.capstone.model.Student;
import com.learn.capstone.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ============================================================
 *  CAPSTONE STUDENT CONTROLLER
 * ============================================================
 *  Combines ALL modules:
 *  - REST API (Module 07)
 *  - Pagination (Module 08)
 *  - @Valid validation (Module 10)
 *  - JWT Security — ADMIN write, USER read (Module 11)
 * ============================================================
 */
@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // GET /api/students?page=0&size=5&sort=name  — paginated
    @GetMapping
    public Page<Student> getAllStudents(
            @PageableDefault(size = 5, sort = "name") Pageable pageable) {
        return studentService.getAllStudents(pageable);
    }

    // GET /api/students/summary  — lightweight DTO view
    @GetMapping("/summary")
    public List<StudentSummary> getSummaries() {
        return studentService.getSummaries();
    }

    // GET /api/students/search?keyword=alice
    @GetMapping("/search")
    public List<Student> search(@RequestParam String keyword) {
        return studentService.search(keyword);
    }

    // GET /api/students/course/{course}?page=0&size=3
    @GetMapping("/course/{course}")
    public Page<Student> getByCourse(
            @PathVariable String course,
            @PageableDefault(size = 5) Pageable pageable) {
        return studentService.getByCourse(course, pageable);
    }

    // GET /api/students/grade/{grade}
    @GetMapping("/grade/{grade}")
    public List<Student> getByGrade(@PathVariable String grade) {
        return studentService.getByGrade(grade);
    }

    // GET /api/students/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Student> getById(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.getStudentById(id));
    }

    // POST /api/students  — ADMIN only
    @PostMapping
    public ResponseEntity<Student> create(@Valid @RequestBody Student student) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(studentService.createStudent(student));
    }

    // PUT /api/students/{id}  — ADMIN only
    @PutMapping("/{id}")
    public ResponseEntity<Student> update(
            @PathVariable Long id,
            @Valid @RequestBody Student student) {
        return ResponseEntity.ok(studentService.updateStudent(id, student));
    }

    // DELETE /api/students/{id}  — ADMIN only
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }

    // GET /api/students/me — who am I?
    @GetMapping("/me")
    public ResponseEntity<String> whoAmI(
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok("Logged in as: " + userDetails.getUsername()
                + " | Roles: " + userDetails.getAuthorities());
    }
}
