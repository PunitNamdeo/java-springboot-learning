package com.learn.capstone;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learn.capstone.dto.AuthRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CapstoneIntegrationTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    private String adminToken;
    private String userToken;

    @BeforeEach
    void setup() throws Exception {
        adminToken = getToken("admin", "password");
        userToken  = getToken("user",  "user123");
    }

    private String getToken(String username, String password) throws Exception {
        AuthRequest req = new AuthRequest();
        req.setUsername(username);
        req.setPassword(password);

        MvcResult result = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andReturn();

        return objectMapper.readTree(
                result.getResponse().getContentAsString()).get("token").asText();
    }

    // ✅ Test 1: Login returns token + username + role
    @Test
    void login_Admin_ReturnsTokenAndRole() throws Exception {
        AuthRequest req = new AuthRequest();
        req.setUsername("admin");
        req.setPassword("password");

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").isNotEmpty())
                .andExpect(jsonPath("$.username").value("admin"))
                .andExpect(jsonPath("$.role").value("ROLE_ADMIN"));
    }

    // ✅ Test 2: USER can read students
    @Test
    void getStudents_AsUser_Returns200() throws Exception {
        mockMvc.perform(get("/api/students")
                        .header("Authorization", "Bearer " + userToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray());
    }

    // ✅ Test 3: No token → 401
    @Test
    void getStudents_NoToken_Returns401() throws Exception {
        mockMvc.perform(get("/api/students"))
                .andExpect(status().isUnauthorized());
    }

    // ✅ Test 4: USER cannot create student → 403
    @Test
    void createStudent_AsUser_Returns403() throws Exception {
        String json = """
                {"name":"Test","email":"test99@example.com","course":"Java","age":20,"grade":"A"}
                """;
        mockMvc.perform(post("/api/students")
                        .header("Authorization", "Bearer " + userToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isForbidden());
    }

    // ✅ Test 5: ADMIN can create student → 201
    @Test
    void createStudent_AsAdmin_Returns201() throws Exception {
        String json = """
                {"name":"New Student","email":"newstudent@example.com","course":"DevOps","age":22,"grade":"B"}
                """;
        mockMvc.perform(post("/api/students")
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("New Student"));
    }

    // ✅ Test 6: Validation — blank name → 400 with fieldErrors
    @Test
    void createStudent_InvalidData_Returns400WithFieldErrors() throws Exception {
        String json = """
                {"name":"","email":"bad-email","course":"","age":10,"grade":"Z"}
                """;
        mockMvc.perform(post("/api/students")
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Validation Failed"))
                .andExpect(jsonPath("$.fieldErrors").isArray());
    }

    // ✅ Test 7: Search by keyword
    @Test
    void search_ByKeyword_ReturnsResults() throws Exception {
        mockMvc.perform(get("/api/students/search?keyword=alice")
                        .header("Authorization", "Bearer " + userToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Alice Johnson"));
    }

    // ✅ Test 8: Get by grade
    @Test
    void getByGrade_A_ReturnsAStudents() throws Exception {
        mockMvc.perform(get("/api/students/grade/A")
                        .header("Authorization", "Bearer " + userToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    // ✅ Test 9: 404 — student not found
    @Test
    void getStudent_NotFound_Returns404() throws Exception {
        mockMvc.perform(get("/api/students/9999")
                        .header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404));
    }

    // ✅ Test 10: Summary endpoint
    @Test
    void getSummary_ReturnsLightweightList() throws Exception {
        mockMvc.perform(get("/api/students/summary")
                        .header("Authorization", "Bearer " + userToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].grade").exists())
                .andExpect(jsonPath("$[0].email").doesNotExist()); // email NOT in summary
    }
}
