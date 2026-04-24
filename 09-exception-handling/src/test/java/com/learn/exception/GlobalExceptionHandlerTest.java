package com.learn.exception;

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
class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    // ✅ Test 1: 404 — Student not found
    @Test
    void getStudentById_NotFound_Returns404() throws Exception {
        mockMvc.perform(get("/api/students/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message").value("Student not found with ID: 999"));
    }

    // ✅ Test 2: 409 — Duplicate email
    @Test
    void createStudent_DuplicateEmail_Returns409() throws Exception {
        String json = """
                {
                    "name": "Alice Copy",
                    "email": "alice@example.com",
                    "course": "Java"
                }
                """;

        mockMvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.status").value(409))
                .andExpect(jsonPath("$.error").value("Conflict"));
    }

    // ✅ Test 3: 400 — Bad path variable type
    @Test
    void getStudentById_InvalidIdType_Returns400() throws Exception {
        mockMvc.perform(get("/api/students/abc"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Bad Request"));
    }

    // ✅ Test 4: 200 — Happy path still works
    @Test
    void getAllStudents_Returns200() throws Exception {
        mockMvc.perform(get("/api/students"))
                .andExpect(status().isOk());
    }
}
