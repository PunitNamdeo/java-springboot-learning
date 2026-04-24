package com.learn.jpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Module 08 - Spring Data JPA
 *
 * After starting, visit:
 *   API:        http://localhost:8080/api/students
 *   H2 Console: http://localhost:8080/h2-console
 *               JDBC URL: jdbc:h2:mem:testdb  |  User: sa  |  Password: (empty)
 */
@SpringBootApplication
public class SpringDataJpaApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringDataJpaApplication.class, args);
        System.out.println("\n========================================");
        System.out.println(" Spring Data JPA Module is running!");
        System.out.println(" API:        http://localhost:8080/api/students");
        System.out.println(" H2 Console: http://localhost:8080/h2-console");
        System.out.println("========================================");
    }
}
