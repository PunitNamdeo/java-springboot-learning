package com.learn.jpa.repository;

import com.learn.jpa.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * StudentRepository — DATA ACCESS LAYER
 *
 * Extends JpaRepository<Student, Long> which provides:
 *   save(), findById(), findAll(), deleteById(), count(), existsById() and more!
 *
 * We only need to add CUSTOM query methods here.
 * Spring Data generates the SQL automatically from method names.
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    // ── Derived Query Methods (Spring generates SQL from name) ────────

    // SELECT * FROM students WHERE email = ?
    Optional<Student> findByEmail(String email);

    // SELECT * FROM students WHERE email = ? (existence check)
    boolean existsByEmail(String email);

    // SELECT * FROM students WHERE LOWER(name) LIKE %?%
    List<Student> findByNameContainingIgnoreCase(String name);

    // SELECT * FROM students WHERE major = ?
    List<Student> findByMajor(String major);

    // SELECT * FROM students WHERE major = ? (paginated)
    Page<Student> findByMajor(String major, Pageable pageable);

    // SELECT * FROM students WHERE age > ?
    List<Student> findByAgeGreaterThan(int age);

    // SELECT * FROM students WHERE age BETWEEN ? AND ?
    List<Student> findByAgeBetween(int min, int max);

    // SELECT COUNT(*) FROM students WHERE major = ?
    long countByMajor(String major);

    // ── Custom @Query (JPQL) ──────────────────────────────────────────

    // Find students by major and minimum age, ordered by name
    @Query("SELECT s FROM Student s WHERE s.major = :major AND s.age >= :minAge ORDER BY s.name")
    List<Student> findByMajorAndMinAge(@Param("major") String major, @Param("minAge") int minAge);

    // Search across name AND major with a single keyword
    @Query("SELECT s FROM Student s WHERE " +
           "LOWER(s.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(s.major) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Student> searchByKeyword(@Param("keyword") String keyword);
}
