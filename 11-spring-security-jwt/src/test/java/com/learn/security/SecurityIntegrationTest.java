package com.learn.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learn.security.dto.AuthRequest;
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
class SecurityIntegrationTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    // ✅ Test 1: Login with valid credentials → 200 + token
    @Test
    void login_ValidCredentials_ReturnsToken() throws Exception {
        AuthRequest req = new AuthRequest();
        req.setUsername("admin");
        req.setPassword("password");

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").isNotEmpty());
    }

    // ✅ Test 2: Login with wrong password → 401
    @Test
    void login_InvalidCredentials_Returns401() throws Exception {
        AuthRequest req = new AuthRequest();
        req.setUsername("admin");
        req.setPassword("wrongpassword");

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isUnauthorized());
    }

    // ✅ Test 3: Access protected route WITHOUT token → 401
    @Test
    void getStudents_NoToken_Returns401() throws Exception {
        mockMvc.perform(get("/api/students"))
                .andExpect(status().isUnauthorized());
    }

    // ✅ Test 4: Access protected route WITH valid token → 200
    @Test
    void getStudents_WithValidToken_Returns200() throws Exception {
        // Step 1: Login and get token
        AuthRequest req = new AuthRequest();
        req.setUsername("admin");
        req.setPassword("password");

        MvcResult loginResult = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = loginResult.getResponse().getContentAsString();
        String token = objectMapper.readTree(responseBody).get("token").asText();

        // Step 2: Use token to access protected endpoint
        mockMvc.perform(get("/api/students")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    // ✅ Test 5: /me endpoint returns logged-in username
    @Test
    void me_WithValidToken_ReturnsUsername() throws Exception {
        AuthRequest req = new AuthRequest();
        req.setUsername("user");
        req.setPassword("user123");

        MvcResult loginResult = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andReturn();

        String token = objectMapper.readTree(
                loginResult.getResponse().getContentAsString()).get("token").asText();

        mockMvc.perform(get("/api/students/me")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("user")));
    }
}
