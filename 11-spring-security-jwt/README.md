# Module 11 — Spring Security + JWT Authentication

> **Goal:** Secure your REST API so only authenticated users with a valid JWT token can access protected endpoints.

---

## 🧠 What You'll Learn

| Concept | Description |
|---|---|
| `spring-boot-starter-security` | Adds Spring Security to your project |
| `JWT (JSON Web Token)` | Stateless auth token — no session needed |
| `@RestControllerAdvice` | Inherited from Module 09 |
| `JwtUtil` | Generate, validate, and parse JWT tokens |
| `JwtAuthFilter` | Reads `Authorization` header on every request |
| `SecurityConfig` | Defines public vs protected routes |
| `AuthenticationManager` | Spring's built-in username/password authenticator |
| `UserDetailsService` | Loads user data (in-memory for this module) |
| `BCryptPasswordEncoder` | Hashes passwords securely |
| `SessionCreationPolicy.STATELESS` | No HTTP sessions — JWT handles state |

---

## 📁 Project Structure

```
11-spring-security-jwt/
├── pom.xml
└── src/main/java/com/learn/security/
    ├── SecurityApp.java
    ├── config/
    │   └── SecurityConfig.java          ← ⭐ Route rules, filter chain, users
    ├── filter/
    │   └── JwtAuthFilter.java           ← ⭐ Reads & validates JWT on every request
    ├── util/
    │   └── JwtUtil.java                 ← ⭐ Generate & validate JWT tokens
    ├── controller/
    │   ├── AuthController.java          ← POST /auth/login (PUBLIC)
    │   └── StudentController.java       ← 🔒 All routes PROTECTED
    ├── dto/
    │   ├── AuthRequest.java             ← Login request body
    │   └── AuthResponse.java            ← Login response { "token": "..." }
    ├── model/
    │   └── Student.java
    └── repository/
        └── StudentRepository.java
```

---

## 🔑 How JWT Auth Works — Full Flow

```
┌─────────────────────────────────────────────────────┐
│  STEP 1 — Login (get your token)                    │
│                                                     │
│  POST /auth/login                                   │
│  { "username": "admin", "password": "password" }    │
│                    ↓                                │
│  AuthController → AuthenticationManager             │
│               → UserDetailsService                  │
│               → BCrypt password check               │
│               → JwtUtil.generateToken()             │
│                    ↓                                │
│  Response: { "token": "eyJhbGci..." }  ✅           │
└─────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────┐
│  STEP 2 — Use token to access protected routes      │
│                                                     │
│  GET /api/students                                  │
│  Authorization: Bearer eyJhbGci...                  │
│                    ↓                                │
│  JwtAuthFilter runs first:                          │
│  → Extract token from header                        │
│  → JwtUtil.validateToken()                          │
│  → Set Authentication in SecurityContext            │
│                    ↓                                │
│  Request reaches StudentController ✅               │
└─────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────┐
│  WITHOUT a token:                                   │
│  GET /api/students  (no Authorization header)       │
│                    ↓                                │
│  JwtAuthFilter: no token found                      │
│  SecurityContext: empty                             │
│                    ↓                                │
│  Spring Security: 401 Unauthorized ❌               │
└─────────────────────────────────────────────────────┘
```

---

## 🚀 Run the App

```bash
cd 11-spring-security-jwt
mvn spring-boot:run
```

---

## 🧪 Test the Security

### Step 1 — Login and get your token
```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"password"}'
```
```json
{ "token": "eyJhbGciOiJIUzI1NiJ9..." }
```

### Step 2 — Use the token to access protected routes
```bash
# Copy the token from Step 1
TOKEN="eyJhbGciOiJIUzI1NiJ9..."

# ✅ Get all students (protected)
curl http://localhost:8080/api/students \
  -H "Authorization: Bearer $TOKEN"

# ✅ Who am I?
curl http://localhost:8080/api/students/me \
  -H "Authorization: Bearer $TOKEN"
```

### Step 3 — Try without a token
```bash
# ❌ 401 Unauthorized — no token
curl http://localhost:8080/api/students
```

### Step 4 — Wrong password
```bash
# ❌ 401 — bad credentials
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"wrong"}'
```

---

## 👤 In-Memory Users

| Username | Password | Role |
|---|---|---|
| `admin` | `password` | ROLE_ADMIN |
| `user` | `user123` | ROLE_USER |

---

## 🧪 Run Tests

```bash
mvn test
```

Expected: **5 tests pass** ✅

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
| 10 | Validation | ✅ Done |
| **11** | **Spring Security + JWT** | ✅ **Done** |
| 12 | Capstone Project 🏆 | 🔜 Next |
