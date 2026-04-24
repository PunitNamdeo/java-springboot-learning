package com.learn.restapi.controller;

import com.learn.restapi.dto.StudentRequestDTO;
import com.learn.restapi.dto.StudentResponseDTO;
import com.learn.restapi.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * StudentController — WEB LAYER (Controller)
 *
 * Responsibilities:
 * - Handle HTTP requests/responses ONLY
 * - Call service for business logic
 * - Return correct HTTP status codes
 * - Should be THIN (no business logic here!)
 *
 * Base URL: /api/students
 *
 * Endpoints:
 *   GET    /api/students                   → get all
 *   GET    /api/students/{id}              → get by ID
 *   GET    /api/students/search?name=xyz   → search by name
 *   POST   /api/students                   → create
 *   PUT    /api/students/{id}              → update
 *   DELETE /api/students/{id}              → delete
 */
@RestController
@RequestMapping("/api/students")
public class StudentController {

    // Constructor injection (recommended over @Autowired on field)
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    /**
     * GET /api/students
     * Returns all students.
     * Status: 200 OK
     */
    @GetMapping
    public ResponseEntity<List<StudentResponseDTO>> getAllStudents() {
        List<StudentResponseDTO> students = studentService.getAllStudents();
        return ResponseEntity.ok(students);  // 200 OK
    }

    /**
     * GET /api/students/{id}
     * Returns a single student by ID.
     * Status: 200 OK or 404 Not Found
     */
    @GetMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> getStudentById(@PathVariable Long id) {
        return studentService.getStudentById(id)
                .map(ResponseEntity::ok)                      // 200 OK if found
                .orElse(ResponseEntity.notFound().build());   // 404 if not found
    }

    /**
     * GET /api/students/search?name=Punit
     * Search students by name (case-insensitive partial match).
     * Status: 200 OK
     */
    @GetMapping("/search")
    public ResponseEntity<List<StudentResponseDTO>> searchByName(
            @RequestParam String name) {
        return ResponseEntity.ok(studentService.searchByName(name));
    }

    /**
     * POST /api/students
     * Create a new student.
     * Status: 201 Created or 409 Conflict (duplicate email)
     *
     * Request Body:
     * {
     *   "name": "Punit Namdeo",
     *   "email": "punit@example.com",
     *   "age": 25,
     *   "major": "Computer Science"
     * }
     */
    @PostMapping
    public ResponseEntity<?> createStudent(@RequestBody StudentRequestDTO dto) {
        try {
            StudentResponseDTO created = studentService.createStudent(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);  // 201 Created
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());  // 409 Conflict
        }
    }

    /**
     * PUT /api/students/{id}
     * Update an existing student (full update).
     * Status: 200 OK or 404 Not Found
     */
    @PutMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> updateStudent(
            @PathVariable Long id,
            @RequestBody StudentRequestDTO dto) {
        return studentService.updateStudent(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * DELETE /api/students/{id}
     * Delete a student by ID.
     * Status: 204 No Content or 404 Not Found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        boolean deleted = studentService.deleteStudent(id);
        if (deleted) {
            return ResponseEntity.noContent().build();    // 204 No Content
        }
        return ResponseEntity.notFound().build();         // 404 Not Found
    }
}
