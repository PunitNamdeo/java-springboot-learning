# Module 12 🏆 — Capstone Project: Complete Student Management System

> **Goal:** Combine ALL 11 modules into one complete, production-style REST API.

---

## 🧠 What This Capstone Combines

| Module | Feature Used |
|---|---|
| 01–03 | Java fundamentals, OOP, generics, streams |
| 04 | Maven build with all dependencies |
| 05–06 | Spring Core, component scanning, auto-config |
| 07 | Full REST API with proper HTTP verbs |
| 08 | JPA entity, repository, pagination, custom JPQL |
| 09 | GlobalExceptionHandler — 404, 409, 403, 400, 500 |
| 10 | `@Valid`, `@NotBlank`, `@Email`, `@Size`, `@Min/@Max`, `@Pattern` |
| 11 | Spring Security + JWT — role-based access control |

---

## 📁 Project Structure

```
12-capstone-project/
├── pom.xml
└── src/main/java/com/learn/capstone/
    ├── CapstoneApp.java
    ├── config/
    │   └── SecurityConfig.java          ← Role-based route rules
    ├── filter/
    │   └── JwtAuthFilter.java
    ├── util/
    │   └── JwtUtil.java
    ├── controller/
    │   ├── AuthController.java          ← POST /auth/login
    │   └── StudentController.java       ← Full CRUD + search + pagination
    ├── service/
    │   └── StudentService.java          ← Business logic
    ├── repository/
    │   └── StudentRepository.java       ← JPA + custom queries + pagination
    ├── model/
    │   └── Student.java                 ← Entity + validation annotations
    ├── dto/
    │   ├── AuthRequest.java
    │   ├── AuthResponse.java            ← token + username + role
    │   ├── StudentSummary.java          ← Lightweight DTO (no email exposed)
    │   └── ErrorResponse.java           ← Structured JSON errors
    ├── exceptions/
    │   ├── StudentNotFoundException.java
    │   └── DuplicateEmailException.java
    └── handler/
        └── GlobalExceptionHandler.java  ← 400, 403, 404, 409, 500
```

---

## 🔐 Role-Based Access Control

| Method | Endpoint | ADMIN | USER |
|---|---|---|---|
| POST | `/auth/login` | ✅ Public | ✅ Public |
| GET | `/api/students/**` | ✅ | ✅ |
| POST | `/api/students` | ✅ | ❌ 403 |
| PUT | `/api/students/{id}` | ✅ | ❌ 403 |
| DELETE | `/api/students/{id}` | ✅ | ❌ 403 |

---

## 🚀 Run the App

```bash
cd 12-capstone-project
mvn spring-boot:run
```

---

## 🧪 Full API Walkthrough

### Step 1 — Login
```bash
# As ADMIN
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"password"}'
# → { "token": "...", "username": "admin", "role": "ROLE_ADMIN" }

# As USER
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"user","password":"user123"}'
```

### Step 2 — Use the token
```bash
ADMIN_TOKEN="paste-admin-token"
USER_TOKEN="paste-user-token"

# ✅ Get all (paginated)
curl "http://localhost:8080/api/students?page=0&size=3&sort=name" \
  -H "Authorization: Bearer $USER_TOKEN"

# ✅ Search by keyword
curl "http://localhost:8080/api/students/search?keyword=alice" \
  -H "Authorization: Bearer $USER_TOKEN"

# ✅ Filter by course
curl "http://localhost:8080/api/students/course/Spring Boot" \
  -H "Authorization: Bearer $USER_TOKEN"

# ✅ Filter by grade
curl "http://localhost:8080/api/students/grade/A" \
  -H "Authorization: Bearer $USER_TOKEN"

# ✅ Summary (no email exposed)
curl http://localhost:8080/api/students/summary \
  -H "Authorization: Bearer $USER_TOKEN"

# ✅ Create (ADMIN only)
curl -X POST http://localhost:8080/api/students \
  -H "Authorization: Bearer $ADMIN_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"name":"New Student","email":"new@example.com","course":"DevOps","age":22,"grade":"B"}'

# ❌ Create as USER → 403
curl -X POST http://localhost:8080/api/students \
  -H "Authorization: Bearer $USER_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"name":"Hacker","email":"hack@example.com","course":"DevOps","age":22,"grade":"A"}'

# ❌ Validation error → 400
curl -X POST http://localhost:8080/api/students \
  -H "Authorization: Bearer $ADMIN_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"name":"","email":"bad","course":"","age":10,"grade":"Z"}'

# ❌ Not found → 404
curl http://localhost:8080/api/students/9999 \
  -H "Authorization: Bearer $USER_TOKEN"
```

---

## 🧪 Run Tests

```bash
mvn test
# Expected: 10 tests pass ✅
```

---

## 📊 Final Progress Tracker

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
| 10 | Validation | ✅ Done |
| 11 | Spring Security + JWT | ✅ Done |
| **12** | **Capstone Project 🏆** | ✅ **COMPLETE!** |

---

## 🎉 Congratulations!

You have built a **complete, production-style Spring Boot REST API** from scratch covering:

✅ Java fundamentals → OOP → Advanced Java  
✅ Maven build system  
✅ Spring Core + Boot auto-configuration  
✅ Full REST API with proper HTTP status codes  
✅ JPA persistence with pagination & custom queries  
✅ Global exception handling with structured JSON errors  
✅ Bean validation with field-level error reporting  
✅ JWT-based authentication + role-based authorization  

**You are now a Spring Boot developer. 🚀**
