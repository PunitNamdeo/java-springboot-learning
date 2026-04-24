package com.learn.validation;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ValidationTest {

    @Autowired
    private MockMvc mockMvc;

    // ✅ Test 1: Valid student — should be created (201)
    @Test
    void createStudent_ValidData_Returns201() throws Exception {
        String json = """
                {
                    "name": "David Lee",
                    "email": "david@example.com",
                    "course": "DevOps",
                    "age": 23
                }
                """;
        mockMvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("David Lee"));
    }

    // ✅ Test 2: Blank name — should return 400 with fieldErrors
    @Test
    void createStudent_BlankName_Returns400() throws Exception {
        String json = """
                {
                    "name": "",
                    "email": "test@example.com",
                    "course": "Java",
                    "age": 20
                }
                """;
        mockMvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Validation Failed"))
                .andExpect(jsonPath("$.fieldErrors").isArray());
    }

    // ✅ Test 3: Invalid email format — should return 400
    @Test
    void createStudent_InvalidEmail_Returns400() throws Exception {
        String json = """
                {
                    "name": "Test User",
                    "email": "not-a-valid-email",
                    "course": "Java",
                    "age": 21
                }
                """;
        mockMvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.fieldErrors[0].field").value("email"));
    }

    // ✅ Test 4: Age below minimum — should return 400
    @Test
    void createStudent_AgeTooLow_Returns400() throws Exception {
        String json = """
                {
                    "name": "Young User",
                    "email": "young@example.com",
                    "course": "Java",
                    "age": 15
                }
                """;
        mockMvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.fieldErrors[0].field").value("age"));
    }

    // ✅ Test 5: Multiple validation errors at once
    @Test
    void createStudent_MultipleErrors_ReturnsAllFieldErrors() throws Exception {
        String json = """
                {
                    "name": "",
                    "email": "bad-email",
                    "course": "",
                    "age": 10
                }
                """;
        mockMvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.fieldErrors").isArray());
    }

    // ✅ Test 6: Happy path — get all students (200)
    @Test
    void getAllStudents_Returns200() throws Exception {
        mockMvc.perform(get("/api/students"))
                .andExpect(status().isOk());
    }
}
