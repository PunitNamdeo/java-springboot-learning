# Module 09 — Exception Handling with `@ControllerAdvice`

> **Goal:** Replace Spring's ugly white-label error page with clean, structured JSON error responses using a `GlobalExceptionHandler`.

---

## 🧠 What You'll Learn

| Concept | Description |
|---|---|
| `@RestControllerAdvice` | Intercepts exceptions from all controllers globally |
| `@ExceptionHandler` | Maps a specific exception type to an HTTP response |
| Custom Exceptions | `StudentNotFoundException`, `DuplicateEmailException` |
| `ErrorResponse` DTO | Structured JSON body with status, message, timestamp |
| HTTP Status Codes | 404, 409, 400, 500 mapped correctly |

---

## 📁 Project Structure

```
09-exception-handling/
├── pom.xml
└── src/main/java/com/learn/exception/
    ├── ExceptionHandlingApp.java          ← Main class
    ├── controller/
    │   └── StudentController.java         ← REST endpoints
    ├── service/
    │   └── StudentService.java            ← Business logic + throws exceptions
    ├── repository/
    │   └── StudentRepository.java         ← JPA repository
    ├── model/
    │   └── Student.java                   ← JPA Entity
    ├── exceptions/
    │   ├── StudentNotFoundException.java  ← Custom 404 exception
    │   └── DuplicateEmailException.java   ← Custom 409 exception
    ├── handler/
    │   └── GlobalExceptionHandler.java    ← ⭐ The star of this module
    └── dto/
        └── ErrorResponse.java             ← Clean JSON error structure
```

---

## 🔑 Key Concepts Explained

### Before vs After

**❌ Before (Spring default — ugly HTML):**
```
Whitelabel Error Page
This application has no explicit mapping for /error...
```

**✅ After (our GlobalExceptionHandler — clean JSON):**
```json
{
  "status": 404,
  "error": "Not Found",
  "message": "Student not found with ID: 99",
  "path": "/api/students/99",
  "timestamp": "2026-04-24 10:30:00"
}
```

---

### How `@RestControllerAdvice` Works

```
[Request] → [Controller] → throws StudentNotFoundException
                                        ↓
                          [GlobalExceptionHandler]
                          @ExceptionHandler(StudentNotFoundException.class)
                                        ↓
                          Returns 404 + ErrorResponse JSON
                                        ↓
                                   [Client] ✅
```

---

### Exception Map

| Exception | HTTP Status | Scenario |
|---|---|---|
| `StudentNotFoundException` | `404 Not Found` | GET/DELETE non-existent student |
| `DuplicateEmailException` | `409 Conflict` | POST with existing email |
| `MethodArgumentTypeMismatchException` | `400 Bad Request` | `/students/abc` instead of `/students/1` |
| `Exception` (fallback) | `500 Internal Server Error` | Any unexpected error |

---

## 🚀 Run the App

```bash
cd 09-exception-handling
mvn spring-boot:run
```

---

## 🧪 Test the Exception Handling

### ✅ Happy Path — Get all students
```bash
curl http://localhost:8080/api/students
```

### ❌ 404 — Student not found
```bash
curl http://localhost:8080/api/students/999
```
```json
{
  "status": 404,
  "error": "Not Found",
  "message": "Student not found with ID: 999",
  "path": "/api/students/999",
  "timestamp": "2026-04-24 10:30:00"
}
```

### ❌ 409 — Duplicate email
```bash
curl -X POST http://localhost:8080/api/students \
  -H "Content-Type: application/json" \
  -d '{"name":"Alice Copy","email":"alice@example.com","course":"Java"}'
```
```json
{
  "status": 409,
  "error": "Conflict",
  "message": "A student with email 'alice@example.com' already exists.",
  "path": "/api/students",
  "timestamp": "2026-04-24 10:30:00"
}
```

### ❌ 400 — Bad path variable
```bash
curl http://localhost:8080/api/students/abc
```
```json
{
  "status": 400,
  "error": "Bad Request",
  "message": "Parameter 'id' must be of type Long",
  "path": "/api/students/abc",
  "timestamp": "2026-04-24 10:30:00"
}
```

### ❌ DELETE — Student not found
```bash
curl -X DELETE http://localhost:8080/api/students/999
```

---

## 🧪 Run Tests

```bash
mvn test
```

Expected: **4 tests pass** ✅

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
| **09** | **Exception Handling** | ✅ **Done** |
| 10 | Validation | 🔜 Next |
| 11 | Spring Security + JWT | ⏳ Pending |
| 12 | Capstone Project 🏆 | ⏳ Pending |
