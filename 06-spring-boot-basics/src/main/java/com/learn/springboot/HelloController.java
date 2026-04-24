package com.learn.springboot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * HelloController — your first Spring Boot REST controller!
 *
 * @RestController = @Controller + @ResponseBody
 * All methods return JSON/String directly (no view templates)
 *
 * Test these endpoints:
 *   GET  http://localhost:8080/hello
 *   GET  http://localhost:8080/hello/Punit
 *   GET  http://localhost:8080/info
 */
@RestController
@RequestMapping("/hello")
public class HelloController {

    // Inject value from application.properties
    @Value("${app.name}")
    private String appName;

    @Value("${app.welcome-message}")
    private String welcomeMessage;

    /**
     * GET /hello
     * Returns a simple welcome message
     */
    @GetMapping
    public String hello() {
        return welcomeMessage;
    }

    /**
     * GET /hello/{name}
     * Returns a personalized greeting
     * @PathVariable extracts {name} from the URL
     */
    @GetMapping("/{name}")
    public String helloName(@PathVariable String name) {
        return "Hello, " + name + "! Welcome to " + appName + "!";
    }

    /**
     * GET /hello/info
     * Returns app info as JSON object (Map serialized to JSON by Jackson)
     */
    @GetMapping("/info")
    public Map<String, Object> info() {
        return Map.of(
            "appName", appName,
            "message", welcomeMessage,
            "timestamp", LocalDateTime.now().toString(),
            "status", "UP"
        );
    }
}
