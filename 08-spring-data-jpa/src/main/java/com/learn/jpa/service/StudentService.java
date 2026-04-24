package com.learn.jpa.service;

import com.learn.jpa.dto.StudentRequestDTO;
import com.learn.jpa.dto.StudentResponseDTO;
import com.learn.jpa.model.Student;
import com.learn.jpa.repository.StudentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * StudentService — BUSINESS LOGIC LAYER
 *
 * - Orchestrates repository calls
 * - Maps between DTOs and entities
 * - Applies business rules (e.g. duplicate email check)
 * - Uses @Transactional to manage DB transactions
 */
@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // ── GET ALL (with pagination) ─────────────────────────────────────
    @Transactional(readOnly = true)
    public Page<StudentResponseDTO> getAllStudents(Pageable pageable) {
        return studentRepository.findAll(pageable).map(this::toDTO);
    }

    // ── GET BY ID ─────────────────────────────────────────────────────
    @Transactional(readOnly = true)
    public Optional<StudentResponseDTO> getStudentById(Long id) {
        return studentRepository.findById(id).map(this::toDTO);
    }

    // ── SEARCH BY NAME ────────────────────────────────────────────────
    @Transactional(readOnly = true)
    public List<StudentResponseDTO> searchByName(String name) {
        return studentRepository.findByNameContainingIgnoreCase(name)
                .stream().map(this::toDTO).toList();
    }

    // ── GET BY MAJOR ──────────────────────────────────────────────────
    @Transactional(readOnly = true)
    public List<StudentResponseDTO> getByMajor(String major) {
        return studentRepository.findByMajor(major)
                .stream().map(this::toDTO).toList();
    }

    // ── CREATE ────────────────────────────────────────────────────────
    @Transactional
    public StudentResponseDTO createStudent(StudentRequestDTO dto) {
        if (studentRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Email already exists: " + dto.getEmail());
        }
        Student student = toEntity(dto);
        Student saved = studentRepository.save(student);
        return toDTO(saved);
    }

    // ── UPDATE ────────────────────────────────────────────────────────
    @Transactional
    public Optional<StudentResponseDTO> updateStudent(Long id, StudentRequestDTO dto) {
        return studentRepository.findById(id).map(existing -> {
            existing.setName(dto.getName());
            existing.setEmail(dto.getEmail());
            existing.setAge(dto.getAge());
            existing.setMajor(dto.getMajor());
            return toDTO(studentRepository.save(existing));
        });
    }

    // ── DELETE ────────────────────────────────────────────────────────
    @Transactional
    public boolean deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) return false;
        studentRepository.deleteById(id);
        return true;
    }

    // ── MAPPING HELPERS ───────────────────────────────────────────────
    private Student toEntity(StudentRequestDTO dto) {
        return new Student(dto.getName(), dto.getEmail(), dto.getAge(), dto.getMajor());
    }

    private StudentResponseDTO toDTO(Student s) {
        return new StudentResponseDTO(s.getId(), s.getName(), s.getEmail(),
                s.getAge(), s.getMajor(), s.getCreatedAt());
    }
}
