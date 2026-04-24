# ⭐ Module 07 — REST API

> **Level:** 🟡 Intermediate | **Estimated Time:** 4–5 days
> This is the most important module. Master this and you can build any REST API!

---

## 📚 Table of Contents
1. [What is REST?](#1-what-is-rest)
2. [HTTP Methods](#2-http-methods)
3. [HTTP Status Codes](#3-http-status-codes)
4. [@RestController vs @Controller](#4-restcontroller-vs-controller)
5. [Request Mapping Annotations](#5-request-mapping-annotations)
6. [Extracting Data from Requests](#6-extracting-data-from-requests)
7. [ResponseEntity](#7-responseentity)
8. [DTO Pattern](#8-dto-pattern)
9. [Layered Architecture](#9-layered-architecture)
10. [Jackson Serialization](#10-jackson-serialization)
11. [Student CRUD API — Full Example](#11-student-crud-api--full-example)
12. [How to Test with Postman](#12-how-to-test-with-postman)

---

## 1. What is REST?

**REST** (Representational State Transfer) is an architectural style for building web APIs.

**6 REST Principles:**
```
1. Client-Server      — frontend and backend are separate
2. Stateless          — each request contains all needed info (no sessions)
3. Cacheable          — responses can be cached
4. Uniform Interface  — consistent URL patterns and HTTP methods
5. Layered System     — client doesn't know if it talks to server or proxy
6. Code on Demand     — (optional) server can send executable code
```

**REST URL Design (Resources):**
```
Resource: Student

GET    /students          → get all students
GET    /students/{id}     → get student by ID
POST   /students          → create a new student
PUT    /students/{id}     → update student (full update)
PATCH  /students/{id}     → update student (partial update)
DELETE /students/{id}     → delete student
```

> 💡 URLs represent **nouns** (resources), HTTP methods represent **verbs** (actions).

---

## 2. HTTP Methods

| Method | Purpose | Request Body | Response Body | Idempotent? |
|--------|---------|-------------|---------------|-------------|
| `GET` | Read data | ❌ No | ✅ Yes | ✅ Yes |
| `POST` | Create new resource | ✅ Yes | ✅ Yes | ❌ No |
| `PUT` | Full update (replace) | ✅ Yes | ✅ Yes | ✅ Yes |
| `PATCH` | Partial update | ✅ Yes | ✅ Yes | ✅ Yes |
| `DELETE` | Delete resource | ❌ No | ❌ Usually no | ✅ Yes |

> 💡 **Idempotent** = calling the same request multiple times gives the same result.

---

## 3. HTTP Status Codes

| Code | Meaning | When to use |
|------|---------|-------------|
| `200 OK` | Success | GET, PUT, PATCH success |
| `201 Created` | Resource created | POST success |
| `204 No Content` | Success, no body | DELETE success |
| `400 Bad Request` | Invalid input | Validation failed |
| `401 Unauthorized` | Not authenticated | No/invalid token |
| `403 Forbidden` | Not authorized | Valid token but no permission |
| `404 Not Found` | Resource not found | ID doesn't exist |
| `409 Conflict` | Conflict | Duplicate email/username |
| `500 Internal Server Error` | Server crashed | Unhandled exception |

---

## 4. @RestController vs @Controller

```java
// @Controller — returns VIEW names (for HTML pages with Thymeleaf)
@Controller
public class PageController {
    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("name", "Punit");
        return "home"; // returns home.html template
    }
}

// @RestController — returns DATA (JSON/XML) directly
// = @Controller + @ResponseBody on every method
@RestController
public class ApiController {
    @GetMapping("/students")
    public List<Student> getStudents() {
        return List.of(new Student("Punit", 25)); // returns JSON automatically
    }
}
```

> 💡 For REST APIs, **always use `@RestController`**.

---

## 5. Request Mapping Annotations

```java
@RestController
@RequestMapping("/api/students")  // base path for all methods in this controller
public class StudentController {

    // GET /api/students
    @GetMapping
    public List<StudentResponseDTO> getAllStudents() { ... }

    // GET /api/students/{id}
    @GetMapping("/{id}")
    public StudentResponseDTO getById(@PathVariable Long id) { ... }

    // POST /api/students
    @PostMapping
    public ResponseEntity<StudentResponseDTO> create(@RequestBody StudentRequestDTO dto) { ... }

    // PUT /api/students/{id}
    @PutMapping("/{id}")
    public StudentResponseDTO update(@PathVariable Long id, @RequestBody StudentRequestDTO dto) { ... }

    // PATCH /api/students/{id}/email
    @PatchMapping("/{id}/email")
    public StudentResponseDTO updateEmail(@PathVariable Long id, @RequestBody String email) { ... }

    // DELETE /api/students/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) { ... }
}
```

---

## 6. Extracting Data from Requests

```java
// @PathVariable — from URL path: /students/42
@GetMapping("/{id}")
public Student getById(@PathVariable Long id) { ... }

// @RequestParam — from query string: /students?page=1&size=10&name=Punit
@GetMapping
public List<Student> search(
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "10") int size,
    @RequestParam(required = false) String name  // optional
) { ... }

// @RequestBody — from JSON body (POST/PUT)
@PostMapping
public Student create(@RequestBody StudentRequestDTO dto) {
    // Spring uses Jackson to deserialize JSON → Java object
    ...
}

// @RequestHeader — from HTTP headers
@GetMapping("/secure")
public String secure(@RequestHeader("Authorization") String token) { ... }

// Multiple params combined
@GetMapping("/department/{deptId}")
public List<Student> getByDept(
    @PathVariable Long deptId,
    @RequestParam(defaultValue = "name") String sortBy
) { ... }
```

---

## 7. ResponseEntity

`ResponseEntity<T>` gives you full control over the HTTP response: status code, headers, and body.

```java
// Return 200 OK with body
return ResponseEntity.ok(student);

// Return 201 Created with body
return ResponseEntity.status(HttpStatus.CREATED).body(createdStudent);

// Return 204 No Content (no body)
return ResponseEntity.noContent().build();

// Return 404 Not Found
return ResponseEntity.notFound().build();

// With custom headers
return ResponseEntity
    .created(URI.create("/api/students/" + newStudent.getId()))
    .header("X-Custom-Header", "value")
    .body(newStudent);

// Conditional return
Optional<Student> student = service.findById(id);
return student
    .map(ResponseEntity::ok)
    .orElse(ResponseEntity.notFound().build());
```

---

## 8. DTO Pattern

**DTO** = Data Transfer Object. A class used to transfer data between layers.

**Why use DTOs?**
```
Without DTO:                     With DTO:
─────────────────────────────    ─────────────────────────────────────
❌ Expose DB entity directly      ✅ Control exactly what is exposed
❌ Expose password field           ✅ Hide sensitive fields (password)
❌ Client can send any field       ✅ Validate only expected fields
❌ Tight coupling between          ✅ API contract decoupled from DB
   API and DB schema                  schema changes
```

```java
// Entity (maps to DB table — internal)
@Entity
public class Student {
    private Long id;
    private String name;
    private String email;
    private String password;  // ← should NEVER be sent to client!
    private LocalDateTime createdAt;
}

// Request DTO (what client sends to create a student)
public class StudentRequestDTO {
    private String name;    // only name + email needed to create
    private String email;
    private int age;
}

// Response DTO (what client receives)
public class StudentResponseDTO {
    private Long id;
    private String name;
    private String email;
    private int age;
    // NO password field!
    // NO createdAt (internal detail)
}
```

**Manual mapping (simple and clear):**
```java
@Service
public class StudentService {

    // Convert Request DTO → Entity
    private Student toEntity(StudentRequestDTO dto) {
        Student student = new Student();
        student.setName(dto.getName());
        student.setEmail(dto.getEmail());
        student.setAge(dto.getAge());
        return student;
    }

    // Convert Entity → Response DTO
    private StudentResponseDTO toDTO(Student student) {
        StudentResponseDTO dto = new StudentResponseDTO();
        dto.setId(student.getId());
        dto.setName(student.getName());
        dto.setEmail(student.getEmail());
        dto.setAge(student.getAge());
        return dto;
    }
}
```

---

## 9. Layered Architecture

```
┌─────────────────────────────────────────────────────────┐
│                    CLIENT (Postman/Browser)              │
└─────────────────────────┬───────────────────────────────┘
                          │ HTTP Request
┌─────────────────────────▼───────────────────────────────┐
│              CONTROLLER LAYER (@RestController)          │
│  • Receives HTTP requests                                │
│  • Validates input (basic)                               │
│  • Calls service layer                                   │
│  • Returns HTTP response with status code                │
└─────────────────────────┬───────────────────────────────┘
                          │ calls
┌─────────────────────────▼───────────────────────────────┐
│              SERVICE LAYER (@Service)                    │
│  • Contains ALL business logic                           │
│  • Orchestrates multiple repositories                    │
│  • Applies business rules and validations                │
│  • Maps between DTOs and entities                        │
└─────────────────────────┬───────────────────────────────┘
                          │ calls
┌─────────────────────────▼───────────────────────────────┐
│              REPOSITORY LAYER (@Repository)              │
│  • Talks to the database                                 │
│  • CRUD operations                                       │
│  • Custom queries                                        │
└─────────────────────────┬───────────────────────────────┘
                          │ SQL
┌─────────────────────────▼───────────────────────────────┐
│                       DATABASE                           │
└─────────────────────────────────────────────────────────┘
```

> ⚠️ **Rule:** Controller never talks to Repository directly. Always goes through Service.

---

## 10. Jackson Serialization

Spring uses **Jackson** to convert Java objects ↔ JSON automatically.

```java
// Java object:
public class Student {
    private Long id = 1L;
    private String name = "Punit";
    private String email = "punit@example.com";

    @JsonIgnore  // this field will NOT appear in JSON response
    private String password;

    @JsonProperty("full_name")  // renames field in JSON
    private String fullName;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;
}

// JSON output:
// {
//   "id": 1,
//   "name": "Punit",
//   "email": "punit@example.com",
//   "full_name": "Punit Namdeo",
//   "birthDate": "2000-01-15"
//   // password is hidden!
// }
```

---

## 11. Student CRUD API — Full Example

This module includes a **complete, runnable** Student Management REST API.

**How to run:**
```bash
cd 07-rest-api
mvn spring-boot:run
# App starts on http://localhost:8080
```

**Endpoints:**

| Method | URL | Description | Request Body |
|--------|-----|-------------|--------------|
| GET | `/api/students` | Get all students | — |
| GET | `/api/students/{id}` | Get by ID | — |
| GET | `/api/students/search?name=Punit` | Search by name | — |
| POST | `/api/students` | Create student | JSON body |
| PUT | `/api/students/{id}` | Update student | JSON body |
| DELETE | `/api/students/{id}` | Delete student | — |

---

## 12. How to Test with Postman

**Create a student (POST):**
```json
POST http://localhost:8080/api/students
Content-Type: application/json

{
  "name": "Punit Namdeo",
  "email": "punit@example.com",
  "age": 25,
  "major": "Computer Science"
}
```

**Response (201 Created):**
```json
{
  "id": 1,
  "name": "Punit Namdeo",
  "email": "punit@example.com",
  "age": 25,
  "major": "Computer Science"
}
```

**Get all students (GET):**
```
GET http://localhost:8080/api/students
```

**Get by ID (GET):**
```
GET http://localhost:8080/api/students/1
```

**Update student (PUT):**
```json
PUT http://localhost:8080/api/students/1
Content-Type: application/json

{
  "name": "Punit Namdeo Updated",
  "email": "punit.new@example.com",
  "age": 26,
  "major": "Software Engineering"
}
```

**Delete student (DELETE):**
```
DELETE http://localhost:8080/api/students/1
→ 204 No Content
```

---

## ✅ Best Practices
- Always use DTOs — never expose entities directly
- Return proper HTTP status codes (201 for create, 204 for delete)
- Keep controllers thin — business logic belongs in service
- Use `ResponseEntity` for full control over responses
- Name URLs with nouns (plural): `/students`, `/products`, `/orders`

## ⚠️ Common Mistakes
- Putting business logic in controllers
- Returning 200 for everything (even errors)
- Not using DTOs — exposing passwords and internal fields
- Using `@RequestBody` for GET requests

## 💡 Key Takeaways
- REST = nouns in URLs + HTTP verbs for actions
- `@RestController` + `@GetMapping/PostMapping/...` = REST endpoint
- Always use the right HTTP status codes
- DTOs protect your internal data model
- Layered architecture keeps code clean and testable

---

➡️ **Next:** [Module 08 — Spring Data JPA](../08-spring-data-jpa/README.md)
