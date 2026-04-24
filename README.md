# ☕ Java + Spring Boot: Zero to Hero Learning Guide

![Java](https://img.shields.io/badge/Java-17-orange?style=for-the-badge&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen?style=for-the-badge&logo=springboot)
![Maven](https://img.shields.io/badge/Maven-3.x-red?style=for-the-badge&logo=apachemaven)
![License](https://img.shields.io/badge/License-MIT-blue?style=for-the-badge)

> **A complete, beginner-friendly repository to take you from absolute zero to building production-ready Spring Boot REST APIs — independently.**

---

## 🎯 What You Will Achieve

After completing this guide, you will be able to:
- ✅ Write confident, clean Java code
- ✅ Understand OOP, Collections, Streams, Lambdas
- ✅ Build a Spring Boot project from scratch
- ✅ Create, test, and document RESTful APIs
- ✅ Connect to a database using Spring Data JPA
- ✅ Handle exceptions and validate inputs
- ✅ Implement JWT-based Security
- ✅ Work independently on real-world Spring Boot projects

---

## 🗺️ Learning Roadmap

```
┌─────────────────────────────────────────────────────────────┐
│              JAVA + SPRING BOOT: ZERO TO HERO               │
└─────────────────────────────────────────────────────────────┘

 🟢 BEGINNER
 ┌────────────────────────┐
 │ 01 - Java Fundamentals │  Variables, Types, Loops, Arrays, Strings
 └──────────┬─────────────┘
            │
 ┌──────────▼─────────────┐
 │ 02 - Java OOP          │  Classes, Inheritance, Polymorphism, Abstraction
 └──────────┬─────────────┘
            │
 🟡 INTERMEDIATE
 ┌──────────▼─────────────┐
 │ 03 - Java Advanced     │  Collections, Streams, Lambdas, Generics
 └──────────┬─────────────┘
            │
 ┌──────────▼─────────────┐
 │ 04 - Maven             │  Build Tool, pom.xml, Dependencies, Lifecycle
 └──────────┬─────────────┘
            │
 ┌──────────▼─────────────┐
 │ 05 - Spring Core       │  IoC, Dependency Injection, Beans
 └──────────┬─────────────┘
            │
 ┌──────────▼─────────────┐
 │ 06 - Spring Boot       │  Auto-Config, Profiles, Actuator, Starters
 └──────────┬─────────────┘
            │
 ┌──────────▼─────────────┐
 │ 07 - REST API ⭐        │  Controllers, HTTP, DTOs, Layered Architecture
 └──────────┬─────────────┘
            │
 ┌──────────▼─────────────┐
 │ 08 - Spring Data JPA   │  Entities, Repositories, H2, Queries, Pagination
 └──────────┬─────────────┘
            │
 ┌──────────▼─────────────┐
 │ 09 - Exception Handling│  @ControllerAdvice, Custom Exceptions
 └──────────┬─────────────┘
            │
 ┌──────────▼─────────────┐
 │ 10 - Validation        │  Bean Validation, @Valid, Custom Validators
 └──────────┬─────────────┘
            │
 🔴 ADVANCED
 ┌──────────▼─────────────┐
 │ 11 - Security + JWT    │  Authentication, Authorization, JWT Flow
 └──────────┬─────────────┘
            │
 ┌──────────▼─────────────┐
 │ 12 - Capstone Project  │  Full Task Manager REST API (All Concepts)
 └────────────────────────┘
```

---

## 📦 Module Overview

| # | Module | Level | Topics | Est. Time |
|---|--------|-------|--------|-----------|
| 01 | Java Fundamentals | 🟢 Beginner | Data Types, Variables, Loops, Arrays, Strings | 2–3 days |
| 02 | Java OOP | 🟢 Beginner | Classes, Inheritance, Polymorphism, Abstraction, Encapsulation | 3–4 days |
| 03 | Java Advanced | 🟡 Intermediate | Collections, Streams, Lambdas, Generics, Exceptions | 4–5 days |
| 04 | Maven & Build Tools | 🟢 Beginner | pom.xml, Dependencies, Lifecycle, Commands | 1 day |
| 05 | Spring Core | 🟡 Intermediate | IoC, DI, Beans, ApplicationContext, Scopes | 2–3 days |
| 06 | Spring Boot Basics | 🟡 Intermediate | Auto-config, Profiles, Actuator, DevTools | 2 days |
| 07 | REST API ⭐ | 🟡 Intermediate | HTTP Methods, Controllers, DTOs, Layered Architecture | 4–5 days |
| 08 | Spring Data JPA | 🟡 Intermediate | ORM, Entities, Repositories, H2, Queries, Pagination | 4–5 days |
| 09 | Exception Handling | 🟡 Intermediate | @ControllerAdvice, Custom Exceptions, Error Responses | 2 days |
| 10 | Validation | 🟡 Intermediate | Bean Validation, @Valid, Custom Validators | 1–2 days |
| 11 | Spring Security + JWT | 🔴 Advanced | Auth/Authz, JWT, SecurityFilterChain, Roles | 4–5 days |
| 12 | Capstone Project 🏆 | 🔴 Advanced | Full Task Manager API combining all concepts | 5–7 days |

---

## 🛠️ Prerequisites

Before you start, install the following:

| Tool | Download | Purpose |
|------|----------|---------|
| **JDK 17+** | [adoptium.net](https://adoptium.net) | Java Development Kit |
| **IntelliJ IDEA** | [jetbrains.com/idea](https://www.jetbrains.com/idea/) | Best IDE for Java/Spring Boot |
| **Maven 3.x** | [maven.apache.org](https://maven.apache.org) | Build tool (or use IntelliJ's bundled Maven) |
| **Postman** | [postman.com](https://www.postman.com) | API testing tool |
| **Git** | [git-scm.com](https://git-scm.com) | Version control |

---

## 🚀 How to Use This Repository

```bash
# 1. Clone the repository
git clone https://github.com/PunitNamdeo/java-springboot-learning.git

# 2. Open in IntelliJ IDEA
# File → Open → Select the folder

# 3. Start from Module 01 and read the README.md in each folder

# 4. For Spring Boot modules (06-12), run with:
cd 07-rest-api
mvn spring-boot:run

# 5. Test APIs using Postman or browser
```

---

## 📚 Module Links

1. [01 - Java Fundamentals](./01-java-fundamentals/README.md)
2. [02 - Java OOP](./02-java-oop/README.md)
3. [03 - Java Advanced](./03-java-advanced/README.md)
4. [04 - Maven & Build Tools](./04-maven-and-build-tools/README.md)
5. [05 - Spring Core](./05-spring-core/README.md)
6. [06 - Spring Boot Basics](./06-spring-boot-basics/README.md)
7. [07 - REST API ⭐](./07-rest-api/README.md)
8. [08 - Spring Data JPA](./08-spring-data-jpa/README.md)
9. [09 - Exception Handling](./09-exception-handling/README.md)
10. [10 - Validation](./10-validation/README.md)
11. [11 - Spring Security + JWT](./11-spring-security-jwt/README.md)
12. [12 - Capstone Project 🏆](./12-capstone-project/README.md)

---

## ⭐ Star This Repo

If this helps you learn, please ⭐ star the repository — it helps others find it too!

---

*Happy Learning! — Built with ❤️ for PunitNamdeo*
