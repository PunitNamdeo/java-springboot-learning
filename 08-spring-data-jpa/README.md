# 📗 Module 08 — Spring Data JPA

> **Level:** 🟡 Intermediate | **Estimated Time:** 3–4 days

---

## 📚 Table of Contents
1. [What is JPA?](#1-what-is-jpa)
2. [@Entity & @Table](#2-entity--table)
3. [Column Annotations](#3-column-annotations)
4. [JpaRepository](#4-jparepository)
5. [Derived Query Methods](#5-derived-query-methods)
6. [@Query — Custom JPQL & Native SQL](#6-query--custom-jpql--native-sql)
7. [Relationships](#7-relationships)
8. [Pagination & Sorting](#8-pagination--sorting)
9. [@Transactional](#9-transactional)
10. [H2 Console — View Your Data](#10-h2-console--view-your-data)
11. [Full Example — Student API with Real DB](#11-full-example--student-api-with-real-db)

---

## 1. What is JPA?

**JPA** (Java Persistence API) is a specification for storing Java objects in a relational database.

```
Without JPA:                          With Spring Data JPA:
──────────────────────────────────    ──────────────────────────────────────────
❌ Write SQL manually                 ✅ Spring generates SQL for you
❌ Open/close connections             ✅ Connection pooling handled
❌ Map ResultSet → Java object        ✅ @Entity maps class → table automatically
❌ Write boilerplate CRUD             ✅ JpaRepository gives you CRUD for free
```

**Stack:**
```
Your Code (Java)
     ↓
Spring Data JPA       ← what you interact with
     ↓
Hibernate (ORM)       ← JPA implementation (generates SQL)
     ↓
JDBC
     ↓
Database (H2 / MySQL / PostgreSQL)
```

---

## 2. @Entity & @Table

```java
@Entity                          // marks this class as a JPA entity (maps to a DB table)
@Table(name = "students")        // optional: specify table name (default = class name)
public class Student {

    @Id                          // marks this as the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // auto-increment
    private Long id;

    private String name;         // maps to column "name" automatically
    private String email;
    private int age;
    private String major;

    @CreationTimestamp           // Hibernate sets this on INSERT
    private LocalDateTime createdAt;

    @UpdateTimestamp             // Hibernate sets this on UPDATE
    private LocalDateTime updatedAt;

    // constructors, getters, setters...
}
```

**What Hibernate generates from this:**
```sql
CREATE TABLE students (
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    name       VARCHAR(255),
    email      VARCHAR(255),
    age        INTEGER,
    major      VARCHAR(255),
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);
```

---

## 3. Column Annotations

```java
@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name", nullable = false, length = 100)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(columnDefinition = "INT DEFAULT 0")
    private int age;

    @Column(length = 50)
    private String major;

    @Column(updatable = false)   // never update this column after INSERT
    @CreationTimestamp
    private LocalDateTime createdAt;
}
```

---

## 4. JpaRepository

`JpaRepository<Entity, IdType>` gives you **18+ CRUD methods for free** — no SQL needed!

```java
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    // That's it! Spring Data generates all implementations automatically.
}
```

**Free methods you get:**
```java
// SAVE
studentRepository.save(student);           // INSERT or UPDATE
studentRepository.saveAll(list);           // batch save

// READ
studentRepository.findById(1L);            // Optional<Student>
studentRepository.findAll();               // List<Student>
studentRepository.findAll(pageable);       // Page<Student>
studentRepository.findAllById(ids);        // List<Student>
studentRepository.count();                 // long
exists = studentRepository.existsById(1L); // boolean

// DELETE
studentRepository.deleteById(1L);
studentRepository.delete(student);
studentRepository.deleteAll();
```

---

## 5. Derived Query Methods

Spring Data generates SQL from **method names** automatically!

```java
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    // SELECT * FROM students WHERE email = ?
    Optional<Student> findByEmail(String email);

    // SELECT * FROM students WHERE name LIKE %?%
    List<Student> findByNameContainingIgnoreCase(String name);

    // SELECT * FROM students WHERE major = ?
    List<Student> findByMajor(String major);

    // SELECT * FROM students WHERE age > ?
    List<Student> findByAgeGreaterThan(int age);

    // SELECT * FROM students WHERE age BETWEEN ? AND ?
    List<Student> findByAgeBetween(int min, int max);

    // SELECT * FROM students WHERE major = ? AND age > ?
    List<Student> findByMajorAndAgeGreaterThan(String major, int age);

    // SELECT * FROM students ORDER BY name ASC
    List<Student> findAllByOrderByNameAsc();

    // SELECT COUNT(*) FROM students WHERE major = ?
    long countByMajor(String major);

    // DELETE FROM students WHERE email = ?
    void deleteByEmail(String email);

    // SELECT * FROM students WHERE email = ? (check existence)
    boolean existsByEmail(String email);
}
```

**Keyword reference:**
| Keyword | SQL | Example |
|---------|-----|---------|
| `findBy` | `WHERE` | `findByName` |
| `And` | `AND` | `findByNameAndEmail` |
| `Or` | `OR` | `findByNameOrEmail` |
| `Containing` | `LIKE %?%` | `findByNameContaining` |
| `StartingWith` | `LIKE ?%` | `findByNameStartingWith` |
| `GreaterThan` | `>` | `findByAgeGreaterThan` |
| `LessThan` | `<` | `findByAgeLessThan` |
| `Between` | `BETWEEN` | `findByAgeBetween` |
| `IsNull` | `IS NULL` | `findByMajorIsNull` |
| `OrderBy` | `ORDER BY` | `findAllByOrderByNameAsc` |
| `IgnoreCase` | `LOWER(?)` | `findByNameIgnoreCase` |

---

## 6. @Query — Custom JPQL & Native SQL

When derived method names get too complex, use `@Query`:

```java
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    // JPQL (uses class/field names, not table/column names)
    @Query("SELECT s FROM Student s WHERE s.email = :email")
    Optional<Student> findByEmailJPQL(@Param("email") String email);

    // JPQL with multiple conditions
    @Query("SELECT s FROM Student s WHERE s.major = :major AND s.age >= :minAge ORDER BY s.name")
    List<Student> findByMajorAndMinAge(@Param("major") String major, @Param("minAge") int minAge);

    // Native SQL (use actual table/column names)
    @Query(value = "SELECT * FROM students WHERE major = :major", nativeQuery = true)
    List<Student> findByMajorNative(@Param("major") String major);

    // Modifying query (UPDATE/DELETE) — needs @Modifying + @Transactional
    @Modifying
    @Transactional
    @Query("UPDATE Student s SET s.major = :newMajor WHERE s.id = :id")
    int updateMajor(@Param("id") Long id, @Param("newMajor") String newMajor);
}
```

---

## 7. Relationships

### @ManyToOne / @OneToMany
```java
// Department — has many students
@Entity
public class Department {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    private List<Student> students = new ArrayList<>();
}

// Student — belongs to one department
@Entity
public class Student {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)   // LAZY = don't load department unless accessed
    @JoinColumn(name = "department_id")  // FK column in students table
    private Department department;
}
```

### Fetch Types
```
FetchType.EAGER — load related entity immediately with parent query (default for @ManyToOne)
FetchType.LAZY  — load related entity only when accessed (recommended, avoids N+1 problem)
```

---

## 8. Pagination & Sorting

```java
// Controller
@GetMapping
public Page<StudentResponseDTO> getAll(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "name") String sortBy) {

    Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
    return studentService.getAllPaged(pageable);
}

// Service
public Page<StudentResponseDTO> getAllPaged(Pageable pageable) {
    return studentRepository.findAll(pageable)
            .map(this::toResponseDTO);
}
```

**Query parameters:**
```
GET /api/students?page=0&size=5&sortBy=name
GET /api/students?page=1&size=10&sortBy=age
```

**Response structure (Page object):**
```json
{
  "content": [...],
  "totalElements": 50,
  "totalPages": 5,
  "number": 0,
  "size": 10,
  "first": true,
  "last": false
}
```

---

## 9. @Transactional

```java
@Service
public class StudentService {

    // @Transactional ensures ALL operations succeed or ALL rollback
    @Transactional
    public void transferStudent(Long studentId, Long newDeptId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Department newDept = departmentRepository.findById(newDeptId)
                .orElseThrow(() -> new RuntimeException("Department not found"));

        student.setDepartment(newDept);
        studentRepository.save(student);
        // If anything above throws, ALL changes are rolled back automatically
    }

    // Read-only transaction (performance optimization)
    @Transactional(readOnly = true)
    public List<StudentResponseDTO> getAllStudents() {
        return studentRepository.findAll().stream()
                .map(this::toResponseDTO)
                .toList();
    }
}
```

---

## 10. H2 Console — View Your Data

After starting the app, visit: **http://localhost:8080/h2-console**

```
Saved Settings: Generic H2 (Embedded)
Driver Class:   org.h2.Driver
JDBC URL:       jdbc:h2:mem:testdb
User Name:      sa
Password:       (leave empty)
```

Then run SQL:
```sql
SELECT * FROM STUDENTS;
```

---

## 11. Full Example — Student API with Real DB

This module includes a complete, runnable Spring Boot + JPA app.

```bash
cd 08-spring-data-jpa
mvn spring-boot:run
```

| Endpoint | Description |
|----------|-------------|
| `GET /api/students` | All students (pageable) |
| `GET /api/students/{id}` | Get by ID |
| `GET /api/students/search?name=Punit` | Search by name |
| `GET /api/students/major/{major}` | Get by major |
| `POST /api/students` | Create student |
| `PUT /api/students/{id}` | Update student |
| `DELETE /api/students/{id}` | Delete student |
| `GET /h2-console` | View database |

---

## ✅ Best Practices
- Use `FetchType.LAZY` for relationships to avoid N+1 queries
- Use `@Transactional(readOnly = true)` on read-only service methods
- Never use `ddl-auto=create` or `create-drop` in production
- Use DTOs — never return `@Entity` objects directly from controllers
- Index frequently queried columns with `@Index`

## ⚠️ Common Mistakes
- `ddl-auto=create` in production → **drops all tables on startup!**
- Using `FetchType.EAGER` everywhere → causes N+1 query problem
- Forgetting `@Transactional` on modifying `@Query` methods
- Returning JPA entities directly (exposes internal structure, causes lazy-load issues)

## 💡 Key Takeaways
- `@Entity` maps a Java class to a DB table
- `JpaRepository` provides free CRUD — no SQL needed
- Derived query methods generate SQL from method names
- `@Query` for complex queries
- Pagination is built-in with `Pageable`

---

➡️ **Next:** [Module 09 — Exception Handling](../09-exception-handling/README.md)
