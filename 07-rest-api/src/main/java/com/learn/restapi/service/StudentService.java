package com.learn.restapi.service;

import com.learn.restapi.dto.StudentRequestDTO;
import com.learn.restapi.dto.StudentResponseDTO;
import com.learn.restapi.model.Student;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * StudentService — BUSINESS LOGIC LAYER
 *
 * Responsibilities:
 * - All business logic lives here (NOT in controller)
 * - Maps between DTOs and domain model
 * - Uses in-memory List as data store (Module 08 adds a real DB)
 *
 * @Service tells Spring to manage this as a bean
 */
@Service
public class StudentService {

    // In-memory storage (replaced by JPA repository in Module 08)
    private final Map<Long, Student> studentStore = new HashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    // Pre-load some sample data
    public StudentService() {
        createStudent(new StudentRequestDTO("Punit Namdeo", "punit@example.com", 25, "Computer Science"));
        createStudent(new StudentRequestDTO("Rahul Sharma", "rahul@example.com", 22, "Mathematics"));
        createStudent(new StudentRequestDTO("Priya Patel", "priya@example.com", 23, "Physics"));
    }

    // ── GET ALL ────────────────────────────────────────────────────────
    public List<StudentResponseDTO> getAllStudents() {
        return studentStore.values().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    // ── GET BY ID ──────────────────────────────────────────────────────
    public Optional<StudentResponseDTO> getStudentById(Long id) {
        return Optional.ofNullable(studentStore.get(id))
                .map(this::toResponseDTO);
    }

    // ── SEARCH BY NAME ─────────────────────────────────────────────────
    public List<StudentResponseDTO> searchByName(String name) {
        return studentStore.values().stream()
                .filter(s -> s.getName().toLowerCase().contains(name.toLowerCase()))
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    // ── CREATE ─────────────────────────────────────────────────────────
    public StudentResponseDTO createStudent(StudentRequestDTO dto) {
        // Business rule: check for duplicate email
        boolean emailExists = studentStore.values().stream()
                .anyMatch(s -> s.getEmail().equalsIgnoreCase(dto.getEmail()));
        if (emailExists) {
            throw new IllegalArgumentException("Email already exists: " + dto.getEmail());
        }

        Student student = toEntity(dto);
        student.setId(idCounter.getAndIncrement());
        studentStore.put(student.getId(), student);
        return toResponseDTO(student);
    }

    // ── UPDATE ─────────────────────────────────────────────────────────
    public Optional<StudentResponseDTO> updateStudent(Long id, StudentRequestDTO dto) {
        Student existing = studentStore.get(id);
        if (existing == null) return Optional.empty();

        existing.setName(dto.getName());
        existing.setEmail(dto.getEmail());
        existing.setAge(dto.getAge());
        existing.setMajor(dto.getMajor());

        studentStore.put(id, existing);
        return Optional.of(toResponseDTO(existing));
    }

    // ── DELETE ─────────────────────────────────────────────────────────
    public boolean deleteStudent(Long id) {
        if (!studentStore.containsKey(id)) return false;
        studentStore.remove(id);
        return true;
    }

    // ── MAPPING HELPERS ────────────────────────────────────────────────

    // RequestDTO → Entity
    private Student toEntity(StudentRequestDTO dto) {
        Student student = new Student();
        student.setName(dto.getName());
        student.setEmail(dto.getEmail());
        student.setAge(dto.getAge());
        student.setMajor(dto.getMajor());
        return student;
    }

    // Entity → ResponseDTO
    private StudentResponseDTO toResponseDTO(Student student) {
        return new StudentResponseDTO(
                student.getId(),
                student.getName(),
                student.getEmail(),
                student.getAge(),
                student.getMajor()
        );
    }
}
