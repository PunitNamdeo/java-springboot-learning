package com.learn.security.config;

import com.learn.security.filter.JwtAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * ============================================================
 *  SECURITY CONFIGURATION
 * ============================================================
 *
 *  Defines:
 *  - Which routes are PUBLIC  (no token needed)
 *  - Which routes are PROTECTED (valid JWT required)
 *  - Session policy: STATELESS (no HttpSession — JWT handles state)
 *  - In-memory users (admin + user)
 *  - Password encoder (BCrypt)
 *  - Where JwtAuthFilter fits in the filter chain
 * ============================================================
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Disable CSRF — not needed for stateless REST APIs
            .csrf(csrf -> csrf.disable())

            // Route authorization rules
            .authorizeHttpRequests(auth -> auth
                // PUBLIC: login endpoint + H2 console
                .requestMatchers("/auth/login", "/h2-console/**").permitAll()
                // PROTECTED: everything else requires a valid JWT
                .anyRequest().authenticated()
            )

            // STATELESS: Spring Security will NOT create HttpSessions
            // Every request must carry its own JWT token
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            // Allow H2 console frames (it uses iframes)
            .headers(headers -> headers.frameOptions(frame -> frame.disable()))

            // Register our JwtAuthFilter BEFORE Spring's default auth filter
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * In-memory users for demo purposes.
     * In production, replace with a database-backed UserDetailsService.
     */
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
        var admin = User.builder()
                .username("admin")
                .password(encoder.encode("password"))
                .roles("ADMIN")
                .build();

        var user = User.builder()
                .username("user")
                .password(encoder.encode("user123"))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(admin, user);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
