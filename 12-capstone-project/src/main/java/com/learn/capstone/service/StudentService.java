package com.learn.capstone.service;

import com.learn.capstone.dto.StudentSummary;
import com.learn.capstone.exceptions.DuplicateEmailException;
import com.learn.capstone.exceptions.StudentNotFoundException;
import com.learn.capstone.model.Student;
import com.learn.capstone.repository.StudentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Business logic layer — combines all modules:
 * - Module 08: Pagination, custom queries
 * - Module 09: Custom exceptions
 * - Module 10: Validation enforced at controller level
 */
@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // Get all (paginated)
    public Page<Student> getAllStudents(Pageable pageable) {
        return studentRepository.findAll(pageable);
    }

    // Get by ID
    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));
    }

    // Create
    public Student createStudent(Student student) {
        if (studentRepository.existsByEmail(student.getEmail())) {
            throw new DuplicateEmailException(student.getEmail());
        }
        return studentRepository.save(student);
    }

    // Update
    public Student updateStudent(Long id, Student updated) {
        Student existing = getStudentById(id);
        // Only allow email change if new email doesn't belong to another student
        if (!existing.getEmail().equals(updated.getEmail())
                && studentRepository.existsByEmail(updated.getEmail())) {
            throw new DuplicateEmailException(updated.getEmail());
        }
        existing.setName(updated.getName());
        existing.setEmail(updated.getEmail());
        existing.setCourse(updated.getCourse());
        existing.setAge(updated.getAge());
        existing.setGrade(updated.getGrade());
        return studentRepository.save(existing);
    }

    // Delete
    public void deleteStudent(Long id) {
        getStudentById(id); // throws if not found
        studentRepository.deleteById(id);
    }

    // Search by keyword
    public List<Student> search(String keyword) {
        return studentRepository.searchByKeyword(keyword);
    }

    // Filter by course (paginated)
    public Page<Student> getByCourse(String course, Pageable pageable) {
        return studentRepository.findByCourse(course, pageable);
    }

    // Filter by grade
    public List<Student> getByGrade(String grade) {
        return studentRepository.findByGrade(grade);
    }

    // Summary DTO list (lightweight view)
    public List<StudentSummary> getSummaries() {
        return studentRepository.findAll().stream()
                .map(s -> new StudentSummary(s.getId(), s.getName(), s.getCourse(), s.getGrade()))
                .collect(Collectors.toList());
    }
}
