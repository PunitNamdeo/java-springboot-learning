package com.learn.exception.service;

import com.learn.exception.exceptions.DuplicateEmailException;
import com.learn.exception.exceptions.StudentNotFoundException;
import com.learn.exception.model.Student;
import com.learn.exception.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student getStudentById(Long id) {
        // Throws StudentNotFoundException if not found → caught by GlobalExceptionHandler
        return studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));
    }

    public Student createStudent(Student student) {
        // Throws DuplicateEmailException if email already exists
        if (studentRepository.existsByEmail(student.getEmail())) {
            throw new DuplicateEmailException(student.getEmail());
        }
        return studentRepository.save(student);
    }

    public Student updateStudent(Long id, Student updated) {
        Student existing = getStudentById(id); // reuses not-found check
        existing.setName(updated.getName());
        existing.setEmail(updated.getEmail());
        existing.setCourse(updated.getCourse());
        return studentRepository.save(existing);
    }

    public void deleteStudent(Long id) {
        getStudentById(id); // throws if not found
        studentRepository.deleteById(id);
    }
}
