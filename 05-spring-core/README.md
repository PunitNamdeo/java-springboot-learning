# 📗 Module 05 — Spring Core

> **Level:** 🟡 Intermediate | **Estimated Time:** 2–3 days

---

## 📚 Table of Contents
1. [What is Spring Framework?](#1-what-is-spring-framework)
2. [IoC — Inversion of Control](#2-ioc--inversion-of-control)
3. [DI — Dependency Injection](#3-di--dependency-injection)
4. [Spring Beans](#4-spring-beans)
5. [Bean Scopes](#5-bean-scopes)
6. [Stereotype Annotations](#6-stereotype-annotations)
7. [@Autowired, @Qualifier, @Primary](#7-autowired-qualifier-primary)
8. [@Value — Injecting Properties](#8-value--injecting-properties)
9. [Bean Lifecycle](#9-bean-lifecycle)
10. [Component Scanning](#10-component-scanning)

---

## 1. What is Spring Framework?

Spring is a **comprehensive Java framework** that simplifies enterprise application development.

**Problems Spring solves:**
```
Without Spring:                    With Spring:
──────────────────────────────     ──────────────────────────────
❌ Manual object creation           ✅ Spring creates objects for you
❌ Manual wiring of dependencies    ✅ Spring injects dependencies
❌ Boilerplate database code        ✅ Spring Data handles it
❌ Complex security setup           ✅ Spring Security abstracts it
❌ Manual transaction management    ✅ @Transactional handles it
```

**Spring ecosystem:**
```
Spring Framework (Core)
    ├── Spring Boot       ← Simplifies Spring setup (Module 06)
    ├── Spring MVC        ← Web/REST (Module 07)
    ├── Spring Data JPA   ← Database (Module 08)
    ├── Spring Security   ← Auth (Module 11)
    └── Spring Cloud      ← Microservices
```

---

## 2. IoC — Inversion of Control

**Traditional code** — YOU control object creation:
```java
// You create objects manually
EmailService emailService = new EmailService();
UserRepository userRepository = new UserRepository();
UserService userService = new UserService(emailService, userRepository);
```

**With IoC** — SPRING controls object creation:
```java
// You just declare what you need, Spring provides it
@Service
public class UserService {
    private final EmailService emailService;      // Spring injects this
    private final UserRepository userRepository;  // Spring injects this

    public UserService(EmailService emailService, UserRepository userRepository) {
        this.emailService = emailService;
        this.userRepository = userRepository;
    }
}
```

> 💡 **Key Takeaway:** IoC = "Don't call us, we'll call you." You define classes and their dependencies; Spring's **IoC Container** creates and wires everything.

---

## 3. DI — Dependency Injection

DI is the mechanism Spring uses to implement IoC. There are 3 types:

### ✅ Constructor Injection (RECOMMENDED)
```java
@Service
public class OrderService {
    private final PaymentService paymentService;
    private final InventoryService inventoryService;

    // Spring sees this constructor and injects the dependencies
    public OrderService(PaymentService paymentService,
                        InventoryService inventoryService) {
        this.paymentService = paymentService;
        this.inventoryService = inventoryService;
    }
}
```
**Why recommended?**
- Dependencies are `final` (immutable)
- Easy to test (just pass mocks)
- Makes dependencies explicit

### Setter Injection (optional dependencies)
```java
@Service
public class NotificationService {
    private EmailService emailService;

    @Autowired  // Spring calls this setter
    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }
}
```

### ❌ Field Injection (avoid in production)
```java
@Service
public class UserService {
    @Autowired  // Spring injects directly into field
    private UserRepository userRepository;  // hard to test, not recommended
}
```

---

## 4. Spring Beans

A **Bean** is simply an object managed by the Spring IoC container.

### Using @Bean in @Configuration
```java
@Configuration
public class AppConfig {

    // This method's return value becomes a Spring Bean
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    // Bean with dependency on another bean
    @Bean
    public EmailService emailService(MailSender mailSender) {
        return new EmailService(mailSender);
    }
}
```

### Using Stereotype Annotations (most common)
```java
@Component   // generic Spring-managed component
@Service     // business logic layer
@Repository  // data access layer
@Controller  // web layer (handles HTTP requests)
@RestController // web layer for REST APIs
```

All of the above tell Spring: *"Create an instance of this class and manage it."*

---

## 5. Bean Scopes

| Scope | Creates | Use When |
|-------|---------|----------|
| `singleton` (default) | ONE instance per Spring container | Stateless services |
| `prototype` | NEW instance every time requested | Stateful objects |
| `request` | ONE per HTTP request | Web apps |
| `session` | ONE per HTTP session | Web apps |

```java
@Component
@Scope("singleton")   // default — same instance every time
public class LoggerService { ... }

@Component
@Scope("prototype")   // new instance every injection
public class ReportGenerator { ... }
```

> ⚠️ **Common Mistake:** Don't store user-specific data in singleton beans — it's shared across all users!

---

## 6. Stereotype Annotations

```java
// @Component — generic bean
@Component
public class CacheManager {
    public void cache(String key, Object value) { ... }
}

// @Service — business logic
@Service
public class ProductService {
    // contains business rules, calculations, orchestration
    public double calculateDiscountedPrice(Product product, int discountPercent) {
        return product.getPrice() * (1 - discountPercent / 100.0);
    }
}

// @Repository — data access
@Repository
public class ProductRepository {
    // communicates with database
    // Spring also adds exception translation here
    // (converts DB exceptions to Spring DataAccessException)
}

// @Controller / @RestController — web layer
@RestController
@RequestMapping("/api/products")
public class ProductController {
    // handles HTTP requests, calls service layer
}
```

**Layered architecture:**
```
HTTP Request
     ↓
@RestController  ← validates input, calls service
     ↓
@Service         ← business logic
     ↓
@Repository      ← database access
     ↓
Database
```

---

## 7. @Autowired, @Qualifier, @Primary

```java
public interface NotificationSender {
    void send(String message);
}

@Component
public class EmailSender implements NotificationSender {
    public void send(String message) {
        System.out.println("Email: " + message);
    }
}

@Component
public class SmsSender implements NotificationSender {
    public void send(String message) {
        System.out.println("SMS: " + message);
    }
}
```

If Spring finds **multiple beans** of the same type, it gets confused:

```java
// ❌ Spring doesn't know which one to inject!
@Service
public class AlertService {
    @Autowired
    private NotificationSender sender; // EmailSender or SmsSender?
}
```

**Solution 1: @Primary** — mark one as default
```java
@Component
@Primary  // This is injected by default
public class EmailSender implements NotificationSender { ... }
```

**Solution 2: @Qualifier** — specify which one
```java
@Service
public class AlertService {
    private final NotificationSender sender;

    public AlertService(@Qualifier("smsSender") NotificationSender sender) {
        this.sender = sender;  // specifically uses SmsSender
    }
}
```

---

## 8. @Value — Injecting Properties

```properties
# application.properties
app.name=My Spring App
app.max-retries=3
app.email=admin@example.com
```

```java
@Component
public class AppConfig {

    @Value("${app.name}")
    private String appName;

    @Value("${app.max-retries}")
    private int maxRetries;

    @Value("${app.email:default@example.com}")  // with default value
    private String email;

    public void printConfig() {
        System.out.println("App: " + appName);
        System.out.println("Max retries: " + maxRetries);
        System.out.println("Email: " + email);
    }
}
```

### @ConfigurationProperties (better for groups)
```properties
# application.properties
app.database.url=jdbc:h2:mem:testdb
app.database.username=sa
app.database.max-connections=10
```

```java
@ConfigurationProperties(prefix = "app.database")
@Component
public class DatabaseProperties {
    private String url;
    private String username;
    private int maxConnections;
    // getters and setters...
}
```

---

## 9. Bean Lifecycle

```
Spring Container starts
       ↓
1. Bean instantiated (constructor called)
       ↓
2. Dependencies injected (@Autowired)
       ↓
3. @PostConstruct method runs   ← YOUR initialization code
       ↓
4. Bean is ready to use
       ↓
   ... application runs ...
       ↓
5. @PreDestroy method runs      ← YOUR cleanup code
       ↓
6. Bean destroyed
```

```java
@Component
public class DatabaseConnectionPool {
    private List<Connection> pool;

    @PostConstruct  // runs after Spring injects all dependencies
    public void initialize() {
        System.out.println("Initializing connection pool...");
        pool = new ArrayList<>();
        // create initial connections
    }

    @PreDestroy  // runs before Spring destroys the bean
    public void cleanup() {
        System.out.println("Closing all connections...");
        // close all connections
    }
}
```

---

## 10. Component Scanning

Spring needs to **find** your beans. It does this through component scanning.

```java
@SpringBootApplication  // includes @ComponentScan
public class MyApp {
    public static void main(String[] args) {
        SpringApplication.run(MyApp.class, args);
    }
}
```

`@SpringBootApplication` scans the **current package and all sub-packages** for annotated classes.

```
com.punit.myapp
    ├── MyApp.java                   ← @SpringBootApplication (scan starts here)
    ├── controller/
    │   └── UserController.java      ← @RestController ✅ found
    ├── service/
    │   └── UserService.java         ← @Service ✅ found
    ├── repository/
    │   └── UserRepository.java      ← @Repository ✅ found
    └── config/
        └── AppConfig.java           ← @Configuration ✅ found
```

> ⚠️ **Common Mistake:** If your main class is in `com.punit`, but a bean is in `org.utils`, Spring won't find it automatically. Use `@ComponentScan(basePackages = "org.utils")` to include it.

---

## ✅ Best Practices
- Always use **Constructor Injection** over field injection
- Keep beans **stateless** (especially singletons)
- Use `@Service` for business logic, `@Repository` for data access — semantics matter
- Use `@ConfigurationProperties` instead of many `@Value` annotations
- Keep `@Configuration` classes clean and focused

## ⚠️ Common Mistakes
- Using field injection (`@Autowired` on fields) — hard to test
- Storing mutable state in singleton beans
- Circular dependencies (A depends on B, B depends on A)
- Forgetting that `@Component` only works if the class is in the scanned package

## 💡 Key Takeaways
- IoC Container manages object lifecycle — you just define classes
- DI wires dependencies together automatically
- Constructor injection is the recommended way
- `@Component`, `@Service`, `@Repository`, `@Controller` are all beans
- Spring finds beans via component scanning from `@SpringBootApplication`

---

➡️ **Next:** [Module 06 — Spring Boot Basics](../06-spring-boot-basics/README.md)
