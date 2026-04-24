package com.learn.jpa.controller;

import com.learn.jpa.dto.StudentRequestDTO;
import com.learn.jpa.dto.StudentResponseDTO;
import com.learn.jpa.service.StudentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * StudentController — WEB LAYER
 *
 * Endpoints:
 *   GET    /api/students                        → all students (paginated)
 *   GET    /api/students?page=0&size=5&sort=name → with pagination
 *   GET    /api/students/{id}                   → by ID
 *   GET    /api/students/search?name=Punit      → search by name
 *   GET    /api/students/major/{major}          → by major
 *   POST   /api/students                        → create
 *   PUT    /api/students/{id}                   → update
 *   DELETE /api/students/{id}                   → delete
 */
@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    /** GET /api/students?page=0&size=10&sort=name */
    @GetMapping
    public ResponseEntity<Page<StudentResponseDTO>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
        return ResponseEntity.ok(studentService.getAllStudents(pageable));
    }

    /** GET /api/students/{id} */
    @GetMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> getById(@PathVariable Long id) {
        return studentService.getStudentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /** GET /api/students/search?name=Punit */
    @GetMapping("/search")
    public ResponseEntity<List<StudentResponseDTO>> search(@RequestParam String name) {
        return ResponseEntity.ok(studentService.searchByName(name));
    }

    /** GET /api/students/major/Computer Science */
    @GetMapping("/major/{major}")
    public ResponseEntity<List<StudentResponseDTO>> getByMajor(@PathVariable String major) {
        return ResponseEntity.ok(studentService.getByMajor(major));
    }

    /** POST /api/students */
    @PostMapping
    public ResponseEntity<?> create(@RequestBody StudentRequestDTO dto) {
        try {
            StudentResponseDTO created = studentService.createStudent(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    /** PUT /api/students/{id} */
    @PutMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> update(
            @PathVariable Long id, @RequestBody StudentRequestDTO dto) {
        return studentService.updateStudent(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /** DELETE /api/students/{id} */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return studentService.deleteStudent(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
