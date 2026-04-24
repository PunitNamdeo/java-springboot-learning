package com.learn.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point of every Spring Boot application.
 *
 * @SpringBootApplication = @Configuration + @EnableAutoConfiguration + @ComponentScan
 *
 * Run this class and visit:
 *   http://localhost:8080/hello
 *   http://localhost:8080/hello/Punit
 *   http://localhost:8080/actuator/health
 */
@SpringBootApplication
public class SpringBootBasicsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootBasicsApplication.class, args);
        System.out.println("\n=====================================");
        System.out.println(" Spring Boot Basics App is running!");
        System.out.println(" Visit: http://localhost:8080/hello");
        System.out.println("=====================================");
    }
}
