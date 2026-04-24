# Module 10 — Validation with `@Valid`, `@NotBlank`, `@Email`, `@Size`

> **Goal:** Automatically reject invalid request data **before it hits your service layer** using Jakarta Bean Validation annotations.

---

## 🧠 What You'll Learn

| Concept | Description |
|---|---|
| `spring-boot-starter-validation` | The dependency that enables Bean Validation |
| `@Valid` | Triggers validation on `@RequestBody` in the controller |
| `@NotBlank` | Field must not be null, empty, or whitespace |
| `@Email` | Field must match a valid email format |
| `@Size(min,max)` | Enforces string length constraints |
| `@Min` / `@Max` | Enforces numeric range constraints |
| `MethodArgumentNotValidException` | Thrown automatically when @Valid fails |
| `BindingResult` | Holds ALL field errors (not just the first one) |

---

## 📁 Project Structure

```
10-validation/
├── pom.xml
└── src/main/java/com/learn/validation/
    ├── ValidationApp.java                    ← Main class
    ├── controller/
    │   └── StudentController.java            ← @Valid on @RequestBody ⭐
    ├── service/
    │   └── StudentService.java
    ├── repository/
    │   └── StudentRepository.java
    ├── model/
    │   └── Student.java                      ← Validation annotations on fields ⭐
    ├── exceptions/
    │   ├── StudentNotFoundException.java
    │   └── DuplicateEmailException.java
    ├── handler/
    │   └── GlobalExceptionHandler.java       ← Handles MethodArgumentNotValidException ⭐
    └── dto/
        └── ErrorResponse.java                ← Now includes fieldErrors list
```

---

## 🔑 Key Concepts Explained

### Step 1 — Add Validation Annotations to the Model

```java
public class Student {

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be a valid email address")
    private String email;

    @Min(value = 18, message = "Age must be at least 18")
    @Max(value = 100, message = "Age must be at most 100")
    private int age;
}
```

### Step 2 — Add `@Valid` to the Controller

```java
@PostMapping
public ResponseEntity<Student> createStudent(@Valid @RequestBody Student student) {
    //  ☝️ @Valid runs validation BEFORE this method body executes.
    //  If any constraint fails → MethodArgumentNotValidException → 400
    return ResponseEntity.status(HttpStatus.CREATED).body(studentService.createStudent(student));
}
```

### Step 3 — Handle the Exception in GlobalExceptionHandler

```java
@ExceptionHandler(MethodArgumentNotValidException.class)
public ResponseEntity<ErrorResponse> handleValidationErrors(
        MethodArgumentNotValidException ex, HttpServletRequest request) {

    List<ErrorResponse.FieldError> fieldErrors = ex.getBindingResult()
            .getFieldErrors().stream()
            .map(fe -> new ErrorResponse.FieldError(
                    fe.getField(), fe.getRejectedValue(), fe.getDefaultMessage()))
            .collect(Collectors.toList());
    // ...
}
```

---

## 📊 Validation Annotation Cheatsheet

| Annotation | Applies To | Description |
|---|---|---|
| `@NotNull` | Any | Must not be null |
| `@NotBlank` | String | Not null, not empty, not whitespace |
| `@NotEmpty` | String/Collection | Not null, not empty |
| `@Email` | String | Valid email format |
| `@Size(min,max)` | String/Collection | Length within range |
| `@Min(value)` | Number | Must be ≥ value |
| `@Max(value)` | Number | Must be ≤ value |
| `@Positive` | Number | Must be > 0 |
| `@Pattern(regexp)` | String | Must match regex |
| `@Future` | Date | Must be in the future |
| `@Past` | Date | Must be in the past |

---

## 🚀 Run the App

```bash
cd 10-validation
mvn spring-boot:run
```

---

## 🧪 Test the Validation

### ✅ Valid student — 201 Created
```bash
curl -X POST http://localhost:8080/api/students \
  -H "Content-Type: application/json" \
  -d '{"name":"David Lee","email":"david@example.com","course":"DevOps","age":23}'
```

### ❌ Blank name — 400 with fieldErrors
```bash
curl -X POST http://localhost:8080/api/students \
  -H "Content-Type: application/json" \
  -d '{"name":"","email":"test@example.com","course":"Java","age":20}'
```
```json
{
  "status": 400,
  "error": "Validation Failed",
  "message": "One or more fields have validation errors. See 'fieldErrors' for details.",
  "path": "/api/students",
  "timestamp": "2026-04-24 10:30:00",
  "fieldErrors": [
    {
      "field": "name",
      "rejectedValue": "",
      "message": "Name is required"
    }
  ]
}
```

### ❌ Invalid email — 400
```bash
curl -X POST http://localhost:8080/api/students \
  -H "Content-Type: application/json" \
  -d '{"name":"Test","email":"not-an-email","course":"Java","age":21}'
```

### ❌ Multiple errors at once — ALL reported in one response
```bash
curl -X POST http://localhost:8080/api/students \
  -H "Content-Type: application/json" \
  -d '{"name":"","email":"bad","course":"","age":10}'
```

---

## 🧪 Run Tests

```bash
mvn test
```

Expected: **6 tests pass** ✅

---

## 📊 Progress Tracker

| # | Module | Status |
|---|---|---|
| 01 | Java Fundamentals | ✅ Done |
| 02 | Java OOP | ✅ Done |
| 03 | Java Advanced | ✅ Done |
| 04 | Maven & Build Tools | ✅ Done |
| 05 | Spring Core | ✅ Done |
| 06 | Spring Boot Basics | ✅ Done |
| 07 | REST API ⭐ | ✅ Done |
| 08 | Spring Data JPA | ✅ Done |
| 09 | Exception Handling | ✅ Done |
| **10** | **Validation** | ✅ **Done** |
| 11 | Spring Security + JWT | 🔜 Next |
| 12 | Capstone Project 🏆 | ⏳ Pending |
