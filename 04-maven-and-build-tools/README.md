# 📗 Module 04 — Maven & Build Tools

> **Level:** 🟢 Beginner | **Estimated Time:** 1 day

---

## 📚 Table of Contents
1. [What is Maven?](#1-what-is-maven)
2. [Maven Project Structure](#2-maven-project-structure)
3. [pom.xml Anatomy](#3-pomxml-anatomy)
4. [Maven Lifecycle](#4-maven-lifecycle)
5. [Common Maven Commands](#5-common-maven-commands)
6. [Dependency Scopes](#6-dependency-scopes)
7. [JAR vs WAR](#7-jar-vs-war)

---

## 1. What is Maven?

Maven is a **build automation and dependency management tool** for Java projects.

**Problems Maven solves:**
- ❌ Without Maven: manually download JARs, manage classpaths, write build scripts
- ✅ With Maven: declare dependencies in `pom.xml`, Maven downloads them automatically

```
You declare:   "I need Spring Boot 3.2.0"
Maven does:    Downloads it + all its dependencies from Maven Central
               Puts them on the classpath
               Compiles, tests, and packages your project
```

---

## 2. Maven Project Structure

```
my-project/
├── pom.xml                          ← Project config (dependencies, build settings)
├── src/
│   ├── main/
│   │   ├── java/                    ← Your Java source code
│   │   │   └── com/example/App.java
│   │   └── resources/               ← Config files (application.properties, etc.)
│   │       └── application.properties
│   └── test/
│       ├── java/                    ← Your test code
│       │   └── com/example/AppTest.java
│       └── resources/               ← Test config files
└── target/                          ← Generated output (compiled classes, JAR)
    └── my-project-1.0.0.jar
```

💡 **Key Takeaway:** Maven enforces a **standard directory structure**. Every Maven project looks the same — makes onboarding easier.

---

## 3. pom.xml Anatomy

`pom.xml` = **Project Object Model** — the heart of a Maven project.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
             https://maven.apache.org/xsd/maven-4.0.0.xsd">

    <modelVersion>4.0.0</modelVersion>

    <!-- ══════════════════════════════════════════════════════════════
         PARENT — Inherit defaults from Spring Boot
         This gives you Spring Boot auto-configuration,
         dependency version management, and build plugins.
    ══════════════════════════════════════════════════════════════ -->
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.2.0</version>
        <relativePath/>
    </parent>

    <!-- ══════════════════════════════════════════════════════════════
         GAV COORDINATES — Uniquely identifies your project
         groupId    = your organization/domain (reverse domain)
         artifactId = your project name
         version    = your project version
    ══════════════════════════════════════════════════════════════ -->
    <groupId>com.punit</groupId>
    <artifactId>my-spring-app</artifactId>
    <version>1.0.0</version>
    <packaging>jar</packaging>   <!-- jar (default) or war -->

    <name>My Spring Application</name>
    <description>A demo Spring Boot project</description>

    <!-- ══════════════════════════════════════════════════════════════
         PROPERTIES — Reusable values across the pom
    ══════════════════════════════════════════════════════════════ -->
    <properties>
        <java.version>17</java.version>
        <mapstruct.version>1.5.5.Final</mapstruct.version>
    </properties>

    <!-- ══════════════════════════════════════════════════════════════
         DEPENDENCIES — Libraries your project needs
         Maven downloads these from Maven Central Repository
    ══════════════════════════════════════════════════════════════ -->
    <dependencies>

        <!-- Spring Boot Web — builds REST APIs, includes Tomcat -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
            <!-- No <version> needed — parent manages it! -->
        </dependency>

        <!-- Spring Boot Data JPA — database access with Hibernate -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>

        <!-- H2 — in-memory database for development/testing -->
        <dependency>
            <groupId>com.h2database</groupId>
            <artifactId>h2</artifactId>
            <scope>runtime</scope>  <!-- only needed at runtime, not compile -->
        </dependency>

        <!-- Spring Boot Validation — @Valid, @NotNull, etc. -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-validation</artifactId>
        </dependency>

        <!-- Spring Boot Security -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
        </dependency>

        <!-- Lombok — reduces boilerplate (@Getter, @Setter, @Builder) -->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>

        <!-- Spring Boot DevTools — hot reload during development -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <scope>runtime</scope>
            <optional>true</optional>
        </dependency>

        <!-- Spring Boot Test — JUnit 5, Mockito, MockMvc -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>  <!-- only available in test code -->
        </dependency>

    </dependencies>

    <!-- ══════════════════════════════════════════════════════════════
         BUILD — Build plugins
    ══════════════════════════════════════════════════════════════ -->
    <build>
        <plugins>
            <!-- Spring Boot Maven Plugin — creates executable JAR -->
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <!-- Exclude Lombok from final JAR -->
                    <excludes>
                        <exclude>
                            <groupId>org.projectlombok</groupId>
                            <artifactId>lombok</artifactId>
                        </exclude>
                    </excludes>
                </configuration>
            </plugin>
        </plugins>
    </build>

</project>
```

---

## 4. Maven Lifecycle

Maven has **3 built-in lifecycles**. The most important is `default`:

```
default lifecycle phases (in order):

  validate    → checks project is correct
      ↓
  compile     → compiles src/main/java → target/classes
      ↓
  test        → runs unit tests in src/test/java
      ↓
  package     → creates JAR/WAR in target/
      ↓
  verify      → runs integration tests
      ↓
  install     → copies JAR to local ~/.m2 repository
      ↓
  deploy      → uploads JAR to remote repository
```

💡 **Key Takeaway:** Running `mvn package` automatically runs ALL phases before it: validate → compile → test → package.

---

## 5. Common Maven Commands

```bash
# Clean the target/ directory (delete compiled output)
mvn clean

# Compile source code
mvn compile

# Run all unit tests
mvn test

# Create JAR/WAR file
mvn package

# Clean + package (most common)
mvn clean package

# Install to local Maven repository (~/.m2)
mvn clean install

# Run Spring Boot application
mvn spring-boot:run

# Skip tests (faster builds)
mvn clean package -DskipTests

# Show dependency tree
mvn dependency:tree

# Download all dependencies
mvn dependency:resolve
```

---

## 6. Dependency Scopes

| Scope | Available In | Example |
|-------|-------------|--------|
| `compile` (default) | Compile + runtime + test | `spring-boot-starter-web` |
| `runtime` | Runtime + test only | `h2`, `mysql-connector` |
| `test` | Test code only | `spring-boot-starter-test` |
| `provided` | Compile + test (not packaged) | `jakarta.servlet-api` |
| `optional` | Compile only, not transitive | `lombok` |

```xml
<!-- runtime scope — H2 only needed when app runs, not at compile time -->
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>runtime</scope>
</dependency>

<!-- test scope — JUnit only needed in test code -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>
```

---

## 7. JAR vs WAR

| Feature | JAR | WAR |
|---------|-----|-----|
| Full name | Java ARchive | Web ARchive |
| Contains | Classes + resources | Classes + JSPs + web.xml |
| Runs with | `java -jar app.jar` | External server (Tomcat, JBoss) |
| Spring Boot default | ✅ JAR | ❌ WAR |
| Embedded server | ✅ Yes (Tomcat inside) | ❌ No |

💡 **Key Takeaway:** Spring Boot packages as **JAR by default** — it includes an embedded Tomcat server. Just run `java -jar your-app.jar` and your REST API is live!

```bash
# After mvn clean package:
java -jar target/my-spring-app-1.0.0.jar
# Your Spring Boot app starts on port 8080!
```

---

## ✅ Best Practices
- Always use `spring-boot-starter-parent` as parent for Spring Boot projects
- Never specify versions for Spring Boot managed dependencies — let the parent handle it
- Use `mvn clean package -DskipTests` for faster builds during development
- Commit `pom.xml` to version control — never commit the `target/` folder
- Add `target/` to `.gitignore`

## ⚠️ Common Mistakes
- Specifying conflicting dependency versions (let parent manage them)
- Forgetting `<scope>test</scope>` on test dependencies
- Not running `mvn clean` when you see weird compilation errors

## 💡 Key Takeaways
- Maven = dependency management + build automation
- `pom.xml` is the single source of truth for your project
- Spring Boot parent pom manages all dependency versions for you
- `mvn spring-boot:run` is all you need during development

---

➡️ **Next:** [Module 05 — Spring Core](../05-spring-core/README.md)
