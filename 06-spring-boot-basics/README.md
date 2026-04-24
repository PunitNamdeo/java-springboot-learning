# 📗 Module 06 — Spring Boot Basics

> **Level:** 🟡 Intermediate | **Estimated Time:** 2 days

---

## 📚 Table of Contents
1. [What is Spring Boot?](#1-what-is-spring-boot)
2. [@SpringBootApplication](#2-springbootapplication)
3. [Auto-Configuration](#3-auto-configuration)
4. [Embedded Server](#4-embedded-server)
5. [application.properties vs application.yml](#5-applicationproperties-vs-applicationyml)
6. [Spring Boot Profiles](#6-spring-boot-profiles)
7. [Spring Boot Starters](#7-spring-boot-starters)
8. [Spring Boot Actuator](#8-spring-boot-actuator)
9. [DevTools — Hot Reload](#9-devtools--hot-reload)
10. [Running the App](#10-running-the-app)

---

## 1. What is Spring Boot?

Spring Boot is an **opinionated, convention-over-configuration** framework built on top of Spring.

```
Spring Framework:   Powerful but requires a LOT of XML/Java config
Spring Boot:        Adds auto-configuration + starter POMs + embedded server
                    = Get started in minutes, not days
```

| Feature | Spring | Spring Boot |
|---------|--------|-------------|
| Setup time | Hours of configuration | Minutes |
| Server | Deploy WAR to Tomcat | Embedded Tomcat inside JAR |
| Config | Manual XML / Java config | Auto-configured |
| Dependency versions | Manual management | Managed by parent POM |
| Production-ready | Manual setup | Actuator built-in |

---

## 2. @SpringBootApplication

This single annotation is the entry point of every Spring Boot app:

```java
@SpringBootApplication
public class MyApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyApplication.class, args);
    }
}
```

`@SpringBootApplication` is a combination of **3 annotations**:

```java
@SpringBootApplication
// is equivalent to:
@Configuration          // This class can define @Bean methods
@EnableAutoConfiguration // Enable Spring Boot auto-configuration
@ComponentScan          // Scan current package + sub-packages for beans
```

---

## 3. Auto-Configuration

Spring Boot looks at what's on your **classpath** and automatically configures things.

```
You add spring-boot-starter-web to pom.xml
    ↓
Spring Boot detects spring-webmvc on classpath
    ↓
Automatically configures:
    - DispatcherServlet
    - Jackson (JSON converter)
    - Embedded Tomcat
    - Default error handling
    - Static resource serving
```

```
You add spring-boot-starter-data-jpa + h2
    ↓
Spring Boot detects JPA + H2 on classpath
    ↓
Automatically configures:
    - DataSource (H2 in-memory)
    - EntityManagerFactory
    - TransactionManager
    - Spring Data repositories
```

> 💡 You can **override** any auto-configuration by defining your own `@Bean`.

---

## 4. Embedded Server

Spring Boot includes an **embedded Tomcat** server. No external server needed!

```bash
# Old way (without Spring Boot):
# 1. Build WAR
# 2. Download Tomcat
# 3. Deploy WAR to Tomcat
# 4. Start Tomcat

# Spring Boot way:
mvn spring-boot:run
# Done! Server starts on http://localhost:8080

# Or build JAR and run:
mvn clean package
java -jar target/my-app-1.0.0.jar
```

Change the default port:
```properties
# application.properties
server.port=9090
```

---

## 5. application.properties vs application.yml

Both configure your Spring Boot app. Choose one style.

### application.properties (flat key=value)
```properties
# Server
server.port=8080
server.servlet.context-path=/api

# Database
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.username=sa
spring.datasource.password=
spring.datasource.driver-class-name=org.h2.Driver

# JPA
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

# H2 Console (access at /h2-console)
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

# Logging
logging.level.root=INFO
logging.level.com.punit=DEBUG

# Custom properties
app.name=My Spring App
app.version=1.0.0
```

### application.yml (hierarchical YAML)
```yaml
server:
  port: 8080
  servlet:
    context-path: /api

spring:
  datasource:
    url: jdbc:h2:mem:testdb
    username: sa
    password:
    driver-class-name: org.h2.Driver
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
  h2:
    console:
      enabled: true

app:
  name: My Spring App
  version: 1.0.0
```

> 💡 **Which to use?** Both work. `.properties` is simpler. `.yml` is cleaner for nested config.

---

## 6. Spring Boot Profiles

Profiles let you have **different configurations for different environments** (dev, test, prod).

```
src/main/resources/
├── application.properties          ← shared/default config
├── application-dev.properties      ← development config
├── application-test.properties     ← test config
└── application-prod.properties     ← production config
```

```properties
# application.properties (base)
app.name=My App

# application-dev.properties
server.port=8080
spring.datasource.url=jdbc:h2:mem:devdb
spring.jpa.show-sql=true
logging.level.root=DEBUG

# application-prod.properties
server.port=80
spring.datasource.url=jdbc:mysql://prod-server:3306/mydb
spring.jpa.show-sql=false
logging.level.root=WARN
```

**Activating a profile:**
```properties
# application.properties
spring.profiles.active=dev
```

```bash
# Or via command line:
java -jar app.jar --spring.profiles.active=prod

# Or via environment variable:
export SPRING_PROFILES_ACTIVE=prod
```

**Profile-specific beans:**
```java
@Configuration
public class DataSourceConfig {

    @Bean
    @Profile("dev")
    public DataSource devDataSource() {
        // H2 in-memory for development
        return new EmbeddedDatabaseBuilder().setType(H2).build();
    }

    @Bean
    @Profile("prod")
    public DataSource prodDataSource() {
        // MySQL for production
        HikariDataSource ds = new HikariDataSource();
        ds.setJdbcUrl("jdbc:mysql://...");
        return ds;
    }
}
```

---

## 7. Spring Boot Starters

Starters are **pre-configured dependency bundles**. Add one, get everything you need.

| Starter | What it includes |
|---------|------------------|
| `spring-boot-starter-web` | Spring MVC, Jackson, Tomcat |
| `spring-boot-starter-data-jpa` | Hibernate, Spring Data JPA |
| `spring-boot-starter-security` | Spring Security |
| `spring-boot-starter-validation` | Hibernate Validator, Jakarta Validation |
| `spring-boot-starter-actuator` | Health, metrics, info endpoints |
| `spring-boot-starter-test` | JUnit 5, Mockito, MockMvc |
| `spring-boot-starter-mail` | JavaMailSender |
| `spring-boot-starter-cache` | Spring Cache abstraction |
| `spring-boot-starter-aop` | Aspect-Oriented Programming |

---

## 8. Spring Boot Actuator

Actuator adds **production-ready monitoring** endpoints to your app.

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

```properties
# Expose all actuator endpoints
management.endpoints.web.exposure.include=*
management.endpoint.health.show-details=always
```

| Endpoint | URL | What it shows |
|----------|-----|---------------|
| Health | `/actuator/health` | App status (UP/DOWN) |
| Info | `/actuator/info` | App info |
| Metrics | `/actuator/metrics` | JVM metrics, HTTP stats |
| Beans | `/actuator/beans` | All Spring beans |
| Env | `/actuator/env` | Environment properties |
| Mappings | `/actuator/mappings` | All URL mappings |

---

## 9. DevTools — Hot Reload

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <scope>runtime</scope>
    <optional>true</optional>
</dependency>
```

With DevTools:
- App **automatically restarts** when you change Java files
- Browser **automatically refreshes** when you change templates
- **Faster restarts** (uses two classloaders)

---

## 10. Running the App

```bash
# Option 1: Maven
mvn spring-boot:run

# Option 2: Run the JAR
mvn clean package
java -jar target/my-app-1.0.0.jar

# Option 3: IntelliJ IDEA
# Click the green ▶ button next to the main method

# With a specific profile:
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

Once running, visit:
- **App:** http://localhost:8080
- **H2 Console:** http://localhost:8080/h2-console
- **Health:** http://localhost:8080/actuator/health

---

## ✅ Best Practices
- Keep sensitive config (passwords, API keys) in environment variables, not `application.properties`
- Use profiles to separate dev/test/prod configuration
- Never expose all actuator endpoints in production — secure them
- Use `spring.jpa.hibernate.ddl-auto=validate` in production (not `create` or `update`)

## ⚠️ Common Mistakes
- Using `ddl-auto=create` in production — this DROPS and recreates tables on startup!
- Committing `application-prod.properties` with real passwords to Git
- Not setting `spring.profiles.active` and wondering why dev config isn't loading

## 💡 Key Takeaways
- Spring Boot = Spring + auto-configuration + embedded server + starter POMs
- `@SpringBootApplication` starts everything
- `application.properties` is your configuration hub
- Profiles let you switch between environments easily
- DevTools makes development much faster

---

➡️ **Next:** [Module 07 — REST API](../07-rest-api/README.md)
