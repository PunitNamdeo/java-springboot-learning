package com.learn.capstone.repository;

import com.learn.capstone.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Combines Module 08 (JPA) features:
 * - Pagination
 * - Custom JPQL queries
 * - Derived query methods
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    boolean existsByEmail(String email);

    // Derived query — find by course
    List<Student> findByCourse(String course);

    // Derived query — find by grade
    List<Student> findByGrade(String grade);

    // Custom JPQL — search by name or email (case-insensitive)
    @Query("SELECT s FROM Student s WHERE " +
           "LOWER(s.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(s.email) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Student> searchByKeyword(@Param("keyword") String keyword);

    // Pagination support
    Page<Student> findAll(Pageable pageable);

    Page<Student> findByCourse(String course, Pageable pageable);
}
