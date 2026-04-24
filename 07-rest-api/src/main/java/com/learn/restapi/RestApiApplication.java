package com.learn.restapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Module 07 - REST API
 *
 * Run this app and test with Postman:
 *
 * GET    http://localhost:8080/api/students
 * GET    http://localhost:8080/api/students/1
 * GET    http://localhost:8080/api/students/search?name=Punit
 * POST   http://localhost:8080/api/students
 * PUT    http://localhost:8080/api/students/1
 * DELETE http://localhost:8080/api/students/1
 */
@SpringBootApplication
public class RestApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(RestApiApplication.class, args);
        System.out.println("\n=================================");
        System.out.println(" REST API Module is running!");
        System.out.println(" http://localhost:8080/api/students");
        System.out.println("=================================");
    }
}
